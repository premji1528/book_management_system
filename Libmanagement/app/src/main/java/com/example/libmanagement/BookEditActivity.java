package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookEditActivity extends AppCompatActivity {
    Button btn_back,btn_update;
    EditText edtxt_bookname,edtxt_code,edtxt_department,edtxt_category;
    String bookname,code,department,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedit);
        btn_back = findViewById(R.id.btn_back);
        btn_update = findViewById(R.id.btn_update);
        edtxt_bookname = findViewById(R.id.edtxt_name);
        edtxt_code = findViewById(R.id.edtxt_code);
        edtxt_category = findViewById(R.id.edtxt_category);
        edtxt_department = findViewById(R.id.edtxt_department);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookEditActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                bookname = edtxt_bookname.getText().toString().trim();
                code = edtxt_code.getText().toString().trim();
                category = edtxt_category.getText().toString().trim();
                department = edtxt_department.getText().toString().trim();
                if(bookname.equals("") || code.equals("") || category.equals("") || department.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}