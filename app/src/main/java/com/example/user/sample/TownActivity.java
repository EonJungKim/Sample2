package com.example.user.sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by user on 2017-11-27.
 */

public class TownActivity extends AppCompatActivity {

    TextView txtTownName;
    TextView txtTownCity, txtTownAddress;
    TextView txtTownProgram, txtTownActivity, txtTownFacility;
    TextView txtTownPresident, txtTownCall, txtTownHomePage;
    TextView txtTownManagement;

    Button btnTownCall, btnTownHomePage;

    String townName;
    String state, city, address;
    String program, activity, facility;
    String president, callNumber, homePage;
    String management;
    Double latitude, longitude;

    SQLiteDatabase db;

    SupportMapFragment mapFragment;

    MarkerOptions marker;

    GoogleMap map;

    private void initWidget() {
        txtTownName = (TextView) findViewById(R.id.txtTownName);
        txtTownCity = (TextView) findViewById(R.id.txtTownCity);
        txtTownAddress = (TextView) findViewById(R.id.txtTownAddress);
        txtTownProgram = (TextView) findViewById(R.id.txtTownProgram);
        txtTownActivity = (TextView) findViewById(R.id.txtTownActivity);
        txtTownFacility = (TextView) findViewById(R.id.txtTownFacility);
        txtTownPresident = (TextView) findViewById(R.id.txtTownPresident);
        txtTownCall = (TextView) findViewById(R.id.txtTownCall);
        txtTownHomePage = (TextView) findViewById(R.id.txtTownHomePage);
        txtTownManagement = (TextView) findViewById(R.id.txtTownManagement);

        btnTownCall = (Button) findViewById(R.id.btnTownCall);
        btnTownHomePage = (Button) findViewById(R.id.btnTownHomePage);


    }

    private void getIntentData() {
        Intent intent = getIntent();
        townName = intent.getStringExtra("TOWN_NAME");
        state = intent.getStringExtra("TOWN_STATE");
        city = intent.getStringExtra("TOWN_CITY");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

        MapsInitializer.initialize(this);

        initWidget();

        getIntentData();

        findDatabase();

        requestMyLocation();

        setWidget();

        btnTownCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTownHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void requestMyLocation() {
        long minTime = 10000;
        float minDistance = 0;

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        showCurrentLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }
        );
    }

    public void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15)); // 15는 zoom 레벨

        showMarker(location);
    }

    private void setWidget() {
        txtTownName.setText(townName);
        txtTownCity.setText(state + " " + city);
        txtTownAddress.setText(address);
        txtTownProgram.setText(program);
        txtTownActivity.setText(activity);
        txtTownFacility.setText(facility);
        txtTownPresident.setText(president);
        txtTownCall.setText(callNumber);
        txtTownHomePage.setText(homePage);
        txtTownManagement.setText(management);


        Toast.makeText(this, "latitude : " + latitude + "\nlongitude : " + longitude, Toast.LENGTH_SHORT).show();
        //showMarker();
    }

    private void findDatabase() {
        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        if(db != null) {
            String sql = "select * " +
                    "from town " +
                    "where townName = \"" + townName + "\" AND state = \"" + state + "\" AND city = \"" + city + "\";";

            Cursor cursor = db.rawQuery(sql, null);

            cursor.moveToNext();

            management = cursor.getString(3);
            program = cursor.getString(4);
            activity = cursor.getString(5);
            facility = cursor.getString(6);
            address = cursor.getString(7);
            president = cursor.getString(8);
            callNumber = cursor.getString(9);
            homePage = cursor.getString(10);
            latitude = cursor.getDouble(12);
            longitude = cursor.getDouble(13);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(map != null) {
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    public void showMarker(Location location) {
        if(marker == null) {
            marker = new MarkerOptions();

            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(townName);
            marker.snippet(address);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));

            map.addMarker(marker);
        }
        else {
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));

        }
    }

    public void showMarker() {
        LatLng point = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
/*
        if(marker == null) {
            marker = new MarkerOptions();

            marker.position(new LatLng(latitude, longitude));
            marker.title(townName);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));

            map.addMarker(marker);
        }
        else {
            marker.position(new LatLng(latitude, longitude));
        }
*/
    }
}