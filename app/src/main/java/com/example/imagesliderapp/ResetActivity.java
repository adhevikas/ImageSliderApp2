package com.example.imagesliderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResetActivity extends AppCompatActivity {

    EditText et_username,et_old_password,et_new_password,et_confirm_password;
    AppCompatButton btn_reset_password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset);

        et_username=findViewById(R.id.et_reset_username);
        et_old_password=findViewById(R.id.edt_reset_old_password);
        et_new_password=findViewById(R.id.edt_reset_new_password);
        et_confirm_password=findViewById(R.id.edt_reset_Confirm_password);
        btn_reset_password=findViewById(R.id.btn_reset_reset);

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=et_username.getText().toString();
                String old_password=et_old_password.getText().toString();
                String new_password=et_new_password.getText().toString();
                String confirm_password=et_confirm_password.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    et_username.setError("username is required");
                }
                else  if (TextUtils.isEmpty(old_password)) {
                    et_old_password.setError("old password is required");
                }
                else if (TextUtils.isEmpty(new_password)) {
                    et_new_password.setError("new password is required");
                }
                else if (TextUtils.isEmpty(confirm_password)) {
                    et_confirm_password.setError("confirm password is required");
                } else if (!new_password.matches(confirm_password)) {
                    Toast.makeText(ResetActivity.this, "Both password are not match", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(ResetActivity.this,LoginActivity.class));
                }
            }
        });








    }
}