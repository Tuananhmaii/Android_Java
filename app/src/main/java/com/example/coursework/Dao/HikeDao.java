package com.example.coursework.Dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Dao;

import com.example.coursework.Models.Hike;

import java.util.List;
@Dao
public interface HikeDao {

    @Insert
    void insertHike (Hike hike);

    @Update
    void updateHike (Hike hike);

    @Query("DELETE FROM hikes WHERE hike_id=:id")
    void deleteHike (long id);

    @Query("SELECT * FROM hikes")
    List<Hike> getAllHikes();
}
