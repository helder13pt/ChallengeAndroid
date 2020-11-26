package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.bd.LocationsDB;
import com.example.myapplication.model.Locations;
import com.example.myapplication.viewmodel.LocationsViewModel;
import com.example.myapplication.viewmodel.MyViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity2 extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker mMarcadorActual;
    private LocationsViewModel locationsViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    public double lat, lon;
    public MarkerOptions markerOptions;
    public LatLng location;
    private List<Locations> localList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FloatingActionButton fab = findViewById(R.id.fab);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationsViewModel = ViewModelProviders.of(this,new MyViewModelFactory(this.getApplication())).get(LocationsViewModel.class);

        locationsViewModel.getAllLocations().observe(this, new Observer<List<Locations>>() {
            @Override
            public void onChanged(List<Locations> locations) {
            localList=locations;
                plotMarkers();
            }

        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, SearchActivity.class));
            }
        });

    }

    public void plotMarkers(){

        double lat, lon;
        for(Locations i: localList){
            lat= Double.parseDouble(i.getLatitute());
            lon=Double.parseDouble(i.getLongitude());

            createMarker(lat,lon,i.getNome());

        }

    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double douLat = getIntent().getExtras().getDouble("EXTRA_LAT");
            Double douLon = getIntent().getExtras().getDouble("EXTRA_LON");
            LatLng locat=new LatLng(douLat,douLon);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locat, 15));
        }
        else {


            LatLng sydney = new LatLng(-34, 151);
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
            mMarcadorActual = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            Log.i("TAG", "Frag on  onMapReady!!!!!! ");
        }

    }

    public void putArguments(Bundle args){

        String latitude=args.getString("value1");
        String longitude = args.getString("value2");
        Log.i("TAG", "Frag on putArguments!!! " +latitude +longitude);
        lat = Double.parseDouble(latitude);
        lon = Double.parseDouble(longitude);
        location = new LatLng(lat, lon);
        Log.i("TAG", "Tem dados na location!!! " +location);
        markerOptions = new MarkerOptions();
        markerOptions.position(location);
        if(mMap!=null) {

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker in "));
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 15f));
        }
    }


}