package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    String name, password, email, phone;
    EditText edtxt_name, edtxt_password, edtxt_email, edtxt_phone;
    Button btn_back, btn_registration;
    Intent intent;
    public String TAG=RegistrationActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn_back = findViewById(R.id.btn_back);
        btn_registration = findViewById(R.id.btn_registration);
        edtxt_name = findViewById(R.id.edtxt_name);
        edtxt_password = findViewById(R.id.edtxt_password);
        edtxt_email = findViewById(R.id.edtxt_email);
        edtxt_phone = findViewById(R.id.edtxt_phone);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtxt_name.getText().toString().trim();

                password = edtxt_password.getText().toString().trim();
                email = edtxt_email.getText().toString().trim();
                phone = edtxt_phone.getText().toString().trim();
                Log.v(TAG,name);

                if (name.equals("") ||  password.equals("") || email.equals("") || phone.equals("")) {
                    Log.i(TAG,"Fill all fields");
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}