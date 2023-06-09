/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mich.gwan.rms.R;
import com.mich.gwan.rms.adapter.AssignTenantAdapter;
import com.mich.gwan.rms.adapter.RoomRecyclerAdapter;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.ActivityRoomBinding;
import com.mich.gwan.rms.databinding.RoomBottomDialogBinding;
import com.mich.gwan.rms.databinding.RoomDialogBinding;
import com.mich.gwan.rms.databinding.TenantBottomDialogBinding;
import com.mich.gwan.rms.helpers.InputValidation;
import com.mich.gwan.rms.helpers.RecyclerTouchListener;
import com.mich.gwan.rms.info.Notification;
import com.mich.gwan.rms.models.Payment;
import com.mich.gwan.rms.models.Room;
import com.mich.gwan.rms.models.Tenant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private ActivityRoomBinding binding;
    private RecyclerView recyclerView;
    private FloatingActionButton fabRegisterRoom;
    private FloatingActionButton fabRegisterTenant;
    private CoordinatorLayout coordinatorLayout;

    private TextView emptyRoom;
    
    private DatabaseHelper db;
    private RoomRecyclerAdapter adapter;
    private AssignTenantAdapter tenantAdapter;
    private InputValidation inputValidation;
    private Notification notification;
    
    private Intent intent;
    private List<Room> list;
    private List<Tenant> tenantList;
    private String houseId;
    private Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initViews();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.window));
    }

    private void initViews() {
        recyclerView = binding.roomView.recyclerViewRooms;
        fabRegisterRoom = binding.fabRegisterHouse;
        fabRegisterTenant = binding.fabRegisterTenant;
        coordinatorLayout = binding.roomLayout;
        emptyRoom = binding.roomView.emptyRooms;
    }
    
    private void initObjects(){
        db = new DatabaseHelper(this);
        payment = new Payment();
        inputValidation = new InputValidation(this);
        notification = new Notification();
        list = new ArrayList<>();
        tenantList = new ArrayList<>();
        tenantAdapter = new AssignTenantAdapter(tenantList);
        adapter = new RoomRecyclerAdapter(list);
        intent = getIntent();
        houseId = intent.getStringExtra("houseId");

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        fabRegisterRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog(false, null, -1);
            }
        });
        fabRegisterTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTenant();
            }
        });

        adapter.setOnItemClickListener(this::selectTenant);
        //tenantAdapter.setOnItemClickListener(this::assignRoom);
    }

    /**
     * A bottomsheet dialog for registering a new tenant
     */
    private void registerTenant() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        TenantBottomDialogBinding bottomBinding = TenantBottomDialogBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomBinding.getRoot());

        final EditText firstName = bottomBinding.textInputEditTextFirstName;
        final EditText middleName = bottomBinding.textInputEditTextMiddleName;
        final EditText lastName = bottomBinding.textInputEditTextLastName;
        final EditText phoneNumber = bottomBinding.textInputEditTextPhoneNumber;
        final EditText email = bottomBinding.textInputEditTextEmail;

        final CardView submit = bottomBinding.cardViewSubmit;
        final CardView cancel = bottomBinding.cardViewCancel;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputValidation.isEditTextEmpty(firstName, getString(R.string.error_first_name))) {
                    firstName.requestFocus();
                    return;
                }
                inputValidation.isEditTextEmpty(middleName, getString(R.string.error_middle_name));
                if (!inputValidation.isEditTextEmpty(lastName, getString(R.string.error_message_lname))) {
                    lastName.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(phoneNumber, getString(R.string.error_phone))) {
                    phoneNumber.requestFocus();
                    return;
                }
                if (!inputValidation.isValidPhoneNumber(phoneNumber, getString(R.string.error_message_valid_phone))) {
                    phoneNumber.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(email, getString(R.string.error_mssage_email))) {
                    email.requestFocus();
                    return;
                }
                if (!inputValidation.isInputEditTextEmail(email, getString(R.string.error_valid_email))) {
                    email.requestFocus();
                    return;
                }
                Tenant tenant = new Tenant(0,firstName.getText().toString().toUpperCase(),middleName.getText().toString().toUpperCase(),
                        lastName.getText().toString().toUpperCase(),phoneNumber.getText().toString().toUpperCase(),email.getText().toString().toUpperCase());
                if (db.checkTenant(phoneNumber.getText().toString().toUpperCase(),email.getText().toString().toUpperCase()) && db.checkTenantPhoneUnique(phoneNumber.getText().toString().toUpperCase())){
                    db.addTenant(tenant);
                    Toast.makeText(getApplicationContext(),getString(R.string.client_register_success),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.email_or_phone_exists),Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }

    /**
     * Shows a dialog with a list of unassigned tenants
     * @param roomPosition the selected room position
     */
    private void selectTenant(int roomPosition){
        if (db.checkUser()){
            Toast.makeText(this,getString(R.string.no_unassigned_tenant),Toast.LENGTH_LONG).show();
        } else {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
            View view = layoutInflaterAndroid.inflate(R.layout.tenant_dialog, null);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            RecyclerView tenantRecyclerView = view.findViewById(R.id.recyclerTenant);
            tenantRecyclerView.setAdapter(tenantAdapter);
            // assign a layout manager
            RecyclerView.LayoutManager catLayoutManager = new LinearLayoutManager(getApplicationContext());
            tenantRecyclerView.setLayoutManager(catLayoutManager);
            tenantRecyclerView.setItemAnimator(new DefaultItemAnimator());
            tenantRecyclerView.setHasFixedSize(true);
            builder.setView(view); // setView

            tenantRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, tenantRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int tenantPosition) {
                    assignRoom(roomPosition, tenantPosition);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            builder
                    .setCancelable(false)
                    .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            dialogBox.cancel();
                        }
                    })
                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        public void onClick(DialogInterface dialogBox, int id) {
                            dialogBox.cancel();
                        }
                    });

            final AlertDialog dialog = builder.create();
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
        }
    }

    /**
     * Assigns the previously selected room to the selected tenant
     * @param roomPosition previously selected room
     * @param tenantPosition the selected tenant
     */
    private void assignRoom(int roomPosition, int tenantPosition) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        RoomBottomDialogBinding bottomBinding = RoomBottomDialogBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomBinding.getRoot());

        Room obj = list.get(roomPosition);
        Tenant tenant = tenantList.get(tenantPosition);
        payment.setTenantId(tenant.getTenantId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            payment.setPaymentDate(String.valueOf(LocalDate.now()));
            payment.setMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM")));
        }
        payment.setGarbageFee("00.00");


        final TextView rent = bottomBinding.textInputEditTextRoomRent;
        final TextView deposit = bottomBinding.textInputEditTextRoomDeposit;
        final TextView roomType = bottomBinding.textInputEditTextRoomType;
        final TextView tenantName = bottomBinding.textViewTenantName;

        final EditText rentEditText = bottomBinding.textInputEditTextRent;
        final EditText depositEditText = bottomBinding.textInputEditTextDeposit;

        final CardView mpesaPayment = bottomBinding.cardViewRequestPayment;
        final CardView showManualLayout = bottomBinding.cardViewManualData;
        final CardView cancel = bottomBinding.cardViewCancel;
        final CardView submit = bottomBinding.cardViewSubmit;

        final LinearLayout manualLayout = bottomBinding.layoutManualEntry;

        if (obj != null){
            rent.setText(obj.getRent());
            deposit.setText(obj.getDeposit());
            roomType.setText(obj.getRoomType());
        }

        tenantName.setText(getString(Integer.parseInt(tenant.getTenantFirstName() + " "+ tenant.getTenantLastName())));

        final boolean isVisible = true;
        mpesaPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        showManualLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible){
                    manualLayout.setVisibility(View.VISIBLE);
                    rentEditText.setText(obj.getRent());
                    depositEditText.setText(obj.getDeposit());
                } else {
                    manualLayout.setVisibility(View.GONE);
                    rentEditText.setText("");
                    depositEditText.setText("");
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible){
                    Toast.makeText(getApplicationContext(),getString(R.string.layout_not_visible),Toast.LENGTH_LONG).show();
                } else {
                    if(db.checkClientIsAssignedRoom(String.valueOf(tenant.getTenantId()))){
                        payment.setGarbageInvoice("200");
                        payment.setRentInvoice(String.valueOf(Integer.parseInt(obj.getRent()) + Integer.parseInt(obj.getDeposit())));
                    }else{
                        payment.setGarbageInvoice("200");
                        payment.setRentInvoice(String.valueOf(Integer.parseInt(obj.getRent())));
                    }
                    int paidAmount = Integer.parseInt(rentEditText.getText().toString()) + Integer.parseInt(depositEditText.getText().toString());
                    int unpaidAmount = (Integer.parseInt(obj.getRent()) + Integer.parseInt(obj.getDeposit())) - paidAmount;
                    payment.setUnpaidAmount(String.valueOf(unpaidAmount));
                    payment.setPaidAmount(String.valueOf(paidAmount));
                    assert obj != null;
                    assert tenant != null;
                    db.addTenantRoom(String.valueOf(obj.getRoomId()),String.valueOf(tenant.getTenantId()));
                    db.addPayment(payment);
                    db.updateRoom(obj);
                    Toast.makeText(getApplicationContext(),getString(R.string.room_assigned_success),Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }

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

    /**
     * Deletes the selected recyclerview data from database
     * @param position the position of the selected item
     */
    private void deleteValue(int position) {
            // deleting the value from db
            db.deleteRoom(list.get(position));
            // removing value from the list
            list.remove(position);
            adapter.notifyItemRemoved(position);

            toggleEmptyList();
    }

    private void showRegisterDialog(final boolean shouldUpdate, final Room room, final int position) {
        RoomDialogBinding dialogBinding = RoomDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(dialogBinding.getRoot());

        final EditText roomType = dialogBinding.textInputEditTextRoomType;
        final EditText roomRent = dialogBinding.textInputEditTextRent;
        final EditText roomDeposit = dialogBinding.textInputEditTextDeposit;

        TextView dialogTitle = dialogBinding.title;
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_room_register) : getString(R.string.lbl_room_edit));



        if (shouldUpdate && room != null) {
            // display text to views
            roomType.setText(room.getRoomType());
            roomRent.setText(room.getRent());
            roomDeposit.setText(room.getDeposit());
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
                // Input validation
                if (!inputValidation.isEditTextEmpty(roomType, getString(R.string.error_room_type))) {
                    roomType.requestFocus();
                    return;
                }
                if (!inputValidation.isValidRoomType(roomType, getString(R.string.invalid_room_type))){
                    roomType.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(roomDeposit, getString(R.string.error_deposit))) {
                    roomDeposit.requestFocus();
                    return;
                }
                if (!inputValidation.isEditTextEmpty(roomRent, getString(R.string.error_rent))) {
                    roomRent.requestFocus();
                    return;
                }
                // check if user is updating values
                if (shouldUpdate && room != null) {
                    // update values by position
                    updateValue(roomType.getText().toString().toUpperCase(),roomRent.getText().toString().toUpperCase(),
                            roomDeposit.getText().toString().toUpperCase(),position);
                    Toast.makeText(getApplicationContext(),getString(R.string.house_update),Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    /*check if roomtype already exists
                    if (db.checkHouse(roomType.getText().toString().toUpperCase(),houseId)){
                        Toast.makeText(getApplicationContext(),getString(R.string.room_exists),Toast.LENGTH_SHORT).show();
                    } else {**/
                        // create new note
                        insertValue(roomType.getText().toString().toUpperCase(),roomRent.getText().toString().toUpperCase(),
                                roomDeposit.getText().toString().toUpperCase());
                        Toast.makeText(getApplicationContext(), getString(R.string.house_registered), Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });
    }

    private void updateValue(String roomType, String rent, String deposit, int position) {
        Room par = list.get(position);
        // updating values
        par.setRoomType(roomType);
        par.setRent(rent);
        par.setDeposit(deposit);
        par.setHouseId(Integer.parseInt(houseId));
        // updating values in db
        db.updateRoom(par);
        // refreshing the list
        list.set(position, par);
        adapter.notifyItemChanged(position);

        toggleEmptyList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void insertValue(String roomType, String rent, String deposit) {
        // Add the new value to db
        Room par = new Room(Integer.parseInt(""),roomType,deposit,rent,"VACANT");
        par.setHouseId(Integer.parseInt(houseId));
        db.addRoom(par);

        // adding new values to array list at 0 position
        list.add(0, par);
        // refreshing the list
        adapter.notifyDataSetChanged();
        toggleEmptyList();
    }

    private void toggleEmptyList() {
        // check list.size() > 0
        if (db.houseCount() > 0) {
            emptyRoom.setVisibility(View.GONE);
        }
        else {
            emptyRoom.setVisibility(View.VISIBLE);
        }
    }


}