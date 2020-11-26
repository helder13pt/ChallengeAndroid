package com.example.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.LocationsRepo;
import com.example.myapplication.model.Locations;

import java.util.List;

public class LocationsViewModel extends AndroidViewModel {
    private LocationsRepo repository;
    private LiveData<List<Locations>> allLocations;

    LocationsViewModel(Application application) {
        super(application);
        repository= new LocationsRepo(application);
        allLocations =repository.getAllLocations();

    }

    public void insert(Locations locations){
        Log.i("TAG", "Place: testessssss insert" + locations.getNome());
        repository.insert(locations);
    }

    public LiveData<List<Locations>> getAllLocations(){
        return allLocations;
    }
}
