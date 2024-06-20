package com.example.fitforce;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
//import com.example.Fragment.*;
import dataBaseRoom.AppDatabase;
import dataBaseRoom.ExerciseInfo;
import dataBaseRoom.ExerciseInfoDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ImageView pp;
    private AppDatabase db;
    private ExerciseInfoDao dao;
    HomeFragment HomeFragment = new HomeFragment();
    StatsFragment StatsFragment = new StatsFragment();
    SettingsFragment SettingsFragment = new SettingsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(MainActivity.this, profileCreationActivity.class));


        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);



        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "exercise-database")
                .build();

        dao = db.exerciseInfoDao();

        // Insert initial data
        insertFixedFields();



        ExerciseInfo firstExercise = PREPOPULATE_DATA[0];
        String exerciseName = firstExercise.getName();
        Toast.makeText(MainActivity.this, "First Exercise: " + exerciseName, Toast.LENGTH_SHORT).show();


    }
    private void insertFixedFields() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(PREPOPULATE_DATA);
            }
        }).start();
    }

    private static final ExerciseInfo[] PREPOPULATE_DATA = {
            new ExerciseInfo( "Push Up", "A basic upper body exercise", "Chest", "http://example.com/pushup", "Strengthens chest and arms"),
            new ExerciseInfo("Squat", "A basic lower body exercise", "Legs", "http://example.com/squat", "Strengthens legs and glutes"),
            // Add more fixed fields here
    };




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.stats) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, StatsFragment)
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, SettingsFragment)
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, HomeFragment)
                    .commit();
            return true;
        }
        return false;

    }
}

