package com.sgbuses;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BusStopMapMarker extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {



    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;


    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_map_marker);

        Button del = (Button)findViewById(R.id.del);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;


        try {


            String rout_n = getIntent().getStringExtra("Route_no");
            readCSV_Orgin(rout_n);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    public void readCSV_Orgin(String b_st_co) throws IOException {


        InputStream is = getApplicationContext().getAssets().open("sgbuses_data.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            if (row[0].equals(b_st_co)) {


                LatLng PERTH;

                String road = row [1];
                String lat = row[2];
                String lon = row [3];

                Double lat1 = Double.parseDouble(lat);
                Double lon1 = Double.parseDouble(lon);

                PERTH = new LatLng(lat1, lon1);

// Add some markers to the map, and add a data object to each marker.
                String Bus_no = getIntent().getStringExtra("Bus_ser");
                mMap.addMarker(new MarkerOptions()
                        .position(PERTH)
                        .title(road  + " (" + b_st_co +" ) ")
                        .snippet(Bus_no)).showInfoWindow();


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(PERTH).zoom(17).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }


    }
}
