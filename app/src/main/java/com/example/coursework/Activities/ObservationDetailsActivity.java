package com.example.coursework.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Observation;
import com.example.coursework.R;

public class ObservationDetailsActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    EditText nameEditText;
    EditText timeEditText;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_details);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite3_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Long observation_id = Long.parseLong(intent.getStringExtra("observation_id"));
        String observation_name = intent.getStringExtra("observation_name");
        String observation_time = intent.getStringExtra("observation_time");
        String observation_description = intent.getStringExtra("observation_description");
        Long hike_id = Long.parseLong(intent.getStringExtra("hike_id"));

        // Capture the layout's TextView and set the string as its text
        nameEditText = findViewById(R.id.nameText);
        timeEditText = findViewById(R.id.timeText);
        descriptionEditText = findViewById(R.id.descriptionText);


        // Set value
        nameEditText.setText(observation_name);
        timeEditText.setText(observation_time);
        descriptionEditText.setText(observation_description);


        Button saveDetailsButton = findViewById(R.id.updateObservationButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if (name.isEmpty() || time.isEmpty()) {
                    Toast.makeText(ObservationDetailsActivity.this, "Please enter all required information!", Toast.LENGTH_LONG).show();
                }
                else {
                    Observation observation = new Observation();
                    observation.observation_id = observation_id;
                    observation.name = name;
                    observation.time = time;
                    observation.description = description;
                    observation.hikeId = hike_id;

                    appDatabase.observationDao().updateObservation(observation);
                    Toast.makeText(ObservationDetailsActivity.this, "Update successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ObservationDetailsActivity.this, ObservationActivity.class);
                    intent.putExtra("hike_id", Long.toString(hike_id));
                    startActivity(intent);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteObservationButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.observationDao().deleteObservation(observation_id);
                Toast.makeText(ObservationDetailsActivity.this, "Delete successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ObservationDetailsActivity.this, ObservationActivity.class);
                intent.putExtra("hike_id", Long.toString(hike_id));
                startActivity(intent);
            }
        });
    }
}
