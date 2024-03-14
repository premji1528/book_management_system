package com.example.libmanagement;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomImgTextAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] bookinfo;
    private final String[] category;
    private final Integer[] imgid;

    public CustomImgTextAdapter(Activity context, String[] bookinfo, String[] category, Integer[] imgid) {
        super(context, R.layout.row_item, bookinfo);        // TODO Auto-generated constructor stub

        this.context = context;
        this.bookinfo = bookinfo;
        this.category = category;
        this.imgid = imgid;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_item, null, true);

        TextView bookname = (TextView) rowView.findViewById(R.id.bookname);
        TextView booktitle = (TextView) rowView.findViewById(R.id.booktitle);
        TextView numberofbooks = (TextView) rowView.findViewById(R.id.numberofbooks);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView categoryText = (TextView) rowView.findViewById(R.id.category);
        TextView bookcode = (TextView) rowView.findViewById(R.id.bookcode);

        String bookDetails[]= bookinfo[position].split("-");

        bookname.setText("Name : " +bookDetails[0]);
        booktitle.setText("Title : " +bookDetails[1]);
        bookcode.setText("Code : " +bookDetails[2]);
        categoryText.setText("Category : " +category[position]);
        numberofbooks.setText("Numbers : " +bookDetails[3]);
        imageView.setImageResource(imgid[position]);
        return rowView;

    }

    ;
}