package com.example.libmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
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
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;
    public String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn_back = findViewById(R.id.btn_back);
        btn_registration = findViewById(R.id.btn_registration);
        edtxt_name = findViewById(R.id.edtxt_bookcode);
        edtxt_password = findViewById(R.id.edtxt_password);
        edtxt_email = findViewById(R.id.edtxt_email);
        edtxt_phone = findViewById(R.id.edtxt_phone);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
            Toast.makeText(getApplicationContext(), "DBInited", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

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
                Log.v(TAG, name);

                if (name.equals("") || password.equals("") || email.equals("") || phone.equals("")) {
                    Log.i(TAG, "Fill all fields");
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                } else {

                    try {

                        String []columns={"uemail"};
                        cursor =db.query("userinfo", columns,  "uemail=?", new String[]{email}, null, null,null);
                        int c=cursor.getCount();
                        if(c>0) {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_LONG).show();
                        }else {
                            db.beginTransaction();
                            db.execSQL("insert into userinfo(uname,upass,uemail,uphone)"
                                    + "values('" + name + "', '" + password + "','" + email + "','" + phone + "')");

                            db.setTransactionSuccessful();
                            Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                            intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    } catch (SQLiteException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        Log.v(TAG,e.toString());
                    } finally {
                        db.endTransaction();
                    }
                }
            }
        });
    }

}