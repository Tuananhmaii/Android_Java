package com.example.coursework.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.coursework.Adapter.HikeAdapter;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hike);
        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sqlite3_example_db")
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

            public void onObservationClick(int position) {
                Hike hike = hikeList.get(position);
                Intent intent = new Intent(HikeActivity.this, ObservationActivity.class);
                intent.putExtra("hike_id", Long.toString(hike.hike_id));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        FloatingActionButton addHikeButton = findViewById(R.id.addHikeButton);
        addHikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HikeAddActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton deleteAllButton = findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HikeActivity.this);
                builder.setTitle("Delete all hike")
                        .setMessage("Do you want to delete all hike ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appDatabase.hikeDao().deleteAllHike();
                                dialog.dismiss();
                                Intent intent = new Intent(HikeActivity.this, HikeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                List<Hike> filteredList = new ArrayList<>();;



                for (Hike item : hikeList) {
                    if (item.name.toLowerCase().contains(text.toLowerCase()) ||
                            item.location.toLowerCase().contains(text.toLowerCase()) ||
                            String.valueOf(item.length).toLowerCase().contains(text.toLowerCase()) ||
                            item.date.toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }
        });
    }
}