package com.sgbuses.Servic_search_view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sgbuses.R;

import java.util.ArrayList;
import java.util.List;

public class BusesRoutes extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    LatLng PERTH1 ;
    LatLng PERTH2 ;


    ArrayList<String> data = new ArrayList<String>();



    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;


    private MarkerOptions options = new MarkerOptions();
    List<String> roadname1 = new ArrayList<>();
    private ArrayList<LatLng> latlngs1 = new ArrayList<>();

    List<String> roadname2 = new ArrayList<>();
    private ArrayList<LatLng> latlngs2 = new ArrayList<>();
    private GoogleMap mMap;

    Button bus_no ;

    RadioButton orgin ;
    RadioButton destin ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_routes);

        bus_no = (Button)findViewById(R.id.busno);
        orgin = (RadioButton)findViewById(R.id.orign);
        destin = (RadioButton)findViewById(R.id.des) ;
        orgin.setEnabled(false);
        destin.setEnabled(false);

        Button del = (Button)findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String Bus_no = getIntent().getStringExtra("Bus_no");
        String origin = getIntent().getStringExtra("Origin");
        String dest = getIntent().getStringExtra("Destin");

        if (origin.equals(dest))
        {
            destin.setVisibility(View.GONE);
        }

        bus_no.setText("Bus " + Bus_no);

        orgin.setText(origin + "  -  " + dest);
        destin.setText(dest + "  -  " + origin);


        orgin.setChecked(true);
        orgin.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));


        orgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destin.setChecked(false);
                destin.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                orgin.setChecked(true);
                orgin.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                route1();

            }
        });

        destin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orgin.setChecked(false);
                orgin.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                destin.setChecked(true);
                destin.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                route2();



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



        String Bus_no = getIntent().getStringExtra("Bus_no");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference scoreRef = rootRef.child("BusRoutes1");
        final ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int val = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    if ( dataSnapshot.exists()) {


                        String dir = ds.child("direction").getValue(String.class);

                        if (dir.equals("1"))
                        {

                            String lat = ds.child("latitude").getValue(String.class);
                            String lon = ds.child("longitude").getValue(String.class);
                            String road_n = ds.child("roadname").getValue(String.class);

                            Double lat1 = Double.parseDouble(lat);
                            Double lon1 = Double.parseDouble(lon);

                            roadname1.add(road_n);
                            latlngs1.add(new LatLng(lat1,lon1));

                            if (val==10)
                            {
                                PERTH1 = new LatLng(lat1, lon1);
                            }
                        }
                        else if (dir.equals("2"))
                        {


                            String lat = ds.child("latitude").getValue(String.class);
                            String lon = ds.child("longitude").getValue(String.class);
                            String road_n = ds.child("roadname").getValue(String.class);

                            Double lat1 = Double.parseDouble(lat);
                            Double lon1 = Double.parseDouble(lon);

                            roadname2.add(road_n);
                            latlngs2.add(new LatLng(lat1,lon1));
                            PERTH2 = new LatLng(lat1, lon1);


                        }

                        val++;

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_LONG).show();

                    }
                }

                destin.setEnabled(true);
                orgin.setEnabled(true);

                route1();

                if (latlngs2.size() <= 0)
                {
                    destin.setVisibility(View.GONE);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        scoreRef.orderByChild("serviceno").equalTo(Bus_no).addListenerForSingleValueEvent(eventListener);

        mMap.setOnMarkerClickListener(this);
    }

    public void route1()
    {

        mMap.clear();

        int i = 0 ;

        for (LatLng point : latlngs1) {
            options.position(point);
            options.title(roadname1.get(i));
            options.snippet("someDesc");
            mMap.addMarker(options);
            i++;
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(PERTH1).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void route2()
    {


        mMap.clear();

        int i = 0 ;

        for (LatLng point : latlngs2) {
            options.position(point);
            options.title(roadname2.get(i));
            options.snippet("someDesc");
            mMap.addMarker(options);
            i++;
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(PERTH1).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
}
