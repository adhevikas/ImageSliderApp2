package com.example.imagesliderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username,et_password,et_email,et_phone;
    AppCompatButton btn_register_now;
    TextView tv_login_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        et_username=findViewById(R.id.et_register_username);
        et_password=findViewById(R.id.edt_register_password);
        et_email=findViewById(R.id.edt_register_email);
        et_phone=findViewById(R.id.edt_register_mobile);
        btn_register_now=findViewById(R.id.btn_register_register);
        tv_login_now=findViewById(R.id.txt_register_login_now);

        tv_login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });

        btn_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Get the input values from EditText fields
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();

                // Perform validation
                if (TextUtils.isEmpty(username)) {
                    et_username.setError("Username is required");
                    return;
                }

               else if (TextUtils.isEmpty(password)) {
                    et_password.setError("Password is required");
                    return;
                }

                else if (!isValidEmail(email)) {
                    et_email.setError("Invalid email address");
                    return;
                }
                else if (!isValidPhoneNumber(phone)) {
                    et_phone.setError("Invalid phone number");
                    return;
                }
                else {
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
    private boolean isValidPhoneNumber(String phone) {
        // You can implement your own validation logic for phone number
        // For simplicity, I'm checking if the phone number has 10 digits

        return phone.length() == 10 && TextUtils.isDigitsOnly(phone);
    }
}