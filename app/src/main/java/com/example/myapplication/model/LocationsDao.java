package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationsDao {

    @Insert
    void insert(Locations locations);

    @Update
    void update(Locations locations);

    @Delete
    void delete(Locations locations);

    @Query("SELECT * FROM locations_table ORDER BY id DESC")
    LiveData<List<Locations>> getAllLocations();

    @Query("SELECT * FROM locations_table ORDER BY id DESC")
    List<Locations> getAllssLocations();
}
