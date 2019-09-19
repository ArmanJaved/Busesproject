package com.sgbuses;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sgbuses.Search_c_listview.AdaptopBusStop;
import com.sgbuses.Servic_search_view.AdaptopBusService;
import com.sgbuses.Servic_search_view.BusesRoutes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tab3Fragment extends Fragment {

    View view;

    EditText bus_stopno;


    ArrayList<String> servicno = new ArrayList<String>();
    ArrayList<String> origin = new ArrayList<String>();
    ArrayList<String>  destinat = new ArrayList<String>();

    ListView listView_servic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_three, container, false);


        bus_stopno = (EditText)view.findViewById(R.id.servicno);
        listView_servic = (ListView)view.findViewById(R.id.list_serviceno);




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

                        if (text.matches(""))
                        {
                            listView_servic.setVisibility(View.GONE);
                        }
                        else {

                            listView_servic.setVisibility(View.VISIBLE);
                            try {
                                readCSV_bus_service(text);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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


    public void readCSV_bus_service(String st_no) throws IOException {


        servicno.clear();
        origin.clear();
        destinat.clear();
        InputStream is = getActivity().getAssets().open("busservices.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[5].startsWith(st_no)) {

                servicno.add(row[5]);
//

                String or = readCSV_Orgin(row[4]);
                String des = readCSV_des(row[1]);

                origin.add(or);
                destinat.add(des);


            }
        }

        listupdata();
    }


    public String readCSV_Orgin(String orig) throws IOException {


        String orig_ret = "";
        InputStream is = getActivity().getAssets().open("stopcode_des.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].equals(orig)) {

                orig_ret = row[1];
            }
        }
        return orig_ret;
    }


    public String readCSV_des(String des) throws IOException {


        String des_ret="";
        InputStream is = getActivity().getAssets().open("stopcode_des.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].equals(des)) {

                des_ret = row[1];
            }
        }
        return des_ret;
    }



        public void listupdata ()
        {

            ArrayList<String> asd = origin;
            ArrayList<String> dss = destinat;

            AdaptopBusService adapter=new AdaptopBusService(getActivity(), servicno, origin, destinat);
            listView_servic.setAdapter(adapter);
            listView_servic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    String org = origin.get(position);
                    String ds = destinat.get(position);
                    String  itemValue    = (String) listView_servic.getItemAtPosition(position);

                    Toast.makeText(getActivity(), itemValue, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getContext(), BusesRoutes.class);
                    intent.putExtra("Bus_no", itemValue);
                    intent.putExtra("Origin", org);
                    intent.putExtra("Destin", ds);

                    startActivity(intent);

                }
            });
        }


}