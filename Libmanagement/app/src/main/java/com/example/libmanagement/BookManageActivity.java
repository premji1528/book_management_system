package com.example.libmanagement;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class BookManageActivity extends AppCompatActivity {
    private static final String TAG = BookManageActivity.class.getSimpleName();
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manage);
      //  Button btn_view= findViewById(R.id.btn_view);
        Button btn_add = findViewById(R.id.btn_add);
        Button btn_update = findViewById(R.id.btn_update);
        Button btn_delete = findViewById(R.id.btn_delete);
        Button btn_logout = findViewById(R.id.btn_logout);

//        btn_view.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                //   Intent intent = new Intent(BookManageActivity.this, BookListActivity.class);
//                 //  startActivity(intent);
//               }
//           });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookManageActivity.this, BookAddActivity.class);
                startActivity(intent);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookManageActivity.this, BookEditActivity.class);
                startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookManageActivity.this, BookDeleteActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookManageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}