package com.example.coursework.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.Models.Hike;
import com.example.coursework.Models.Observation;

import java.util.List;
@Dao
public interface ObservationDao {
    @Insert
    void insertObservation (Observation observation);

    @Update
    void updateObservation (Observation observation);

    @Query("DELETE FROM observations WHERE observation_id=:id")
    void deleteObservation (long id);

    @Query("SELECT * FROM observations WHERE hikeId=:id")
    List<Observation> getAllObservations(long id);
}
