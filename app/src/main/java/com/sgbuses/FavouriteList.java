package com.sgbuses;

import android.content.Context;
import android.provider.Settings;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouriteList {


    public static ArrayList<String> BUSFAVList = new ArrayList<String>();
    public Context context;
    public  FavouriteList(Context context){
        this.context = context;
    }


    public void comp_list () {



        FirebaseDatabase database;
        DatabaseReference mRef;
        database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mRef = database.getReference().child("FavCat").child(android_id);
//
//        String mGroupId = mRef.push().getKey();
//
//        BusFirebase user = new BusFirebase("100", "1", "2");
//        mRef.child(mGroupId).setValue(user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                int size = (int) dataSnapshot.getChildrenCount();


                for (DataSnapshot childdata : dataSnapshot.getChildren()) {


                    String ptitle = childdata.getKey().toString();

                    int size1 = (int) childdata.getChildrenCount();


                    for (DataSnapshot child1 : childdata.getChildren()) {

                        BusFirebase busFirebase = child1.getValue(com.sgbuses.BusFirebase.class);

                        String bus = busFirebase.getBus();
                        BUSFAVList.add(bus);

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
    }
}
