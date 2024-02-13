package com.example.fitforce;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.example.Fragment.*;
import com.example.fitforce.HomeFragment;
import com.example.fitforce.R;
import com.example.fitforce.SettingsFragment;
import com.example.fitforce.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.stats);
    }

    HomeFragment HomeFragment = new HomeFragment();
    StatsFragment StatsFragment = new StatsFragment();
    SettingsFragment SettingsFragment = new SettingsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.stats) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, HomeFragment)
                    .commit();
            return true;
        }

            if (item.getItemId() == R.id.settings) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, StatsFragment)
                        .commit();
                return true;
            }

                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flFragment, SettingsFragment)
                            .commit();
                    return true;
                }
                return false;

        }
    }

