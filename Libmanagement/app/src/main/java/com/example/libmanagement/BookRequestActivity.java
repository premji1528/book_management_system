package com.example.libmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookrequest);
        Button btn_back = findViewById(R.id.btn_back);
        Button btn_request = findViewById(R.id.btn_request);
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
                Toast.makeText(getApplicationContext(),"Request process completed",Toast.LENGTH_LONG).show();
            }
        });
    }
}