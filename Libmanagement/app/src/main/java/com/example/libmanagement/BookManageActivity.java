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
    Cursor cursor;

    SQLiteDatabase db;
    Bitmap bitmap;
    ImageView selectedBookImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manage);
        Drawable drawable = getResources().getDrawable(R.drawable.bookicon);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        Button btn_save = findViewById(R.id.btn_save);
       // selectedBookImage = findViewById(R.id.book_image);
//        try {
//            database = new BookDBProcess();
//            db = database.DbCreateDB();
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ContextWrapper cw = new ContextWrapper(getApplicationContext());
//                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//                String uniqueID = UUID.randomUUID().toString();
//                Long tsLong = System.currentTimeMillis() / 1000;
//                String fileName = uniqueID + "-" + tsLong.toString();
//                File file = new File(directory, fileName.toString() + ".jpg");
//                if (!file.exists()) {
//                    Log.d("path", file.toString());
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                        fos.flush();
//                        fos.close();
//                    } catch (java.io.IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                //image load
                imageChooser();
            }
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                   // selectedBookImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}