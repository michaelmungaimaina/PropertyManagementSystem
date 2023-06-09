/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mich.gwan.rms.R;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.ActivityRegisterBinding;
import com.mich.gwan.rms.helpers.InputValidation;
import com.mich.gwan.rms.info.Notification;
import com.mich.gwan.rms.models.Manager;

public class RegisterActivity extends AppCompatActivity {
    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutIdNumber;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutMiddleName;
    private TextInputLayout textInputLayoutSurname;
    private TextInputLayout textInputLayoutPhoneNumber;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutLocation;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextIdNumber;
    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextMiddleName;
    private TextInputEditText textInputEditTextSurname;
    private TextInputEditText textInputEditTextPhoneNumber;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextLocation;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewSigninLink;

    private InputValidation inputValidation;
    private Notification notification;
    private DatabaseHelper databaseHelper;
    private ActivityRegisterBinding binding;
    private Manager user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initListeners();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.window));
    }

    private void initViews() {
        textInputLayoutIdNumber = binding.textInputLayoutIdNumber;
        textInputLayoutFirstName = binding.textInputLayoutFirstName;
        textInputLayoutMiddleName = binding.textInputLayoutMiddleName;
        textInputLayoutSurname = binding.textInputLayoutSurname;
        textInputLayoutPhoneNumber = binding.textInputLayoutPhoneNumber;
        textInputLayoutEmail = binding.textInputLayoutEmail;
        textInputLayoutLocation = binding.textInputLayoutLocation;
        textInputLayoutConfirmPassword = binding.textInputLayoutConfirmPassword;
        textInputLayoutPassword = binding.textInputLayoutPassword;

        textInputEditTextIdNumber = binding.textInputEditTextIdNumber;
        textInputEditTextFirstName = binding.textInputEditFirstName;
        textInputEditTextMiddleName = binding.textInputEditTextMiddleName;
        textInputEditTextSurname = binding.textInputEditTextSurname;
        textInputEditTextPhoneNumber = binding.textInputEditTextPhoneNumber;
        textInputEditTextEmail = binding.textInputEditTextEmail;
        textInputEditTextLocation = binding.textInputEditTextLocation;
        textInputEditTextConfirmPassword = binding.textInputEditTextConfirmPassword;
        textInputEditTextPassword = binding.textInputEditTextPassword;

        appCompatButtonRegister = binding.appCompatButtonRegister;
        appCompatTextViewSigninLink = binding.textViewLinkRegister;
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        appCompatTextViewSigninLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, getString(R.string.error_first_name))) {
            return;
        }
        inputValidation.isInputEditTextFilled(textInputEditTextMiddleName, textInputLayoutMiddleName, getString(R.string.error_middle_name));
        if (!inputValidation.isInputEditTextFilled(textInputEditTextSurname, textInputLayoutSurname, getString(R.string.error_surname))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextIdNumber, textInputLayoutIdNumber, getString(R.string.error_mssage_id))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextIdNumber, textInputLayoutIdNumber, getString(R.string.error_mssage_id))) {
            return;
        }
        if (!inputValidation.isValidIdNumber(textInputEditTextIdNumber, textInputLayoutIdNumber, getString(R.string.error_valid_id))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPhoneNumber, textInputLayoutPhoneNumber, getString(R.string.error_phone))) {
            return;
        }
        if (!inputValidation.isValidPhoneNumber(textInputEditTextPhoneNumber, textInputLayoutPhoneNumber, getString(R.string.error_valid_phone))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_mssage_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_valid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_confirm_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_mismatch))) {
            return;
        }
        if (!databaseHelper.checkUser(Integer.parseInt(textInputEditTextIdNumber.getText().toString()), textInputEditTextEmail.getText().toString().trim())){

            user.setFirstName(textInputEditTextFirstName.getText().toString().trim());
            user.setSecondName(textInputEditTextMiddleName.getText().toString().trim());
            user.setLastName(textInputEditTextSurname.getText().toString().trim());
            user.setManagerId(Integer.parseInt(textInputEditTextIdNumber.getText().toString().trim()));
            user.setPhoneNumber(textInputEditTextPhoneNumber.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setLocation(textInputEditTextLocation.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            // insert to database
            databaseHelper.addManager(user);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },
                    1000
            );
            notification.snackBar(nestedScrollView, "Registered successfully!", Color.GREEN);
            emptyInputEditText();

        } else {
            // Snack Bar to show message that record is wrong
            notification.snackBar(nestedScrollView, "ID Number Already Registered!", Color.RED);
            finish();
        }
    }

    private void emptyInputEditText() {
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        notification = new Notification();
        user = new Manager();
    }
}