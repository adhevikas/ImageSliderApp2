package com.example.imagesliderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    TextView tv_register_now, txt_forget_password;
    EditText et_mobile, et_password;
    AppCompatButton btn_login_login;
//    Boolean isFirstTime;
//
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;
    BiometricManager biometricManager;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        biometricManager = BiometricManager.from(this);

//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences.edit();
//
//        isFirstTime = preferences.getBoolean("isFirstTime", true);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // notifyUser("Authentication Error: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // notifyUser("Authentication Succeeded");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // notifyUser("Authentication Failed");
            }
        });

//        if (isFirstTime) {
//            editor.putBoolean("isFirstTime", false).commit();
//            Toast.makeText(this, "First time user", Toast.LENGTH_SHORT).show();
//        } else {
//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//        }

        tv_register_now = findViewById(R.id.txt_register_now);
        et_mobile = findViewById(R.id.et_login_mobile_number);
        et_password = findViewById(R.id.edt_login_password);
        btn_login_login = findViewById(R.id.btn_login_login);
        txt_forget_password = findViewById(R.id.txt_forget_password);

        txt_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
              //  finish();
            }
        });

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from EditText fields
                String password = et_password.getText().toString().trim();
                String phone = et_mobile.getText().toString().trim();

                // Perform validation
                if (TextUtils.isEmpty(phone)) {
                    et_mobile.setError("mobile is required");
                    return;
                } else if (!isValidPhoneNumber(phone)) {
                    et_mobile.setError("Invalid phone number");
                    // return;
                } else if (TextUtils.isEmpty(password)) {
                    et_password.setError("password is required");
                    return;
                } else {
                  //  editor.putBoolean("isFirstTime", false).commit();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                }
            }
        });

        tv_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Authentication can be performed
                authenticateUser();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // No biometric hardware is available
                notifyUser("No biometric hardware available");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // Biometric hardware is unavailable
                notifyUser("Biometric hardware is unavailable");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // No biometric credentials enrolled
                notifyUser("No biometric credentials enrolled");
                break;
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10 && TextUtils.isDigitsOnly(phone);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void authenticateUser() {

//        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
//                .setTitle("Login With the FingerPrint")
//                .setNegativeButtonText("Login With Other")
//                .build();
//        biometricPrompt.authenticate(promptInfo);

    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelBioMetricAuthenticationPrompt();
    }

    private void cancelBioMetricAuthenticationPrompt() {
        if (biometricPrompt != null) {
            biometricPrompt.cancelAuthentication();
        }
    }
    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
