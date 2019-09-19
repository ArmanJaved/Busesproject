package com.sgbuses;


import android.os.Bundle;
import android.os.FileUtils;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.sgbuses.FavouriteList.BUSFAVList;

public class StoreFirebase extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference mRef;

    String road_name;
    String lat;
    String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);





//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BusStop");
//        ref.orderByChild("busstopcode")
//                .startAt("011")
//                .endAt("011"+"\uf8ff")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    String key = userSnapshot.getKey();
//                    String val = userSnapshot.child("busstopcode").getValue(String.class);
//
//                    System.out.println(userSnapshot.getKey());
//                    System.out.println(userSnapshot.child("busstopcode").getValue(String.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });


        database =  FirebaseDatabase.getInstance();
        mRef =  database.getReference().child("FavCat").child("YMCA");

        String mGroupId = mRef.push().getKey();

        BusFirebase user = new BusFirebase( "100", "1", "3");

        mRef.child(mGroupId).setValue(user);
//
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                int size = (int) dataSnapshot.getChildrenCount();
//
//
//                for (DataSnapshot childdata: dataSnapshot.getChildren())
//                {
//
//                    String key = childdata.getKey().toString();
//
//
//                    int size1 = (int) childdata.getChildrenCount();
//
//
//                    for (DataSnapshot child1: childdata.getChildren()) {
//
//                        BusFirebase busFirebase = child1.getValue(com.sgbuses.BusFirebase.class);
//
//                        String bus = busFirebase.getBus();
//                        String t1 = busFirebase.getTim1();
//                        String t2 = busFirebase.getTim2();
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                String as = snapshot.getValue().toString();
//                Toast.makeText(getApplicationContext(), snapshot.getValue().toString(), Toast.LENGTH_LONG).show();
////                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

//        BusFirebase user = new BusFirebase( "107", "12", "13");
//
//        mRef.child("Outram").setValue(user);

//        final EditText sto = (EditText)findViewById(R.id.stopno);
//        ImageButton btn = (ImageButton)findViewById(R.id.btnRequest);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String as = "";
//                as = sto.getText().toString();
//                try {
//                    BusStops(as);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });




    }





    public void BusStops(String st_code) throws IOException {


        String url= "http://datamall2.mytransport.sg/ltaodataservice/BusRoutes?$skip="+st_code;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("AccountKey", "Z9Qt9PlQTXKRbtlcjmoLqw==")
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();

                String sds= e.toString();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                StoreFirebase.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Get_Bus_Stop_No(myResponse);

                    }
                });

            }
        });

    }

//    public void BusStops(String asas) throws IOException {
//
//
//        String url= "http://datamall2.mytransport.sg/ltaodataservice/BusServices";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .header("AccountKey", "Z9Qt9PlQTXKRbtlcjmoLqw==")
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                call.cancel();
//
//                String sds= e.toString();
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                final String myResponse = response.body().string();
//                Get_Bus_Stop_No(myResponse);
//
//            }
//        });
//
//    }





    public void Get_Bus_Stop_No (String jsonval)
    {

        try {

            String bus_S;
            String b_ope;
            String b_dir;
            String b_st_seq;
            String b_st_co;
            String b_dist;



            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(jsonval);
            // fetch JSONArray named users
            JSONArray userArray = obj.getJSONArray("value");
            // implement for loop for getting users list data
            int a = userArray.length();
            for (int i = 0; i < userArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject userDetail = userArray.getJSONObject(i);
                // fetch email and name and store it in arraylist
                bus_S = userDetail.getString("ServiceNo");
                b_ope = userDetail.getString("Operator");
                b_dir = userDetail.getString("Direction");
                b_st_seq = userDetail.getString("StopSequence");
                b_dist = userDetail.getString("Distance");
                b_st_co = userDetail.getString("BusStopCode");

                try {
                    readCSV_Orgin(b_st_co, bus_S, b_ope, b_dir, b_st_seq, b_dist);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void readCSV_Orgin(String b_st_co, String bus_S, String b_ope, String b_dir, String b_st_seq, String b_dist) throws IOException {


        InputStream is = getApplicationContext().getAssets().open("sgbuses_data.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].equals(b_st_co)) {

                road_name = row[1];
                lat = row[2];
                lon = row[3];

            }
        }


        BusStopUploadToFirebase user = new BusStopUploadToFirebase(bus_S, b_ope, b_dir , b_st_seq, b_st_co, b_dist, road_name, lat, lon);
        String mGroupId = mRef.push().getKey();
        mRef.child(mGroupId).setValue(user);
    }
}