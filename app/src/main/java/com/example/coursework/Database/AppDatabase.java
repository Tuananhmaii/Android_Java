package com.example.coursework.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coursework.Dao.HikeDao;
import com.example.coursework.Models.Hike;


@Database(entities = {Hike.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}

