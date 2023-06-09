/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mich.gwan.rms.R;
import com.mich.gwan.rms.adapter.HouseRecyclerAdapter;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.ActivityHouseBinding;
import com.mich.gwan.rms.databinding.HouseDialogBinding;
import com.mich.gwan.rms.helpers.InputValidation;
import com.mich.gwan.rms.helpers.RecyclerTouchListener;
import com.mich.gwan.rms.info.Notification;
import com.mich.gwan.rms.models.House;

import java.util.ArrayList;
import java.util.List;

public class HouseActivity extends AppCompatActivity {
    private ActivityHouseBinding binding;
    private CoordinatorLayout coordinatorLayout;

    private TextView emptyHouses;
    private FloatingActionButton registerHouses;
    private RecyclerView recyclerViewHouses;

    private InputValidation inputValidation;
    private Notification notification;
    private DatabaseHelper databaseHelper;

    private List<House> list;
    private HouseRecyclerAdapter recyclerAdapter;
    private String clientId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initListeners();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.window));
    }

    private void initObjects() {
        list = new ArrayList<>();
        intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        recyclerAdapter = new HouseRecyclerAdapter(list);

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(this);
        recyclerViewHouses.setLayoutManager(myLayoutManager);
        recyclerViewHouses.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHouses.setHasFixedSize(true);
        recyclerViewHouses.setAdapter(recyclerAdapter);
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        notification = new Notification();

        recyclerViewHouses.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerViewHouses, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(HouseActivity.this,RoomActivity.class).putExtra("houseId", list.get(position).getHouseId()));
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    private void initListeners() {

    }

    private void initViews() {
        registerHouses = binding.fabRegisterHouse;
        recyclerViewHouses = binding.houseView.recyclerViewHouses;
        emptyHouses = binding.houseView.emptyHouses;
        coordinatorLayout = binding.homeLayout;

        registerHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showRegisterDialog(false, null, -1);
            }
        });
    }

    /**
     * Inserting new value in db
     * and refreshing the list
     */
    @SuppressLint("NotifyDataSetChanged")
    private void insertValue(String houseName, String houseLocation, String houseType) {
        // Add the new value to db
        House par = new House(houseName,houseLocation,houseType);
        par.setClientId(Integer.parseInt(clientId));
        databaseHelper.addHouse(par);

        // adding new values to array list at 0 position
        list.add(0, par);
        // refreshing the list
        recyclerAdapter.notifyDataSetChanged();
        toggleEmptyList();
    }

    /**
     * Updating values in db and updating
     * item in the list by its position
     */
    private void updateValue(String houseName, String houseLocation, String houseType, int position) {
        House par = list.get(position);
        // updating values
        par.setHouseName(houseName);
        par.setHouseType(houseType);
        par.setLocation(houseLocation);
        par.setClientId(Integer.parseInt(clientId));
        // updating values in db
        databaseHelper.updateHouse(par);
        // refreshing the list
        list.set(position, par);
        recyclerAdapter.notifyItemChanged(position);

        toggleEmptyList();
    }

    /**
     * Deleting values from SQLite and removing the
     * item from the list by its position
     */
    private void deleteValue(int position) {
        // deleting the value from db
        databaseHelper.deleteHouse(list.get(position));
        // removing value from the list
        list.remove(position);
        recyclerAdapter.notifyItemRemoved(position);

        toggleEmptyList();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence[] colors = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showRegisterDialog(true, list.get(position), position);
                } else {
                    deleteValue(position);
                }
            }
        });
        builder.show();
    }


    private void showRegisterDialog(final boolean shouldUpdate, final House house, final int position) {
        HouseDialogBinding dialogBinding = HouseDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(dialogBinding.getRoot());

        final EditText houseName = dialogBinding.textInputEditTextHouseName;
        final EditText houseLocation = dialogBinding.textInputEditTextHouseLocation;
        final EditText houseType = dialogBinding.textInputEditTextHouseType;

        TextView dialogTitle = dialogBinding.title;
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_hse_register) : getString(R.string.lbl_hse_edit));

        if (shouldUpdate && house != null) {
            // display text to views
            houseName.setText(house.getHouseName());
            houseLocation.setText(house.getLocation());
            houseType.setText(house.getHouseType());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "UPDATE" : "SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show error message when no text is entered
                if (!inputValidation.isEditTextEmpty(houseName, getString(R.string.error_message_hsename))) {
                    houseName.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(houseType, getString(R.string.error_message_hsetype))) {
                    houseType.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(houseLocation, getString(R.string.error_message_hselocation))) {
                    houseLocation.requestFocus();
                    return;
                }
                // check if user is updating values
                if (shouldUpdate && house != null) {
                    // update values by position
                        updateValue(houseName.getText().toString().toUpperCase(),houseLocation.getText().toString().toUpperCase(),
                                houseType.getText().toString().toUpperCase(),position);
                    Toast.makeText(getApplicationContext(),getString(R.string.house_update),Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    if (databaseHelper.checkHouse(houseName.getText().toString().toUpperCase(),clientId)){
                        Toast.makeText(getApplicationContext(),getString(R.string.house_exists),Toast.LENGTH_SHORT).show();
                    } else {
                        // create new note
                            insertValue(houseName.getText().toString().toUpperCase(),houseLocation.getText().toString().toUpperCase(),
                                    houseType.getText().toString().toUpperCase());
                        Toast.makeText(getApplicationContext(), getString(R.string.house_registered), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void toggleEmptyList() {
        // check list.size() > 0
        if (databaseHelper.houseCount() > 0) {
            emptyHouses.setVisibility(View.GONE);
        }
        else {
            emptyHouses.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite(){
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                list.clear();
                list.addAll(databaseHelper.getHouses());
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}