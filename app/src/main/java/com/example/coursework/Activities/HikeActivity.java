package com.example.coursework.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursework.Adapter.HikeAdapter;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.util.List;

public class HikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike);
        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Hike> hikeList = appDatabase.hikeDao().getAllHikes();

        HikeAdapter adapter = new HikeAdapter(hikeList);
        adapter.setOnItemClickListener(new HikeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HikeActivity.this, HikeDetailsActivity.class);
                Hike hike = hikeList.get(position);
                intent.putExtra("hike_id", Long.toString(hike.hike_id));
                intent.putExtra("hike_name", hike.name);
                intent.putExtra("hike_location", hike.location);
                intent.putExtra("hike_date", hike.date);
                intent.putExtra("hike_availablePark", hike.availablePark);
                intent.putExtra("hike_length", hike.length);
                intent.putExtra("hike_difficulty", hike.difficulty);
                intent.putExtra("hike_teamSize", hike.teamSize);
                intent.putExtra("hike_weather", hike.weather);
                intent.putExtra("hike_description", hike.description);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        Button addHikeButton = findViewById(R.id.addHikeButton);
        addHikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddHikeActivity.class);
                startActivity(intent);
            }
        });
    }
}