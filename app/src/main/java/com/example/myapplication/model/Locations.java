package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations_table")
public class Locations {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String latitute;
    private  String longitude;
    private String nome;

    public Locations(String latitute, String longitude, String nome) {
        this.latitute = latitute;
        this.longitude = longitude;
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLatitute() {
        return latitute;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNome() {
        return nome;
    }
}
