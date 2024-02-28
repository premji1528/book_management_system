package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedit);
        Button btn_back = findViewById(R.id.btn_back);
        Button btn_update = findViewById(R.id.btn_update);
        btn_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookEditActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
        btn_update .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
            }
        });


    }
}