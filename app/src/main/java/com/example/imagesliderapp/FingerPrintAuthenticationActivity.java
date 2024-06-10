package com.example.imagesliderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FingerPrintAuthenticationActivity extends AppCompatActivity {

    private FingerprintManager fingerprintManager;
    private FingerprintHandler fingerprintHandler;
    private EditText etMobile, etPassword;
    private Button btnDoAuthenticate;
    private TextView tv_authentication;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finger_print_authentication);


       btnDoAuthenticate=findViewById(R.id.btn_finger_print_authentication_use_another_way);
      tv_authentication=findViewById(R.id.tv_authentication_available_or_not);



      btnDoAuthenticate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(FingerPrintAuthenticationActivity.this, LoginActivity.class));


          }
      });

        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (fingerprintManager != null && fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
                fingerprintHandler = new FingerprintHandler(FingerPrintAuthenticationActivity.this);

                //Toast.makeText(this, "Fingerprint authentication available", Toast.LENGTH_SHORT).show();

            }
            else {
                btnDoAuthenticate.setEnabled(false);
                tv_authentication.setText("Fingerprint authentication not available");
                startActivity(new Intent(FingerPrintAuthenticationActivity.this,LoginActivity.class));
                finish();

                // Toast.makeText(this, "Fingerprint authentication not available", Toast.LENGTH_SHORT).show();
            }

        }

    @Override
    protected void onStart() {
        super.onStart();
        startFingerprintAuthentication();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startFingerprintAuthentication() {

        FingerprintManager.CryptoObject cryptoObject = null; // Normally you should use a valid CryptoObject
        fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (fingerprintHandler != null && fingerprintHandler.cancellationSignal != null) {
            fingerprintHandler.cancellationSignal.cancel();

        }
    }

    @SuppressLint("ResourceAsColor")
    public void wronFingerPrint(String s)
    {
        //TextView textView=new TextView(R.id.tv_authentication_available_or_not);

        tv_authentication.setText("wrong finger entered");
        tv_authentication.setTextColor(tv_authentication.getContext().getColor(R.color.red));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_authentication.setText("Try again...");
                tv_authentication.setTextColor(tv_authentication.getContext().getColor(R.color.white));
            }
        },1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_authentication.setText("Place Your Finger on the Scanner...");
                tv_authentication.setTextColor(tv_authentication.getContext().getColor(R.color.white));
            }
        },2500);

    }
    @SuppressLint("ResourceAsColor")
    public void rightFingerPrint(String s1)
    {

        btnDoAuthenticate.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_authentication.setText("Please Wait...");
                //tv_authentication.setTextColor(R.color.white);
                tv_authentication.setTextColor(tv_authentication.getContext().getColor(R.color.white));

            }
        },1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_authentication.setText("Authentication succeeded...");
                tv_authentication.setTextColor(tv_authentication.getContext().getColor(R.color.white));

            }
        },2500);




    }
}