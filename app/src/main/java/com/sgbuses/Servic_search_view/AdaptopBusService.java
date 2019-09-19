package com.sgbuses.Servic_search_view;


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

public class AdaptopBusService extends ArrayAdapter<String> {

    private final Activity context;


    private ArrayList<String> st_no =new ArrayList<String>();
    private ArrayList<String> st_des =new ArrayList<String>();
    private ArrayList<String> st_destinat =new ArrayList<String>();


    public AdaptopBusService(Activity context, ArrayList<String> st_no, ArrayList<String> st_des, ArrayList<String> dest) {
        super(context, R.layout.mylist, st_no);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.st_no=st_no;
        this.st_des=st_des;
        this.st_destinat = dest;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.servic_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        TextView textView_dest = (TextView) rowView.findViewById(R.id.dest);

        titleText.setText(st_no.get(position));
//        imageView.setImageResource(imgid[position]);
        subtitleText.setText(st_des.get(position));

        textView_dest.setText(st_destinat.get(position));

        return rowView;

    };
}