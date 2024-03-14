package com.example.libmanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class BookRequestActivity extends AppCompatActivity {
    SharedPreferences sp;
    Button btn_back, btn_request;
    EditText edtxt_bookcode, edtxt_studentname, edtxt_requestdate, edtxt_department;
    String userName, bookcode, studentemail, requestdate, department,numbooks;
    SQLiteDatabase db;
    BookDBProcess database;
    int numberOfBooks =0;
    Cursor cursor;
    String isreturned ="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookrequest);
        btn_back = findViewById(R.id.btn_back);
        btn_request = findViewById(R.id.btn_request);
        edtxt_bookcode = findViewById(R.id.edtxt_bookcode);
        edtxt_studentname = findViewById(R.id.edtxt_studentname);
        edtxt_requestdate = findViewById(R.id.edtxt_requestdate);
        sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        userName = (sp.getString("username", ""));
        edtxt_studentname.setText(userName);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookRequestActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
        edtxt_requestdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        BookRequestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                edtxt_requestdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                datePickerDialog.show();
            }
        });
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookcode = edtxt_bookcode.getText().toString().trim();
                studentemail= edtxt_studentname.getText().toString().trim();

                requestdate = edtxt_requestdate.getText().toString().trim();
                if (bookcode.equals("") || studentemail.equals("") || requestdate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all required fileds", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String[] columns = {"numberofbooks"};
                        cursor = db.query("bookinfo", columns, "bookcode=?", new String[]{bookcode}, null, null, null);
                        int c = cursor.getCount();
                        if (c > 0) {
                            int i = 0;
                            int numberofbooksindex = cursor.getColumnIndex("numberofbooks");
                            while (cursor.moveToNext()) {
                                numbooks = cursor.getString(numberofbooksindex);
                                i++;
                            }
                            numberOfBooks = Integer.parseInt(numbooks);
                            if(numberOfBooks >0) {
                                numberOfBooks = numberOfBooks -1;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:MM:SS");
                                String createdDate = sdf.format(new Date()).toString();
                                String username ="Admin";
                                db.beginTransaction();
                                db.execSQL("update bookinfo set numberofbooks="+numberOfBooks+"   where bookcode ='" + bookcode + "' ");
                                db.execSQL("insert into booktransaction(bookcode ,studentemail ,requestdate ,createdon,updatedon,createdby,isreturned)" +
                                        " values('" + bookcode + "', '" +studentemail+ "','" +requestdate+ "','"+createdDate+"','"+createdDate+"','"+username+"','"+isreturned+"')");
                                db.setTransactionSuccessful();
                               Toast.makeText(getApplicationContext(), "Request completed successfully", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Request book is not available", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Book code not found", Toast.LENGTH_LONG).show();
                        }
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