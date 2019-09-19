package com.sgbuses;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sgbuses.Search_c_listview.AdaptopBusStop;
import com.sgbuses.Servic_search_view.BusesRoutes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.sgbuses.FavouriteList.BUSFAVList;

public class Tab4Fragment extends Fragment {

    View view;

    private ArrayList<DataModel> dataModels;
    private RecyclerView listView_bus_t;
    private static StopListAdapter adapter;
    ImageButton search_b_s;
    private EditText bus_stopno;
    private TextView st_name_des;
    private View lib ;
    private ListView listView_s;

    private boolean flag;
    private ArrayList<String> rowss_st;
    private ArrayList<String> rowss_st_des ;

    String Stop_name;


    String Bus_Servic_No="";

    String Route_No;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.stop_fragment, container, false);

        dataModels= new ArrayList<>();
        bus_stopno = (EditText)view.findViewById(R.id.stopno);
        bus_stopno.setText("");
        st_name_des = (TextView) view.findViewById(R.id.stop_no);
        lib = (View)view.findViewById(R.id.lineb);
        lib.setVisibility(View.GONE);
        listView_bus_t=(RecyclerView) view.findViewById(R.id.list);
        listView_s = (ListView)view.findViewById(R.id.list_stopcode);
        listView_s.setVisibility(View.VISIBLE);
        st_name_des.setVisibility(View.GONE);
        lib.setVisibility(View.GONE);
        flag = false;




        bus_stopno.addTextChangedListener(new TextWatcher() {

            CountDownTimer timer = null;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (timer != null) {
                    timer.cancel();
                }

                timer = new CountDownTimer(500, 500) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        String text = bus_stopno.getText().toString();
                        Route_No = bus_stopno.getText().toString();

                        if (flag)
                        {

                            flag = false;
                            search_bus_stop_list(text);


                        }
                        else {

                            listView_bus_t.setVisibility(View.GONE);
                            listView_s.setVisibility(View.VISIBLE);
                            st_name_des.setVisibility(View.GONE);
                            lib.setVisibility(View.GONE);
                            search_bus_stop_list(text);

                        }

                    }

                }.start();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }

    private void search_bus_stop_list ( String text)
    {



        try {

            if (text.matches("-?\\d+"))
            {
                readCSV(text);
            }else {

                readCSV_text(text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        AdaptopBusStop adapter=new AdaptopBusStop(getActivity(), rowss_st, rowss_st_des);
        listView_s.setAdapter(adapter);
        listView_s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView_bus_t.setVisibility(View.VISIBLE);

                String  itemValue    = (String) listView_s.getItemAtPosition(position);

                try {
//                  BusStops(itemValue);
                    run(itemValue);
                    bus_stopno.setText(itemValue);
                    flag = true;
                    listView_s.setVisibility(View.GONE);
                    Setstop_name_des_c(itemValue);
                    st_name_des.setVisibility(View.VISIBLE);
                    lib.setVisibility(View.VISIBLE);
                    bus_stopno.setSelection(bus_stopno.getText().length());
//                                    bus_stopno.setCursorVisible(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }





    public void readCSV(String st_no) throws IOException {


        rowss_st =new ArrayList<String>();
        rowss_st_des =new ArrayList<String>();
        InputStream is = getActivity().getAssets().open("sgbuses_data.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].startsWith(st_no)) {
                rowss_st.add(row[0]);
                rowss_st_des.add(row[1]);
            }
        }
    }


    public void readCSV_text(String st_no) throws IOException {


        rowss_st =new ArrayList<String>();
        rowss_st_des =new ArrayList<String>();
        InputStream is = getActivity().getAssets().open("sgbuses_data.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[1].toLowerCase().startsWith(st_no)) {
                rowss_st.add(row[0]);
                rowss_st_des.add(row[1]);
            }
        }
    }



    public void Setstop_name_des_c (String st_no) throws IOException {


        InputStream is = getActivity().getAssets().open("sgbuses_data.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].equals(st_no)) {
                st_name_des.setText(row[1]);
                Stop_name = row[1];

            }
        }
    }



    public void run(String st_no) throws IOException {


        dataModels.clear();

        String url= "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode="+st_no;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("AccountKey", "Z9Qt9PlQTXKRbtlcjmoLqw==")
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        jsontransform(myResponse);
                    }
                });

            }
        });

    }

    public void jsontransform (String jsonval)
    {

        try {

            String serv_no;
            String est_next_bus_int;
            String est_next_bus2_int;
            String est_next_bus3_int;
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(jsonval);
            // fetch JSONArray named users
            JSONArray userArray = obj.getJSONArray("Services");
            // implement for loop for getting users list data
            int a = userArray.length();

            Bus_Servic_No="";
            for (int i = 0; i < userArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject userDetail = userArray.getJSONObject(i);
                // fetch email and name and store it in arraylist
                serv_no = userDetail.getString("ServiceNo");
//                emailIds.add(userDetail.getString("Operator"));
                // create a object for getting contact data from JSONObject
                JSONObject nex_bus_obj = userDetail.getJSONObject("NextBus");

                Bus_Servic_No += serv_no +" ";

                // fetch mobile number and store it in arraylist
                est_next_bus_int = nex_bus_obj.getString("EstimatedArrival");
                est_next_bus_int = gettime(est_next_bus_int);


                JSONObject nex_bus2_obj = userDetail.getJSONObject("NextBus2");
                // fetch mobile number and store it in arraylist
                est_next_bus2_int = nex_bus2_obj.getString("EstimatedArrival");
                est_next_bus2_int = gettime(est_next_bus2_int);


                JSONObject nex_bus3_obj = userDetail.getJSONObject("NextBus3");
                // fetch mobile number and store it in arraylist
                est_next_bus3_int = nex_bus3_obj.getString("EstimatedArrival");

                est_next_bus3_int = gettime(est_next_bus3_int);

                dataModels.add(new DataModel(serv_no, est_next_bus_int, est_next_bus2_int ,est_next_bus3_int ));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }






        MyListAdapter adapter = new MyListAdapter(dataModels, Stop_name, Bus_Servic_No, Route_No );
        listView_bus_t.setHasFixedSize(true);
        listView_bus_t.setLayoutManager(new LinearLayoutManager(getContext()));
        listView_bus_t.setAdapter(adapter);





//        adapter= new StopListAdapter(dataModels, Stop_name, getActivity());
//        listView_bus_t.setAdapter(adapter);
//        listView_bus_t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                DataModel dataModel= dataModels.get(position);
//
//
//                Intent intent = new Intent(getContext(), BusStopMapMarker.class);
//                intent.putExtra("Bus_ser", Bus_Servic_No);
//                intent.putExtra("Route_no", Route_No);
//
//                startActivity(intent);
//
//                Snackbar.make(view, dataModel.getServ_no()+"\n"+
//                        dataModel.getBus_inter1()+" API: "+
//                        dataModel.getBus_inter2(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//            }
//        });

    }

    public String gettime (String esttim) {

        String est_min = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        try {
            Date date1 = simpleDateFormat.parse(esttim);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("Singapore"));
            sdf.format(date);

            String text = sdf.format(date);
            Date date2 = simpleDateFormat.parse(text);
            est_min = printDifference(date2, date1);



        } catch (ParseException e) {
            e.printStackTrace();
        }

        return est_min;
    }



    public String printDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long elapsedMinutes = different / minutesInMilli;
        if (elapsedMinutes <= 0 )
        {
            elapsedMinutes = 0;
        }
        String str = Long.toString(elapsedMinutes);
        return str;

    }



}