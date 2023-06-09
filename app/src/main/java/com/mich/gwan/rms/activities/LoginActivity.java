/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mich.gwan.rms.R;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.ActivityLoginBinding;
import com.mich.gwan.rms.helpers.InputValidation;
import com.mich.gwan.rms.info.Notification;
import com.mich.gwan.rms.models.Manager;

public class LoginActivity extends AppCompatActivity {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutIdNumber;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextIdNumber;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewSignupLink;

    private InputValidation inputValidation;
    private Notification notification;
    private DatabaseHelper databaseHelper;
    private Manager user;
    private ActivityLoginBinding binding;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    private SharedPreferences sharedPreferences;
    private String idNumber, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initListeners();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.window));
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        notification = new Notification();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        idNumber = sharedPreferences.getString(EMAIL_KEY,null);
        password = sharedPreferences.getString(PASSWORD_KEY, null);
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        appCompatTextViewSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper.checkUser()){
                    notification.snackBar(nestedScrollView, "A user is already Registered. Only one user is allowed!", Color.RED);
                } else {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }
        });
    }

    private void login() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextIdNumber, textInputLayoutIdNumber, getString(R.string.error_mssage_id))) {
                return;
            }
            if (!inputValidation.isValidIdNumber(textInputEditTextIdNumber, textInputLayoutIdNumber, getString(R.string.error_valid_id))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                return;
            }
            if (databaseHelper.checkUser(textInputEditTextIdNumber.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim())){

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Intent intent;
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("managerId", textInputEditTextIdNumber.getText().toString());
                                startActivity(intent);
                            }
                        },
                        1000
                );
                notification.snackBar(nestedScrollView, "Session opened Successfully!", Color.GREEN);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                // put values for password, email and usertype
                editor.putString(EMAIL_KEY, textInputEditTextIdNumber.getText().toString());
                editor.putString(PASSWORD_KEY, textInputEditTextPassword.getText().toString());

                user.setManagerId(Integer.parseInt(textInputEditTextIdNumber.getText().toString()));
                // save data with key and value
                editor.apply();
                // clear fields
                emptyInputEditText();

            } else {
                // Snack Bar to show message that record is wrong
                notification.snackBar(nestedScrollView, "Invalid Email or Password!", Color.RED);
            }
    }

    private void emptyInputEditText() {
        textInputEditTextIdNumber.setText("");
        textInputEditTextPassword.setText("");
    }

    private void initViews() {
        nestedScrollView = binding.nestedScrollView;
        textInputLayoutIdNumber = binding.textInputLayoutIdNumber;
        textInputLayoutPassword = binding.textInputLayoutPassword;
        textInputEditTextIdNumber = binding.textInputEditTextIdNumber;
        textInputEditTextPassword = binding.textInputEditTextPassword;
        appCompatButtonRegister = binding.appCompatButtonLogin;
        appCompatTextViewSignupLink = binding.textViewLinkRegister;
    }
}