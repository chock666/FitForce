package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class strengthSessionActivity extends AppCompatActivity implements View.OnClickListener {

    WebView wvAnimation;
    int i = 1;
    TextView tvExerciseTitle, tvExercisedDescription, tvExercisedExplaination, tvExercisedMuscle, tvExerciseLink;
    Button btNext;
    SolveHelper sh;
    Exercise exercise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_session);

        tvExerciseTitle = findViewById(R.id.tvTitle);
        tvExercisedDescription = findViewById(R.id.tvDescription);
        tvExercisedMuscle = findViewById(R.id.tvMuscleGroup);
        tvExercisedExplaination = findViewById(R.id.tvExpalin);
        tvExerciseLink = findViewById(R.id.tvLink);
        btNext = findViewById(R.id.btNextExercise);
        btNext.setOnClickListener(this);
        sh = new SolveHelper(this);
        sh.open();

        insertFieldsByID(i);
        //animationSettings(wvAnimation);
    }


    @Override
    public void onClick(View view) {
    if ( view == btNext){
        if (i+1>=sh.getAllExercises().size()){
            startActivity(new Intent(strengthSessionActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Session ended successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            i++;
            insertFieldsByID(i);
        }
            }



    }


    public void insertFieldsByID(int i){
        tvExercisedExplaination.setText(sh.getAllExercises().get(i).getShortExplanation().toString());
        tvExerciseLink.setText(sh.getAllExercises().get(i).getLink().toString());
        tvExercisedMuscle.setText(sh.getAllExercises().get(i).getMuscleGroup().toString());
        tvExerciseTitle.setText(sh.getAllExercises().get(i).getExerciseName().toString());
        tvExercisedDescription.setText(sh.getAllExercises().get(i).getDescription().toString());
    }
}