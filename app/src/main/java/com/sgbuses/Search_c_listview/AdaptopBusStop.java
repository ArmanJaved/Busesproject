package com.sgbuses.Search_c_listview;


import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgbuses.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptopBusStop extends ArrayAdapter<String> {

    private final Activity context;


    private ArrayList<String> st_no =new ArrayList<String>();
    private ArrayList<String> st_des =new ArrayList<String>();


    public AdaptopBusStop(Activity context, ArrayList<String> st_no,ArrayList<String> st_des) {
        super(context, R.layout.mylist, st_no);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.st_no=st_no;
        this.st_des=st_des;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(st_no.get(position));
//        imageView.setImageResource(imgid[position]);
        subtitleText.setText(st_des.get(position));

        return rowView;

    };
}