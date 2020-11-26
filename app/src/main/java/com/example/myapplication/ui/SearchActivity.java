package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.model.Locations;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.viewmodel.LocationsViewModel;
import com.example.myapplication.viewmodel.MyViewModelFactory;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.util.Arrays;

public class SearchActivity extends FragmentActivity {
    private LocationsViewModel locationsViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_window);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -200;
        params.height = 500;
        params.width = 1100;
        params.y = 0;

        this.getWindow().setAttributes(params);

        locationsViewModel = ViewModelProviders.of(this,new MyViewModelFactory(this.getApplication())).get(LocationsViewModel.class);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),"AIzaSyDVSLVN5n5an3Xp5e1UOF9WuIxub9G2zD8");
        }
        PlacesClient placesClient = Places.createClient(this);


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                 getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME,Place.Field.ID, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("TAG", "Place: " + place.getName() +"localizacao:" + place.getLatLng());
                LatLng destinationLatLng = place.getLatLng();

                String destlat = String.valueOf(destinationLatLng.latitude);
                String destLon = String.valueOf(destinationLatLng.longitude);
                String nome = place.getName();

                Locations locations = new Locations (destlat,destLon,nome);
                Log.i("TAG", "Place: testessssss" + locations.getNome());
                locationsViewModel.insert(locations);

                Intent intent = new Intent(getBaseContext(), MainActivity2.class);
                intent.putExtra("EXTRA_LAT", destinationLatLng.latitude);
                intent.putExtra("EXTRA_LON", destinationLatLng.longitude);
                startActivity(intent);

              //  finish();


            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("TAG", "Aconteceu erro!!! " );
            }
        });


    }




}
