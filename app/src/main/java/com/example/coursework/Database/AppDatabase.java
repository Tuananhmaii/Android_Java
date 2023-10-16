package com.example.coursework.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.example.coursework.Dao.HikeDao;
import com.example.coursework.Dao.ObservationDao;
import com.example.coursework.Models.Hike;
import com.example.coursework.Models.Observation;

@Database(entities = {Hike.class, Observation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
    public abstract ObservationDao observationDao();


}
