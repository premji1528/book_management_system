package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookAddActivity extends AppCompatActivity {
    Button btn_back, btn_add;
    EditText edtxt_name, edtxt_category, edtxt_code, edtxt_department;
    String bookname, category, code, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookadd);
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        edtxt_name = findViewById(R.id.edtxt_name);
        edtxt_code = findViewById(R.id.edtxt_code);
        edtxt_category = findViewById(R.id.edtxt_category);
        edtxt_department = findViewById(R.id.edtxt_department);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookAddActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookname = edtxt_name.getText().toString().trim();
                category = edtxt_category.getText().toString().trim();
                code = edtxt_code.getText().toString().trim();
                department = edtxt_department.getText().toString().trim();
                if(bookname.equals("") || category.equals("")|| code.equals("")|| department.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}