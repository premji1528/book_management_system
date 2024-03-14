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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class BookReturnActivity extends AppCompatActivity {
    Button btn_back, btn_return;
    SharedPreferences sp;
    EditText edtxt_bookcode, edtxt_studentname, edtxt_returndate;
    String bookcode, studentname,studentemail, returndate, numbooks;

    Cursor cursor;
    BookDBProcess database;
    SQLiteDatabase db;
    int numberOfBooks = 0;
    String userName;
    String isreturned ="true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookreturn);
        btn_back = findViewById(R.id.btn_back);
        btn_return = findViewById(R.id.btn_return);
        edtxt_bookcode = findViewById(R.id.edtxt_bookcode);
        edtxt_studentname = findViewById(R.id.edtxt_studentname);
        edtxt_returndate = findViewById(R.id.edtxt_returndate);
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
                Intent intent = new Intent(BookReturnActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
        edtxt_returndate.setOnClickListener(new View.OnClickListener() {
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
                        BookReturnActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                edtxt_returndate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                datePickerDialog.show();
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookcode = edtxt_bookcode.getText().toString().trim();
                studentemail = edtxt_studentname.getText().toString().trim();
                returndate = edtxt_returndate.getText().toString().trim();
                if (bookcode.equals("") || studentemail.equals("") || returndate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String[] columns = {"bookcode","studentemail","isreturned"};
                        cursor = db.query("booktransaction", columns, "bookcode=? AND studentemail =? AND isreturned =?", new String[]{bookcode,studentemail,"false"}, null, null, null);
                        int c = cursor.getCount();
                        if(c>0) {
                            String[] bcolumns = {"numberofbooks"};
                            cursor = db.query("bookinfo", bcolumns, "bookcode=?", new String[]{bookcode}, null, null, null);
                            int c1 = cursor.getCount();
                            if (c1 > 0) {
                                int i = 0;
                                int numberofbooksindex = cursor.getColumnIndex("numberofbooks");
                                while (cursor.moveToNext()) {
                                    numbooks = cursor.getString(numberofbooksindex);
                                    i++;
                                }
                                numberOfBooks = Integer.parseInt(numbooks);
                                numberOfBooks = numberOfBooks +1;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:MM:SS");
                                String createdDate = sdf.format(new Date()).toString();
                                String username ="Admin";
                                db.beginTransaction();
                                db.execSQL("update bookinfo set numberofbooks="+numberOfBooks+"   where bookcode ='" + bookcode + "' ");
                                db.execSQL("update  booktransaction set isreturned='"+isreturned+"'   where bookcode ='" + bookcode + "' AND  studentemail ='" +studentemail + "' AND  isreturned ='false' ");
                                db.setTransactionSuccessful();
                            }else {
                                Toast.makeText(getApplicationContext(), "Book details not found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Book details not found", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getApplicationContext(), "Returned successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                    }finally {
                        db.endTransaction();
                    }
                }
            }
        });
    }
}