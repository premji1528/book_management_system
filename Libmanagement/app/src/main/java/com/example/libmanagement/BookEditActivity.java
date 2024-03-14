package com.example.libmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookEditActivity extends AppCompatActivity {
    Button btn_back, btn_update, btn_getbook;
    EditText edtxt_bookname, edtxt_booktitle, edtxt_code, edtxt_department, edtxt_category, edtxt_numberofbooks;
    String bookname, booktitle, code, department, category, numbooks;
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;
    Spinner sp_category;
    ArrayAdapter<String> categoryadapter;
    public String TAG = BookEditActivity.class.getSimpleName();
    int numberofBooks = 0;

    String categoryList[] = BookDBProcess.GetBookCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedit);
        btn_back = findViewById(R.id.btn_back);
        btn_update = findViewById(R.id.btn_update);
        btn_getbook = findViewById(R.id.btn_getbook);
        edtxt_bookname = findViewById(R.id.edtxt_bookcode);
        edtxt_booktitle = findViewById(R.id.edtxt_booktitle);
        edtxt_code = findViewById(R.id.edtxt_bookcode);
        sp_category = findViewById(R.id.sp_category);
        edtxt_department = findViewById(R.id.edtxt_department);
        edtxt_numberofbooks = findViewById(R.id.edtxt_numberofbooks);
        categoryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        sp_category.setAdapter(categoryadapter);
        btn_update.setEnabled(false);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookEditActivity.this, BookManageActivity.class);
                startActivity(intent);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookname = edtxt_bookname.getText().toString().trim();
                category = sp_category.getSelectedItem().toString();
                booktitle = edtxt_booktitle.getText().toString().trim();
                department = edtxt_department.getText().toString().trim();
                numbooks = edtxt_numberofbooks.getText().toString();

                if (bookname.equals("") || booktitle.equals("") || category.equals("") || department.equals("") || numbooks.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    numberofBooks = Integer.parseInt(numbooks);
                    try {
                        db.beginTransaction();
                        db.execSQL("update bookinfo set bookname='" + bookname + "'," +
                                "booktitle='" + booktitle + "',category='" + category + "',department='" + category + "',numberofbooks="+numberofBooks +"   where bookcode ='" + code + "' ");
                        db.setTransactionSuccessful();
                        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    }finally {
                        db.endTransaction();
                    }
                }
            }
        });
        btn_getbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_update.setEnabled(false);
                edtxt_bookname.setText("");
                edtxt_booktitle.setText("");
                edtxt_department.setText("");
                edtxt_numberofbooks.setText("");
               // sp_category.setSelection(0);
                code = edtxt_code.getText().toString().trim();
                if (code.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Code ", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String[] columns = {"bookname", "booktitle", "category", "department", "numberofbooks"};
                        cursor = db.query("bookinfo", columns, "bookcode=?", new String[]{code}, null, null, null);
                        int c = cursor.getCount();
                        if (c > 0) {
                            int i = 0;
                            int nameindex = cursor.getColumnIndex("bookname");
                            int titleindex = cursor.getColumnIndex("booktitle");
                            int categoryindex = cursor.getColumnIndex("category");
                            int departmentindex = cursor.getColumnIndex("department");
                            int numberofbookindex = cursor.getColumnIndex("numberofbooks");
                            while (cursor.moveToNext()) {
                                bookname = cursor.getString(nameindex);
                                booktitle = cursor.getString(titleindex);
                                department = cursor.getString(departmentindex );
                                category = cursor.getString(categoryindex);
                                numbooks = cursor.getString(numberofbookindex);
                                i++;
                            }
                            sp_category.setSelection(categoryadapter.getPosition(category));
                            edtxt_bookname.setText(bookname);
                            edtxt_booktitle.setText(booktitle);
                            edtxt_department.setText(department);
                            edtxt_numberofbooks.setText(numbooks);
                            btn_update.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "Retrieved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Book code not found", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    }finally {
                      //  db.endTransaction();
                    }
                }
            }
        });
    }
}