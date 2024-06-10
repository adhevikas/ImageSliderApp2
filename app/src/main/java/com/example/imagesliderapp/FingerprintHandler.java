package com.example.imagesliderapp;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.Toast;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    CancellationSignal cancellationSignal;
    private FingerPrintAuthenticationActivity activity;

    public FingerprintHandler(FingerPrintAuthenticationActivity activity) {

        this.activity = activity;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
   //     Toast.makeText(activity, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
      //  Toast.makeText(activity, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {

//        FingerPrintAuthenticationActivity fingerPrintAuthenticationActivity=new FingerPrintAuthenticationActivity();
//        fingerPrintAuthenticationActivity.wronFingerPrint("Authentication Successfully");
//
//        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_LONG).show();

        activity.wronFingerPrint("Wrong Fingerprint Entered");

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
       // Toast.makeText(activity, "Authentication succeeded.", Toast.LENGTH_LONG).show();

//        FingerPrintAuthenticationActivity fingerPrintAuthenticationActivity=new FingerPrintAuthenticationActivity();
//        fingerPrintAuthenticationActivity.rightFingerPrint("Authentication Successfully");

        activity.rightFingerPrint("Authentication succeeded");
        boolean handler=new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        activity.startActivity(new Intent(activity, HomeActivity.class));
                        activity.finish();
                    }
                },3000);

    }
}
