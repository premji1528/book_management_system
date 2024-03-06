package com.example.libmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    String userName, passWord;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    boolean isSuccess = false;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_back = findViewById(R.id.btn_back);
        EditText editText_Name = findViewById(R.id.editText_Name);
        EditText editText_Password = findViewById(R.id.editText_Password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSuccess = false;
                userName = editText_Name.getText().toString().trim();
                passWord = editText_Password.getText().toString().trim();
                if (userName.toString().equals("") || passWord.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else if (userName.toString().equals("student") && passWord.toString().equals("student")) {
                    intent = new Intent(LoginActivity.this, BookListActivity.class);
                    isSuccess = true;
                } else if (userName.toString().equals("admin") && passWord.toString().equals("admin")) {
                    intent = new Intent(LoginActivity.this, BookManageActivity.class);
                    isSuccess = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Enter correct credentials", Toast.LENGTH_SHORT).show();
                }
                if (isSuccess) {
                    sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    ed = sp.edit();
                    ed.putString("username", userName);
                    ed.commit();
                    startActivity(intent);
                }else {
                    sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    ed = sp.edit();
                    ed.clear();
                    ed.apply();
                    finish();
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