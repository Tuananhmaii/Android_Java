package com.example.coursework.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

public class HikeDetailsActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    Spinner parkSpinner;
    Spinner difficultySpinner;

    EditText nameEditText;
    EditText locationEditText;
    EditText dateEditText;
    EditText lengthEditText;
    EditText teamEditText;
    EditText weatherEditText;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailshike);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Long Id = Long.parseLong(intent.getStringExtra("hike_id"));
        String hike_name = (intent).getStringExtra("hike_name");
        String hike_location = intent.getStringExtra("hike_location");
        String hike_date = intent.getStringExtra("hike_date");
        String hike_availablePark = intent.getStringExtra("hike_availablePark");
        String hike_difficulty = intent.getStringExtra("hike_difficulty");
        String hike_weather = intent.getStringExtra("hike_weather");
        String hike_description = intent.getStringExtra("hike_description");
        int hike_length = intent.getIntExtra("hike_length", 0); // Default value if not provided
        int hike_teamSize = intent.getIntExtra("hike_teamSize", 0); // Default value if not provided

        Log.d("hike_length", "Hike Weather: " + hike_length);
        Log.d("hike_teamSize", "hike_teamSize: " + hike_teamSize);
        Log.d("hike_difficulty", "hike_difficulty: " + hike_difficulty);

        // Capture the layout's TextView and set the string as its text
        nameEditText = findViewById(R.id.nameText);
        locationEditText = findViewById(R.id.locationText);
        dateEditText = findViewById(R.id.dateText);
        lengthEditText = findViewById(R.id.lengthText);
        teamEditText = findViewById(R.id.teamText);
        weatherEditText = findViewById(R.id.weatherText);
        descriptionEditText = findViewById(R.id.descriptionText);
        parkSpinner = findViewById(R.id.parkSpinner);
        difficultySpinner = findViewById(R.id.difficultySpinner);

        String[] items = new String[]{"Yes", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        parkSpinner.setAdapter(adapter);

        String[] items2 = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        difficultySpinner.setAdapter(adapter2);

        // Set value
        nameEditText.setText(hike_name);
        locationEditText.setText(hike_location);
        dateEditText.setText(hike_date);
        lengthEditText.setText("" + hike_length);
        teamEditText.setText("" + hike_teamSize);
        weatherEditText.setText(hike_weather);
        descriptionEditText.setText(hike_description);

        int difficultyPosition = adapter2.getPosition(hike_difficulty);  // Use adapter2 for the difficulty Spinner
        difficultySpinner.setSelection(difficultyPosition);
        int parkPosition = adapter.getPosition(hike_availablePark);  // Use adapter for the park Spinner
        parkSpinner.setSelection(parkPosition);




        Button saveDetailsButton = findViewById(R.id.updateHikeButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String length = lengthEditText.getText().toString();
                String team = teamEditText.getText().toString();
                String weather = weatherEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if (name.isEmpty() || location.isEmpty() || date.isEmpty() || length.isEmpty() ||
                        team.isEmpty() || weather.isEmpty()) {
                    Toast.makeText(HikeDetailsActivity.this, "Please enter all required information!", Toast.LENGTH_LONG).show();
                }
                else {
                    Hike hike = new Hike();
                    hike.hike_id = Id;
                    hike.name = name;
                    hike.location = location;
                    hike.date = date;
                    hike.length = Integer.parseInt(length);
                    hike.teamSize = Integer.parseInt(team);
                    hike.weather = weather;
                    hike.description = description;
                    hike.availablePark = parkSpinner.getSelectedItem().toString();
                    hike.difficulty = difficultySpinner.getSelectedItem().toString();

                    appDatabase.hikeDao().updateHike(hike);
                    Toast.makeText(HikeDetailsActivity.this, "Update successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HikeDetailsActivity.this, HikeActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteHikeButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.hikeDao().deleteHike(Id);
                Toast.makeText(HikeDetailsActivity.this, "Delete successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HikeDetailsActivity.this, HikeActivity.class);
                startActivity(intent);
            }
        });
    }
}
