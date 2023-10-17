package com.example.coursework.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ObservationAddActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    EditText nameEditText;
    EditText timeEditText;
    EditText descriptionEditText;
    EditText IdEditText;
    String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_add);

        Intent intent = getIntent();
        Id = intent.getStringExtra("hike_id");

        nameEditText = findViewById(R.id.nameText);
        timeEditText = findViewById(R.id.timeText);
        descriptionEditText = findViewById(R.id.descriptionText);

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(currentDate);
        timeEditText.setText(formattedDate);


        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite2_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        Button saveObservationButton = findViewById(R.id.saveObservationButton);
        saveObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if (name.isEmpty() || time.isEmpty()) {
                    Toast.makeText(ObservationAddActivity.this, "Please enter all required information!", Toast.LENGTH_LONG).show();
                }
                else {
                    Id = intent.getStringExtra("hike_id");
                    Observation observation = new Observation();
                    observation.name = name;
                    observation.time = time;
                    observation.description = description;
                    observation.hikeId = Long.parseLong(Id);

                    String message = "Name: " + name + "\n" +
                            "Time: " + time + "\n" +
                            "Description: " + description + "\n";

                    AlertDialog.Builder builder = new AlertDialog.Builder(ObservationAddActivity.this);
                    builder.setTitle("Add new observation")
                            .setMessage(message)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    appDatabase.observationDao().insertObservation(observation);
                                    Intent intent = new Intent(ObservationAddActivity.this, ObservationActivity.class);
                                    intent.putExtra("hike_id", Id);
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

//                    appDatabase.observationDao().insertObservation(observation);
//                    startActivity(intent);
                }
            }
        });
    }
}
