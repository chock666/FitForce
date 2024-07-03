package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.widget.Toast;


public class strengthSessionActivity extends AppCompatActivity implements View.OnClickListener {



    TextView tvExerciseTitle, tvExercisedDescription,
            tvExercisedExplaination, tvExercisedMuscle,
            tvExerciseLink;// טקסטים שיציגו את פרטי התרגיל
    Button btNext; // כפתור ששולח לתרגיל הבא דרך מסך הטיימר
    exerciseHelper sh;// דרכו נעשה פעולות על טבלת הdataBase
    SharedPreferences sp,count;// לשמירת נתוני כמות אימונים וID של אימון נוכחי
    SharedPreferences.Editor editor,countEditor;// עורך את הנ"ל



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_session);
        count = getSharedPreferences("sessionsCounter", MODE_PRIVATE);
        countEditor = count.edit();
        sp = getSharedPreferences("exerciseID", MODE_PRIVATE);
        editor = sp.edit();

        tvExerciseTitle = findViewById(R.id.tvTitle);
        tvExercisedDescription = findViewById(R.id.tvDescription);
        tvExercisedMuscle = findViewById(R.id.tvMuscleGroup);
        tvExercisedExplaination = findViewById(R.id.tvExpalin);
        tvExerciseLink = findViewById(R.id.tvLink);
        btNext = findViewById(R.id.btNextExercise);
        btNext.setOnClickListener(this);


        sh = new exerciseHelper(this);
        sh.open();

        try{
            insertFieldsByID(sp.getInt("i", 0), "strength");
        }
        catch (IndexOutOfBoundsException e){
            editor.putInt("i", 0);
            editor.apply();
            insertFieldsByID(sp.getInt("i", 0), "strength");
        }


    }


    @Override
    public void onClick(View view) {
    if ( view == btNext){
        if (sp.getInt("i",0)+1>=sh.sizeByType("strength")){
            startActivity(new Intent(strengthSessionActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Session ended successfully!", Toast.LENGTH_SHORT).show();
            editor.putInt("i", 0);
            editor.apply();
            countEditor.putInt("strength", count.getInt("strength",-1)+1);
            countEditor.apply();
        }
        else {
            startActivity(new Intent(strengthSessionActivity.this, TimerActivity.class));


        }
            }



    }


    public void insertFieldsByID(int i, String type){
        if (sh.getAllExercises().get(i).getType().equals(type)){
            tvExercisedExplaination.setText(sh.getAllExercises().get(i).getShortExplanation().toString());
            tvExerciseLink.setText(sh.getAllExercises().get(i).getLink().toString());
            tvExercisedMuscle.setText(sh.getAllExercises().get(i).getMuscleGroup().toString());
            tvExerciseTitle.setText(sh.getAllExercises().get(i).getExerciseName().toString());
            tvExercisedDescription.setText(sh.getAllExercises().get(i).getDescription().toString());
        }
        else{
            editor.putInt("i", sp.getInt("i", 0)+1);
            editor.apply();
            insertFieldsByID(sp.getInt("i", 0), type);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt("i", 0);
        editor.apply();
    }
}