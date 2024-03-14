package com.example.libmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    String email, passWord;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    boolean isSuccess = false;
    private static final String TAG = LoginActivity.class.getSimpleName();
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_back = findViewById(R.id.btn_back);
        EditText edtxt_email = findViewById(R.id.edtxt_email);
        EditText edtxt_password = findViewById(R.id.edtxt_password);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
            Toast.makeText(getApplicationContext(), "DBInited", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSuccess = false;
                email = edtxt_email.getText().toString().trim();
                passWord = edtxt_password.getText().toString().trim();
                if (email.toString().equals("") || passWord.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.toString().equals("admin@gmail.com") ) {
                    try {
                        String []columns={"uemail"};
                        cursor =db.query("userinfo", columns,  "uemail=? AND upass =?", new String[]{email,passWord}, null, null,null);
                        int c=cursor.getCount();
                        if(c >0) {
                            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                            intent = new Intent(LoginActivity.this, BookListActivity.class);
                            isSuccess = true;
                        }else  {
                            Toast.makeText(getApplicationContext(), "Enter correct credentials", Toast.LENGTH_SHORT).show();
                            isSuccess = false;
                        }
                    }catch(SQLiteException e) {
                        isSuccess = false;
                        Toast.makeText(getApplicationContext(), "DB Error", Toast.LENGTH_SHORT).show();
                    }
                } else if (email.toString().equals("admin@gmail.com") && passWord.toString().equals("admin")) {
                    intent = new Intent(LoginActivity.this, BookManageActivity.class);
                    isSuccess = true;
                }
                if (isSuccess) {
                    sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    ed = sp.edit();
                    ed.putString("username", email);
                    ed.commit();
                    startActivity(intent);
                }else {
                    sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    ed = sp.edit();
                    ed.clear();
                    ed.apply();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}