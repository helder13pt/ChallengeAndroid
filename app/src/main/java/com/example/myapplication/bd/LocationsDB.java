package com.example.myapplication.bd;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.model.Locations;
import com.example.myapplication.model.LocationsDao;

@Database(entities = {Locations.class}, version = 1)
public abstract class LocationsDB extends RoomDatabase {

    private static LocationsDB instance;

    public abstract LocationsDao locationsDao();

    public static synchronized LocationsDB getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    LocationsDB.class,"locations_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationsDao locationsDao;

        private PopulateDbAsyncTask(LocationsDB db){
            locationsDao= db.locationsDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            locationsDao.insert(new Locations("41.8473","-8.41852","Arcos de Valdevez"));
            return null;
        }
    }
}
