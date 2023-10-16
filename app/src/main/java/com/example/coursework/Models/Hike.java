package com.example.coursework.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikes")

public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public String name;
    public String location;
    public String date;
    public String availablePark;
    public int length;
    public String difficulty;
    public String description;
    public String weather;
    public int teamSize;
}
