package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookReturnActivity extends AppCompatActivity {
    Button btn_back,btn_return;
    EditText edtxt_bookname, edtxt_studentname,edtxt_returndate;
    String bookname, studentname,returndate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookreturn);
         btn_back = findViewById(R.id.btn_back);
         btn_return = findViewById(R.id.btn_return);
         edtxt_bookname = findViewById(R.id.edtxt_bookname);
         edtxt_studentname =findViewById(R.id.edtxt_studentname);
         edtxt_returndate =findViewById(R.id.edtxt_returndate);
         btn_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookReturnActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                bookname = edtxt_bookname.getText().toString().trim();
                studentname = edtxt_studentname.getText().toString().trim();
                returndate = edtxt_returndate.getText().toString().trim();
                if(bookname.equals("") || studentname.equals("") || returndate.equals("")) {
                    Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Returned successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}