package com.sgbuses;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.sgbuses.FavouriteList.BUSFAVList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<DataModel> listdata;
    private String Stop_name ;
    private String Bus_Servic_No;
    private String Route_No;

    private static int TYPE_DEFAULT = 0 ;
    private static int TYPE_FAVOURITE = 1;



    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<DataModel> listdata, String stop, String bus_Servic_No, String route_No) {
        this.listdata = listdata;
        this.Stop_name = stop;
        this.Bus_Servic_No = bus_Servic_No;
        this.Route_No = route_No;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_FAVOURITE) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_item11_fav, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);

//            viewHolder.favrow.setTag("Fav");
            viewHolder.favrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(parent.getContext(), "Fav", Toast.LENGTH_LONG).show();
                }
            });

            return viewHolder;
        }else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_item11, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            viewHolder.favrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(parent.getContext(), "Un Fav", Toast.LENGTH_LONG).show();
                }
            });
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String sa = listdata.get(position).getServ_no().trim();

        if (BUSFAVList.contains(sa))
        {

            return TYPE_FAVOURITE;
        } else {

            return TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataModel myListData = listdata.get(position);

        holder.bus_ser.setText(listdata.get(position).getServ_no());


        holder.favrow.setTag("Un");

        if (listdata.get(position).getBus_inter1().equals(""))
        {
            holder.time1.setText("N.A");
        }
        else {
            holder.time1.setText(listdata.get(position).getBus_inter1()+" Min ");
        }

        if (listdata.get(position).getBus_inter2().equals(""))
        {
            holder.time2.setText("N.A");
        }
        else {
            holder.time2.setText(listdata.get(position).getBus_inter2()+" Min ");
        }


        if (listdata.get(position).getBus_inter3().equals(""))
        {
            holder.time3.setText("N.A");
        }
        else {
            holder.time3.setText(listdata.get(position).getBus_inter3()+" Min ");
        }


//        holder.favrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Toast.makeText(view.getContext(), "Fav", Toast.LENGTH_LONG).show();
//
//
//                if (holder.favrow.getTag() =="Un")
//                {
//                    Toast.makeText(view.getContext(), "Un Fav", Toast.LENGTH_LONG).show();
//                    holder.favrow.setTag("Fav");
//                }
//
//                holder.favrow.setImageResource(R.drawable.ic_fav);
//                FirebaseDatabase database;
//                DatabaseReference mRef;
//                String android_id = Settings.Secure.getString(view.getContext().getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//                database =  FirebaseDatabase.getInstance();
//                mRef =  database.getReference().child("FavCat").child(android_id).child(Stop_name);
//                String mGroupId = mRef.push().getKey();
//
//                BusFirebase user = new BusFirebase( listdata.get(position).getServ_no(),
//                        listdata.get(position).getBus_inter1(),
//                        listdata.get(position).getBus_inter2());
//                mRef.child(mGroupId).setValue(user);
////                Toast.makeText(view.getContext(), "Fav btn is clicked", Toast.LENGTH_LONG).show();
//
//            }
//        });



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getServ_no() +
//                        myListData.getBus_inter1(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), BusStopMapMarker.class);
                intent.putExtra("Bus_ser", Bus_Servic_No);
                intent.putExtra("Route_no", Route_No);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView favrow;
        public TextView bus_ser;
        public TextView time1;
        public TextView time2;
        public TextView time3;
        public ImageView emfav;

        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.favrow = (ImageView) itemView.findViewById(R.id.favrow);
            this.bus_ser = (TextView) itemView.findViewById(R.id.bus_s);
            this.time1 = (TextView) itemView.findViewById(R.id.t1);
            this.time2 = (TextView) itemView.findViewById(R.id.t2);
            this.time3 = (TextView) itemView.findViewById(R.id.t3);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }
}