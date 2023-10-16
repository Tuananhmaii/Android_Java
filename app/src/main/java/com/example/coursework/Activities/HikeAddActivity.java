package com.example.coursework.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class HikeAddActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    EditText nameEditText;
    EditText locationEditText;
    EditText dateEditText;
    EditText lengthEditText;
    EditText teamEditText;
    EditText weatherEditText;
    EditText descriptionEditText;
    Spinner parkSpinner;
    Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hike_add);

        // Initialize spinners
        parkSpinner = findViewById(R.id.parkSpinner);
        difficultySpinner = findViewById(R.id.difficultySpinner);

        String[] items = new String[]{"Yes", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        parkSpinner.setAdapter(adapter);

        String[] items2 = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        difficultySpinner.setAdapter(adapter2);

        nameEditText = findViewById(R.id.nameText);
        locationEditText = findViewById(R.id.locationText);
        dateEditText = findViewById(R.id.dateText);
        lengthEditText = findViewById(R.id.lengthText);
        teamEditText = findViewById(R.id.teamText);
        weatherEditText = findViewById(R.id.weatherText);
        descriptionEditText = findViewById(R.id.descriptionText);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite2_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        Button saveHikeButton = findViewById(R.id.saveHikeButton);

        saveHikeButton.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(HikeAddActivity.this, "Please enter all required information!", Toast.LENGTH_LONG).show();
                } else {
                    Hike hike = new Hike();
                    hike.name = name;
                    hike.location = location;
                    hike.date = date;
                    hike.length = Integer.parseInt(length);
                    hike.teamSize = Integer.parseInt(team);
                    hike.weather = weather;
                    hike.description = description;
                    hike.availablePark = parkSpinner.getSelectedItem().toString();
                    hike.difficulty = difficultySpinner.getSelectedItem().toString();

                    String message = "Name: " + name + "\n" +
                            "Location: " + location + "\n" +
                            "Date of hike: " + date + "\n" +
                            "Length: " + length + "\n" +
                            "Available park: " + parkSpinner.getSelectedItem().toString() + "\n" +
                            "Difficulty: " + difficultySpinner.getSelectedItem().toString() + "\n" +
                            "Team Size: " + team + "\n" +
                            "Weather: " + weather + "\n" +
                            "Description: " + description + "\n";

                    AlertDialog.Builder builder = new AlertDialog.Builder(HikeAddActivity.this);
                    builder.setTitle("Add new hike")
                            .setMessage(message)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    appDatabase.hikeDao().insertHike(hike);
                                    dialog.dismiss();
                                    Intent intent = new Intent(HikeAddActivity.this, HikeActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}
