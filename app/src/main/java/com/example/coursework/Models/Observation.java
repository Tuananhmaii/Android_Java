package com.example.coursework.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "observations")

public class Observation {
    @PrimaryKey(autoGenerate = true)
    public long observation_id;
    public String name;
    public String time;
    public String description;
    public long hikeId;
}
