package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FitnessSessionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvExerciseTitle, tvExercisedDescription,
            tvExercisedExplaination, tvExercisedMuscle,
            tvExerciseLink;// טקסטים שיציגו את פרטי התרגיל
    Button btNext;// כפתור ששולח לתרגיל הבא דרך מסך הטיימר
    exerciseHelper sh;// דרכו נעשה פעולות על טבלת הdataBase
    SharedPreferences sp,count;// לשמירת נתוני כמות אימונים וID של אימון נוכחי
    SharedPreferences.Editor editor,countEditor;// עורך את הנ"ל

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_session);
        sp = getSharedPreferences("exerciseID", MODE_PRIVATE);
        editor = sp.edit();
        count = getSharedPreferences("sessionsCounter", MODE_PRIVATE);
        countEditor = count.edit();
        tvExerciseTitle = findViewById(R.id.tvFitTitle);
        tvExercisedDescription = findViewById(R.id.tvFitDescription);
        tvExercisedMuscle = findViewById(R.id.tvFitMuscleGroup);
        tvExercisedExplaination = findViewById(R.id.tvFitExpalin);
        tvExerciseLink = findViewById(R.id.tvFitLink);
        btNext = findViewById(R.id.btNextFitExercise);
        btNext.setOnClickListener(this);
        sh = new exerciseHelper(this);
        sh.open();

        try{
            insertFieldsByID(sp.getInt("i", 0), "fitness");

        }
        catch (IndexOutOfBoundsException e){
            editor.putInt("i", 0);
            editor.apply();
            insertFieldsByID(sp.getInt("i", 0), "fitness");

        }
    }

    @Override
    public void onClick(View view) {
        if ( view == btNext){
            if (sp.getInt("i",0)+1>=sh.getAllExercises().size()){
                startActivity(new Intent(FitnessSessionActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Session ended successfully!", Toast.LENGTH_SHORT).show();
                editor.putInt("i", 0);
                editor.apply();
                countEditor.putInt("fitness", count.getInt("fitness",-1)+1);
                countEditor.apply();
            }
            else {
                startActivity(new Intent(FitnessSessionActivity.this, TimerActivity.class));
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