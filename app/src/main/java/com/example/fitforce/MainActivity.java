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
            new ExerciseInfo( "Push Up", "A basic chest exercise", "Chest", "http://example.com/pushup", "Place hands in shoulder-width, lower chest, then push up."),
            new ExerciseInfo( "Push Up - wide", "A  chest exercise that works mainly on the wider parts of the chest", "chest, shoulders, and upper arms", "http://example.com/pushup", "Place hands wider than shoulder-width, lower chest, then push up."),
            new ExerciseInfo( "Push Up - narrow", "A  chest exercise that works mainly on the narrower parts of the chest", "triceps and chest ", "http://example.com/pushup", "Place hands narrower than shoulder-width, lower chest, then push up."),
            new ExerciseInfo("Squat", "A basic lower body exercise", "quadriceps, hamstrings and butt", "http://example.com/squat", "Stand with feet shoulder-width apart, lower hips back and down, then rise back up."),
            new ExerciseInfo("pull up - wide", "A  back exercise that works mainly on the wider parts of the back", "back and arms", "http://example.com/squat", "Grip the bar wide, pull up until your chin is over the bar, then lower back down."),
            new ExerciseInfo("pull up ", "A  basic back exercise ", "back and arms", "http://example.com/squat", "Grip the bar in shoulders width , pull up until your chin is over the bar, then lower back down."),
            new ExerciseInfo("pull up - underhand grip", "A back and biceps exercise", "back and biceps", "http://example.com/squat", "Grab the bar with palms facing you, pull your chin over the bar, then lower yourself down."),
            new ExerciseInfo("Lunges", "A basic lower body exercise", "Legs and butt", "http://example.com/squat", "Step forward with one leg, bending both knees until the front thigh is parallel to the floor, then return to starting position and switch legs."),
            new ExerciseInfo("Dips", "A basic triceps exercise", "triceps", "http://example.com/squat", "Grip bars, lower the body and then extend arms back up."),
            new ExerciseInfo("Planks", "A basic abs exercise", "abs", "http://example.com/squat", "Hold a plank by supporting your body on your forearms and toes, keeping your back straight and core engaged."),
            new ExerciseInfo("Burpees", "full-body exercise", "legs, hips, buttocks, abdomen, arms, chest, and shoulders", "http://example.com/squat", "Perform a burpee by squatting, kicking back into a plank, doing a push-up, jumping feet to hands, and explosively jumping up."),

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

