package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.content.Intent;

public class TimerActivity extends AppCompatActivity {

    private CircularProgressBar progressCircular;
    private TextView timerText;
    exerciseHelper sh;
    SharedPreferences.Editor editor;

    SharedPreferences sp;

    private Button skipButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        sh = new exerciseHelper(this);
        sh.open();
        sp = getSharedPreferences("exerciseID", MODE_PRIVATE);
        editor = sp.edit();

        progressCircular = findViewById(R.id.progress_circular);
        timerText = findViewById(R.id.timer_text);
        skipButton = findViewById(R.id.skip_button);

        startTimer();

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTimer();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Start the new activity when the timer finishes
                finish();
                IntentBack();


            }
        }.start();
    }

    private void updateTimer() {
        int secondsLeft = (int) (timeLeftInMillis / 1000);
        float progress = (float) ((30000 - timeLeftInMillis) / 300);
        progressCircular.setProgress(progress);
        timerText.setText(secondsLeft + "s");
    }

    private void skipTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        // Handle skip action, possibly start the new activity

        finish();
        IntentBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void IntentBack(){

        if(sh.getAllExercises().get(sp.getInt("i",0)).getType().equals("strength")){
            Intent intent = new Intent(TimerActivity.this, strengthSessionActivity.class);
            editor.putInt("i", sp.getInt("i", 0)+1);
            editor.apply();
            startActivity(intent);
        }
        else if(sh.getAllExercises().get(sp.getInt("i",0)).getType().equals("fitness")){
            Intent intent = new Intent(TimerActivity.this, FitnessSessionActivity.class);
            editor.putInt("i", sp.getInt("i", 0)+1);
            editor.apply();
            startActivity(intent);

        }
    }
}
