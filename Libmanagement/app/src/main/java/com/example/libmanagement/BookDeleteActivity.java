package com.example.libmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDeleteActivity extends AppCompatActivity {
    Button btn_back,btn_delete;
    EditText edtxt_code;
    String code;
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdelete);
         btn_back = findViewById(R.id.btn_back);
         btn_delete = findViewById(R.id.btn_delete);
         edtxt_code = findViewById(R.id.edtxt_code);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
         btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookDeleteActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
        btn_delete .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                code = edtxt_code.getText().toString().trim();
                if(code.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                }else {
                    try{
                        db.beginTransaction();
                        db.execSQL("delete from bookinfo  where bookcode ='" + code + "' ");
                        db.setTransactionSuccessful();
                        Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_LONG).show();
                    }catch(Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    }finally {
                        db.endTransaction();
                    }
                }
            }
        });

    }
}