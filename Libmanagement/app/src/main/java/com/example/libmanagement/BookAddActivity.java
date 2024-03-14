package com.example.libmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class BookAddActivity extends AppCompatActivity   {
    Button btn_back, btn_add;
    EditText edtxt_name,edtxt_title, edtxt_code, edtxt_department,edtxt_numberofbooks;
    Spinner sp_category;
    String bookname,booktitle, category, code, department,username,imagePath,imageName,numbooks;
    int categoryid;
    int numberofBooks =0;
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;
    ArrayAdapter<String> categoryadapter;
    ImageView imgview_bookimage;
    public String TAG = BookAddActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookadd);
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        edtxt_name = findViewById(R.id.edtxt_bookcode);
        edtxt_code = findViewById(R.id.edtxt_code);
        sp_category = findViewById(R.id.sp_category);
        edtxt_title = findViewById(R.id.edtxt_title);
        sp_category.setPrompt("Cateory");
        edtxt_department = findViewById(R.id.edtxt_department);
        edtxt_numberofbooks = findViewById(R.id.edtxt_numberofbooks);
        imgview_bookimage = findViewById(R.id.imgview_bookimage);
        categoryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BookDBProcess.GetBookCategories());
        sp_category.setAdapter(categoryadapter);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookAddActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
     sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String bookCategoryList[] = BookDBProcess.GetBookCategories();
            String selectedCategory = bookCategoryList[position].toString();
            if(selectedCategory.equals("c&c++")) {
                imgview_bookimage.setImageResource(R.drawable.cplusguide);
            }else if(selectedCategory.equals("java")) {
                imgview_bookimage.setImageResource(R.drawable.javaguide);
            }else if(selectedCategory.equals("dot.net")) {
                imgview_bookimage.setImageResource(R.drawable.dotnetguide);
            }else if(selectedCategory.equals("mobileApp")) {
                imgview_bookimage.setImageResource(R.drawable.mobiledevelopment);
            }else if(selectedCategory.equals("webdesigning")) {
                 imgview_bookimage.setImageResource(R.drawable.webdesign);
             }else {
                imgview_bookimage.setImageResource(R.drawable.book);
            }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookname = edtxt_name.getText().toString().trim();
                booktitle = edtxt_title.getText().toString().trim();
                category = sp_category.getSelectedItem().toString();
                code = edtxt_code.getText().toString().trim();
                department = edtxt_department.getText().toString().trim();
                numbooks = edtxt_numberofbooks.getText().toString();
                if(bookname.equals("") || booktitle.equals("")  || category.equals("")|| code.equals("")|| department.equals("") || numbooks.equals(("")) ) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                }else {
                   try {
                        String []columns={"bookcode"};
                        cursor =db.query("bookinfo", columns,  "bookcode=?", new String[]{code}, null, null,null);
                        int c=cursor.getCount();
                        if(c>0) {
                            Toast.makeText(getApplicationContext(), "Book code already exists", Toast.LENGTH_LONG).show();
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:MM:SS");
                            String createdDate = sdf.format(new Date()).toString();
                            username ="Admin";
                            imagePath="Test";
                            imageName="Test";
                            numberofBooks = Integer.parseInt(numbooks);
                            db.beginTransaction();
                            db.execSQL("insert into bookinfo(bookname ,booktitle ,bookcode,category ,department,numberofbooks,createdon,updatedon,createdby," +
                                    "imagepath,imagename)"
                                    + "values('" + bookname + "', '" +booktitle+ "','" +code+ "','" + category + "','" + department + "'," + numberofBooks+ "," +
                                    "'"+createdDate+"','"+createdDate+"','"+username+"','"+imagePath+"','"+imageName+"')");

                            db.setTransactionSuccessful();
                            Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLiteException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        Log.v(TAG,e.toString());
                    } finally {
                        db.endTransaction();
                    }
                }
            }
        });
    }


}