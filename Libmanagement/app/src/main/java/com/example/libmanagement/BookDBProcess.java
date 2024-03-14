package com.example.libmanagement;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BookDBProcess {
    SQLiteDatabase db;
    static String msg;
    public SQLiteDatabase DbCreateDB() {
        try {

            db= SQLiteDatabase.openDatabase(
                    "data/data/com.example.libmanagement/bookDB.db",
                    null,
                    SQLiteDatabase.CREATE_IF_NECESSARY) ;
            DbCreateTable();

            return db;

        }catch(SQLException e) {
            return null;
        }
    }


    private void DbCreateTable() {

        db.beginTransaction();
        try{

            db.execSQL("create table userinfo(uid integer primary key AUTOINCREMENT ,uname text,upass text,uemail text,uphone text); ");
            db.execSQL("create table bookinfo(id integer primary key AUTOINCREMENT ,bookname text,booktitle text,bookcode text,category text,department text,numberofbooks integer," +
                    "createdon text,updatedon text, createdby text,imagepath text,imagename text); ");
            db.execSQL("create table booktransaction(id integer primary key AUTOINCREMENT ,bookcode text,studentemail text,requestdate text," +
                    "createdon text,updatedon text, createdby text,isreturned text); ");
            //commit your changes
            db.setTransactionSuccessful();
            //Toast.makeText(getApplicationContext(), "table created", 5).show();

        } catch(SQLException e1) {
            //Toast.makeText(getApplicationContext(), e1.getMessage(), 2).show();

        }
        finally{
            //finish transaction processing
            db.endTransaction();
        }

    } //table crate

    static public  String[] GetBookCategories() {
        String []categories={"","c&c++","java","dot.net","webdesigning","mobileApp"};
        return categories;
    }

}//class


