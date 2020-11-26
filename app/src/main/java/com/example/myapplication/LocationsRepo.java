package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.bd.LocationsDB;
import com.example.myapplication.model.Locations;
import com.example.myapplication.model.LocationsDao;
import com.google.android.gms.common.data.DataBufferObserver;

import java.util.List;
import java.util.Observable;

public class LocationsRepo {
    private LocationsDao locationsDao;
    private LiveData<List<Locations>> allLocations;

    public LocationsRepo(Application application){
        LocationsDB database = LocationsDB.getInstance(application);
        locationsDao =database.locationsDao();
        allLocations = locationsDao.getAllLocations();
    }

    public void insert (Locations locations){
        new InsertLocationsAsyncTask(locationsDao).execute(locations);
    }

    public LiveData<List<Locations>> getAllLocations(){
        return allLocations;
    }

    private static class InsertLocationsAsyncTask extends AsyncTask<Locations,Void ,Void> {
        private LocationsDao locationsDao;

        private InsertLocationsAsyncTask(LocationsDao locationsDao){
            this.locationsDao=locationsDao;
        }

        @Override
        protected Void doInBackground(Locations... locations) {
            locationsDao.insert(locations[0]);
            return null;
        }
    }


}
