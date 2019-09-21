package com.sgbuses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.sgbuses.FavouriteList.BUSFAVList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<DataModel> listdata;
    private String Stop_name ;
    private String Bus_Servic_No;
    private String Route_No;

    private static int TYPE_DEFAULT = 0 ;
    private static int TYPE_FAVOURITE = 1;

    public Context context;




    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<DataModel> listdata, String stop, String bus_Servic_No, String route_No, Context context) {
        this.listdata = listdata;
        this.Stop_name = stop;
        this.Bus_Servic_No = bus_Servic_No;
        this.Route_No = route_No;
        this.context = context;

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

                    final int position = viewHolder.getAdapterPosition();

                    String as = (String) viewHolder.favrow.getTag();
                    if (as.equals("un"))
                    {

//                        Toast.makeText(view.getContext(), "un" + position + listdata.get(position).getServ_no(), Toast.LENGTH_LONG).show();

                        viewHolder.favrow.setImageResource(R.drawable.ic_fav);
                        viewHolder.favrow.setTag("fav");
                    }
                    else {
//                        Toast.makeText(view.getContext(), "fav" + position + listdata.get(position).getServ_no(), Toast.LENGTH_LONG).show();

//                        Toast.makeText(parent.getContext(), "Fav", Toast.LENGTH_LONG).show();
                        viewHolder.favrow.setImageResource(R.drawable.ic_emptyfav);
                        viewHolder.favrow.setTag("un");
                        removeFavval(listdata.get(position).getServ_no());
                    }
                }
            });

            return viewHolder;
        }else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View listItem = layoutInflater.inflate(R.layout.row_item11, parent, false);

            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item11, parent, false);
            final ViewHolder holder = new ViewHolder(view);

            holder.favrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int position = holder.getAdapterPosition();

                    holder.favrow.setImageResource(R.drawable.ic_fav);

                    String as = (String) holder.favrow.getTag();
                    if (as.equals("fav"))
                    {

//                        Toast.makeText(view.getContext(), "fav" + position + listdata.get(position).getServ_no(), Toast.LENGTH_LONG).show();
                        holder.favrow.setImageResource(R.drawable.ic_emptyfav);
                        holder.favrow.setTag("un");
//                        removeFavval(listdata.get(position).getServ_no());
                    }
                    else {

//                        Toast.makeText(view.getContext(), "un fav" + position + listdata.get(position).getServ_no(), Toast.LENGTH_LONG).show();

                        holder.favrow.setImageResource(R.drawable.ic_fav);
                        holder.favrow.setTag("fav");


                        FirebaseDatabase database;
                        DatabaseReference mRef;
                          String android_id = Settings.Secure.getString(view.getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                        database =  FirebaseDatabase.getInstance();
                         mRef =  database.getReference().child("FavCat").child(android_id).child(Stop_name);
                            String mGroupId = mRef.push().getKey();

                BusFirebase user = new BusFirebase( listdata.get(position).getServ_no(),
                        listdata.get(position).getBus_inter1(),
                        listdata.get(position).getBus_inter2());
                mRef.child(mGroupId).setValue(user);
//                Toast.makeText(view.getContext(), "Fav btn is clicked", Toast.LENGTH_LONG).show();
                    }
                }
            });
            return holder;
        }
    }


    public void removeFavval (String rem_val)
    {
        FirebaseDatabase database;
        DatabaseReference mRef;
        database =  FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mRef =  database.getReference().child("FavCat").child(android_id);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                BUSFAVList.clear();
                int size = (int) dataSnapshot.getChildrenCount();
                String key = dataSnapshot.getKey();


                for (DataSnapshot childdata: dataSnapshot.getChildren())
                {

                    String ptitle = childdata.getKey();

                    int size1 = (int) childdata.getChildrenCount();


                    for (DataSnapshot child1: childdata.getChildren()) {

                        BusFirebase busFirebase = child1.getValue(BusFirebase.class);

                        String chilkey = child1.getKey();
                        String bus = busFirebase.getBus();
                        BUSFAVList.add(bus);

                        if (rem_val.equals(bus))
                        {
                            mRef.child(ptitle).child(chilkey).removeValue();
                        }


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

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
            holder.time1.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }

        else {

            if (Integer.parseInt(listdata.get(position).getBus_inter1()) <= 0 )
            {
                holder.time1.setText("Arr");
            }
            else {
                holder.time1.setText(listdata.get(position).getBus_inter1() + " Min ");
            }
        }

        if (listdata.get(position).getBus_inter2().equals(""))
        {
            holder.time2.setText("N.A");
            holder.time2.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }
        else {

            if (Integer.parseInt(listdata.get(position).getBus_inter2()) <= 0 )
            {
                holder.time2.setText("Arr");
            }
            else {
                holder.time2.setText(listdata.get(position).getBus_inter2() + " Min ");
            }
        }


        if (listdata.get(position).getBus_inter3().equals(""))
        {
            holder.time3.setText("N.A");
            holder.time3.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }
        else {

            if (Integer.parseInt(listdata.get(position).getBus_inter3()) <= 0 )
            {
                holder.time3.setText("Arr");
            }
            else {
                holder.time3.setText(listdata.get(position).getBus_inter3() + " Min ");
            }
        }






        ////////////     This is for backgroud color check for time interval 1
        if (listdata.get(position).getBus1load().equals("SEA"))
        {
            holder.time1.setBackgroundColor(Color.parseColor("#00ff00"));
        }
        else if (listdata.get(position).getBus1load().equals("SDA"))
        {
            holder.time1.setBackgroundColor(Color.parseColor("#ffff00"));
        }
        else if (listdata.get(position).getBus1load().equals("LSD"))
        {
            holder.time1.setBackgroundColor(Color.parseColor("#ff0000"));
        }



        ////////////     This is for backgroud color check for time interval 2
        if (listdata.get(position).getBus2load().equals("SEA"))
        {
            holder.time2.setBackgroundColor(Color.parseColor("#00ff00"));
        }
        else if (listdata.get(position).getBus2load().equals("SDA"))
        {
            holder.time2.setBackgroundColor(Color.parseColor("#ffff00"));
        }
        else if (listdata.get(position).getBus2load().equals("LSD"))
        {
            holder.time2.setBackgroundColor(Color.parseColor("#ff0000"));
        }


        ////////////     This is for backgroud color check for time interval 3
        if (listdata.get(position).getBus3load().equals("SEA"))
        {
            holder.time3.setBackgroundColor(Color.parseColor("#00ff00"));
        }
        else if (listdata.get(position).getBus3load().equals("SDA"))
        {
            holder.time3.setBackgroundColor(Color.parseColor("#ffff00"));
        }
        else if (listdata.get(position).getBus3load().equals("LSD"))
        {
            holder.time3.setBackgroundColor(Color.parseColor("#ff0000"));
        }

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