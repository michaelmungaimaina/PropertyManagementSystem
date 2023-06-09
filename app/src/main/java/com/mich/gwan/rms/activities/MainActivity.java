/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.activities;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mich.gwan.rms.R;
import com.mich.gwan.rms.adapter.ClientRecyclerAdapter;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.ActivityMainBinding;
import com.mich.gwan.rms.databinding.ClientDialogBinding;
import com.mich.gwan.rms.helpers.InputValidation;
import com.mich.gwan.rms.helpers.RecyclerTouchListener;
import com.mich.gwan.rms.info.Notification;
import com.mich.gwan.rms.models.Client;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CoordinatorLayout coordinatorLayout;

    private TextView emptyClients;
    private FloatingActionButton registerClients;
    private RecyclerView recyclerViewClients;

    private InputValidation inputValidation;
    private Notification notification;
    private DatabaseHelper databaseHelper;

    private List<Client> list;
    private ClientRecyclerAdapter recyclerAdapter;
    private String managerId;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        managerId = intent.getStringExtra("managerId");
        recyclerAdapter = new ClientRecyclerAdapter(list);

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(this);
        recyclerViewClients.setLayoutManager(myLayoutManager);
        recyclerViewClients.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClients.setHasFixedSize(true);
        recyclerViewClients.setAdapter(recyclerAdapter);
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        notification = new Notification();

        recyclerViewClients.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerViewClients, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(MainActivity.this,HouseActivity.class).putExtra("clientId", list.get(position).getClientId()));
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
        registerClients = binding.fabRegisterClient;
        recyclerViewClients = binding.homeView.recyclerViewClients;
        emptyClients = binding.homeView.emptyClients;
        coordinatorLayout = binding.homeLayout;

        registerClients.setOnClickListener(new View.OnClickListener() {
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
    private void insertValue(String fName, String midName, String lName, String location, String phone) {
        // Add the new value to db
        Client par = new Client(fName,midName,lName,location,phone);
        par.setManagerId(Integer.parseInt(managerId));
        databaseHelper.addClient(par);

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
    private void updateValue(String fName, String midName, String lName, String location, String phone, int position) {
        Client par = list.get(position);
        // updating values
        par.setFirstName(fName);
        par.setSecondName(midName);
        par.setLastName(lName);
        par.setLocation(location);
        par.setPhoneNumber(phone);
        par.setManagerId(Integer.parseInt(managerId));
        // updating values in db
        databaseHelper.updateClient(par);
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
databaseHelper.deleteClient(list.get(position));
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


    private void showRegisterDialog(final boolean shouldUpdate, final Client client, final int position) {
        ClientDialogBinding dialogBinding = ClientDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(dialogBinding.getRoot());

        final EditText firstName = dialogBinding.textInputEditTextFirstName;
        final EditText middleNAme = dialogBinding.textInputEditTextMiddleName;
        final EditText lastNAme = dialogBinding.textInputEditTextLastName;
        final EditText phoneNumber = dialogBinding.textInputEditTextPhoneNumber;
        final EditText location = dialogBinding.textInputEditTextLocation;

        TextView dialogTitle = dialogBinding.title;
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_register) : getString(R.string.lbl_edit));

        if (shouldUpdate && client != null) {
            // display text to views
            firstName.setText(client.getFirstName());
            middleNAme.setText(client.getSecondName());
            lastNAme.setText(client.getThirdName());
            phoneNumber.setText(client.getPhoneNumber());
            location.setText(client.getLocation());
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
                if (!inputValidation.isEditTextEmpty(firstName, getString(R.string.error_message_fname))) {
                    firstName.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(lastNAme, getString(R.string.error_message_lname))) {
                    lastNAme.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(phoneNumber, getString(R.string.error_message_phone))) {
                    phoneNumber.requestFocus();
                    return;
                }
                if (!inputValidation.isValidPhoneNumber(phoneNumber, getString(R.string.error_message_valid_phone))) {
                    phoneNumber.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(location, getString(R.string.error_message_location))) {
                    location.requestFocus();
                    return;
                }
                if (databaseHelper.checkClientPhoneUnique(phoneNumber.getText().toString().toUpperCase())) {
                    phoneNumber.setError(getString(R.string.error_phone_number));
                    phoneNumber.requestFocus();
                    return;
                }
                String clientMiddleName = middleNAme.getText().toString().toUpperCase();
                // check if user is updating values
                if (shouldUpdate && client != null) {
                    // update values by position
                    if (clientMiddleName.isEmpty()) {
                        updateValue(firstName.getText().toString().toUpperCase(),"",
                                lastNAme.getText().toString().toUpperCase(),location.getText().toString().toUpperCase(),
                                phoneNumber.getText().toString().toUpperCase(),position);
                    } else {
                        updateValue(firstName.getText().toString().toUpperCase(),clientMiddleName,
                                lastNAme.getText().toString().toUpperCase(),location.getText().toString().toUpperCase(),
                                phoneNumber.getText().toString().toUpperCase(),position);
                    }
                    Toast.makeText(getApplicationContext(),getString(R.string.client_update),Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    if (databaseHelper.checkClient(firstName.getText().toString().toUpperCase(),lastNAme.getText().toString().toUpperCase())){
                        Toast.makeText(getApplicationContext(),getString(R.string.client_exists),Toast.LENGTH_SHORT).show();
                    } else {
                        // create new note
                        if (clientMiddleName.isEmpty()) {
                            insertValue(firstName.getText().toString().toUpperCase(),"",
                                    lastNAme.getText().toString().toUpperCase(),location.getText().toString().toUpperCase(),
                                    phoneNumber.getText().toString().toUpperCase());
                        }else{
                            insertValue(firstName.getText().toString().toUpperCase(),clientMiddleName,
                                    lastNAme.getText().toString().toUpperCase(),location.getText().toString().toUpperCase(),
                                    phoneNumber.getText().toString().toUpperCase());
                        }
                        Toast.makeText(getApplicationContext(), getString(R.string.client_registered), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void toggleEmptyList() {
        // check list.size() > 0
        if (databaseHelper.clientCount() > 0) {
            emptyClients.setVisibility(View.GONE);
        }
        else {
            emptyClients.setVisibility(View.VISIBLE);
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
                list.addAll(databaseHelper.getClients());
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
