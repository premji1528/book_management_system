package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookRequestActivity extends AppCompatActivity {

    Button btn_back,btn_request;
    EditText edtxt_bookname,edtxt_studentname,edtxt_requestdate,edtxt_department;
    String userName, bookname,studentname,requestdate,department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookrequest);
         btn_back = findViewById(R.id.btn_back);
         btn_request = findViewById(R.id.btn_request);
         edtxt_bookname = findViewById(R.id.edtxt_bookname);
         edtxt_studentname = findViewById(R.id.edtxt_studentname);
         edtxt_requestdate = findViewById(R.id.edtxt_requestdate);
         edtxt_department = findViewById(R.id.edtxt_department);
         btn_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookRequestActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
        btn_request .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                bookname = edtxt_bookname.getText().toString().trim();
                studentname = edtxt_studentname.getText().toString().trim();
                department= edtxt_department.getText().toString().trim();
                requestdate = edtxt_requestdate.getText().toString().trim();
                if(bookname.equals("")|| studentname.equals("")|| department.equals("")|| requestdate.equals("")) {
                    Toast.makeText(getApplicationContext(),"Fill all required fileds",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Request process completed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}