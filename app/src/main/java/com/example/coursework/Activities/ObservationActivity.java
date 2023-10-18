package com.example.coursework.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.coursework.Adapter.ObservationAdapter;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.Models.Observation;
import com.example.coursework.R;

import java.util.List;

public class ObservationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation);
        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite3_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Long Id = Long.parseLong(intent.getStringExtra("hike_id"));
        List<Observation> observationList = appDatabase.observationDao().getAllObservations(Id);

        TextView hikeName = findViewById(R.id.hikeName);
        Hike hike = appDatabase.hikeDao().getHike(Id);
        hikeName.setText(hike.name);

        ObservationAdapter adapter = new ObservationAdapter(observationList);
        adapter.setOnItemClickListener(new ObservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ObservationActivity.this, ObservationDetailsActivity.class);
                Observation observation = observationList.get(position);
                intent.putExtra("observation_id", Long.toString(observation.observation_id));
                intent.putExtra("observation_name", observation.name);
                intent.putExtra("observation_time", observation.time);
                intent.putExtra("observation_description", observation.description);
                intent.putExtra("hike_id", Long.toString(Id));
                startActivity(intent);
            }

        });
        recyclerView.setAdapter(adapter);

        Button addObservationButton = findViewById(R.id.addObservationButton);
        addObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationActivity.this, ObservationAddActivity.class);
                intent.putExtra("hike_id", Long.toString(Id));
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationActivity.this, HikeActivity.class);
                startActivity(intent);
            }
        });
    }
}
