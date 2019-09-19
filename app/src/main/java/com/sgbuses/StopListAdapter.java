package com.sgbuses;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.sgbuses.FavouriteList.BUSFAVList;

public class StopListAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;
    String Stop_name;

    ArrayList<String> Servic_No = new ArrayList<String>();

    // View lookup cache
    private static class ViewHolder {

        TextView Serv_No;
        TextView Bus_T1;
        TextView Bus_T2;
        TextView Bus_T3;
        ImageView favbtn;

    }

    public StopListAdapter(ArrayList<DataModel> data, String route_name, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.Stop_name = route_name;


    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                break;
            case R.id.favrow:
                Snackbar.make(v, "Fav btn is clicked " , Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.Serv_No = (TextView) convertView.findViewById(R.id.name);
            viewHolder.Bus_T1 = (TextView) convertView.findViewById(R.id.type);
            viewHolder.Bus_T2 = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.Bus_T3 = (TextView) convertView.findViewById(R.id.item_info);
            viewHolder.favbtn = (ImageView)convertView.findViewById(R.id.favrow);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.Serv_No.setText(dataModel.getServ_no());

        int count =  BUSFAVList.size();


        String apibus = dataModel.getServ_no().trim();






         if (Integer.parseInt(dataModel.getBus_inter1()) <=0)
        {
            viewHolder.Bus_T1.setText("Arr");
        }
        else {
            viewHolder.Bus_T1.setText(dataModel.getBus_inter1()+" Min");
        }

        if (dataModel.getBus_inter2().equals(""))
        {
            viewHolder.Bus_T2.setText("N.A");

        }
        else {
            viewHolder.Bus_T2.setText(dataModel.getBus_inter2() +" Min");
        }
        if (dataModel.getBus_inter3().equals(""))
        {
            viewHolder.Bus_T3.setText("N.A");

        }
        else {
            viewHolder.Bus_T3.setText(dataModel.getBus_inter3() + " Min");
        }

        if (BUSFAVList.contains(apibus))
        {
            viewHolder.favbtn.setImageResource(R.drawable.ic_fav);
        }


        viewHolder.favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                viewHolder.favbtn.setImageResource(R.drawable.ic_fav);
                FirebaseDatabase database;
                DatabaseReference mRef;
                database =  FirebaseDatabase.getInstance();
                mRef =  database.getReference().child("FavCat").child(Stop_name);
                String mGroupId = mRef.push().getKey();
                BusFirebase user = new BusFirebase( dataModel.getServ_no(), dataModel.getBus_inter1(), dataModel.getBus_inter2());
                mRef.child(mGroupId).setValue(user);
                Toast.makeText(mContext, "Fav btn is clicked", Toast.LENGTH_LONG).show();

                Snackbar.make(view, dataModel.getServ_no()+"\n"+dataModel.getBus_inter1()+" API: "
                        +dataModel.getBus_inter2(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
        return convertView;
    }
}
