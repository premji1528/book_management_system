package com.example.libmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;

public class BookListActivity extends AppCompatActivity {
    String userName;
    SharedPreferences sp;
    SQLiteDatabase db;
    BookDBProcess database;
    Cursor cursor;
    public String TAG = BookListActivity.class.getSimpleName();
    NavigationView navigationView;
    int bookListLength=0;
    String bookNames[] =new String[bookListLength];
    String bookCategory[] = new String[bookListLength];
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Integer imageid[] =new Integer[bookListLength];
    ListView listView;
    CustomImgTextAdapter customBookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_request = findViewById(R.id.btn_request);
        Button btn_return = findViewById(R.id.btn_return);
        listView=(ListView)findViewById(R.id.imgbook_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        try {
            database = new BookDBProcess();
            db = database.DbCreateDB();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setBookList("");
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"You Selected "+bookNames[position]+ " as Book",Toast.LENGTH_SHORT).show();        }
        });
        sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        userName = (sp.getString("username", ""));
        if(userName.equals("admin@gmail.com")) {
            btn_login.setText("Back");
            btn_request.setVisibility(View.GONE);
            btn_return.setVisibility(View.GONE);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(userName.equals("admin@gmail.com")) {
                    Intent intent = new Intent(BookListActivity.this, BookManageActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(BookListActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookListActivity.this, BookRequestActivity.class);
                startActivity(intent);
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BookListActivity.this, BookReturnActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               if(menuItem.getItemId() == R.id.nav_java) {
                setBookList("java");
               }else if(menuItem.getItemId() == R.id.nav_cplus) {
                   setBookList("c&c++");
               }else if(menuItem.getItemId() == R.id.nav_dotnet) {
                   setBookList("dot.net");
               }else if(menuItem.getItemId() == R.id.nav_webdesigning) {
                   setBookList("webdesigning");
               }else if(menuItem.getItemId() == R.id.nav_mobileapp) {
                   setBookList("mobileApp");
               }else if(menuItem.getItemId() == R.id.nav_all) {
                   setBookList("");
               }
                return false;
            }
        });
    }
    public void setBookList(String category) {
        listView.setVisibility(View.GONE);
        try {
            String []columns={"bookname" ,"booktitle","bookcode","category" ,"department","numberofbooks",
                    "createdon","updatedon","createdby","imagepath","imagename"};
            if(category.equals(""))
            cursor =db.query("bookinfo", columns,  null,null, null, null,null);
            else
                cursor =db.query("bookinfo", columns,  "category=?",new String[]{category}, null, null,null);
            bookListLength =cursor.getCount();
            if(bookListLength >0) {
                bookNames =new String[bookListLength];
                bookCategory =new String[bookListLength];
                imageid =new Integer[bookListLength];
                int i=0;
                int nameindex= cursor.getColumnIndex("bookname");
                int categoryindex= cursor.getColumnIndex("category");
                int codeindex= cursor.getColumnIndex("bookcode");
                int titleindex= cursor.getColumnIndex("booktitle");
                int numberofbooksindex= cursor.getColumnIndex("numberofbooks");
                while(cursor.moveToNext()) {
                    bookNames[i] = cursor.getString( nameindex) +"-" + cursor.getString(titleindex)+"-" + cursor.getString(codeindex)+"-" + cursor.getString(numberofbooksindex);
                    bookCategory[i] = cursor.getString( categoryindex);
                    imageid[i] = R.drawable.bookicon;
                    ++i;
                }
                customBookList = new CustomImgTextAdapter(this, bookNames, bookCategory, imageid);
                listView.setAdapter(customBookList);
                listView.setVisibility(View.VISIBLE);
            }else {
                Toast.makeText(getApplicationContext(), "No books found", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}