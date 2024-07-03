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

    private CircularProgressBar progressCircular; // משתנה עבור הבר העגול שמציג את ההתקדמות
    private TextView timerText; // משתנה עבור הטקסט שמציג את הזמן הנותר
    exerciseHelper sh; // משתנה עבור העזרה לטיפול בטבלת הנתונים
    SharedPreferences.Editor editor; // עורך עבור SharedPreferences
    SharedPreferences sp; // משתנה SharedPreferences עבור ID האימון
    private Button skipButton; // כפתור לדילוג על הטיימר
    private CountDownTimer countDownTimer; // משתנה עבור הטיימר עצמו
    private long timeLeftInMillis = 30000; // זמן נותר במילישניות (30 שניות)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        sh = new exerciseHelper(this); // יצירת אובייקט עזרה
        sh.open(); // פתיחת העזרה
        sp = getSharedPreferences("exerciseID", MODE_PRIVATE); // קבלת SharedPreferences
        editor = sp.edit(); // יצירת עורך SharedPreferences

        progressCircular = findViewById(R.id.progress_circular); // חיבור משתנה הבר העגול לתצוגה
        timerText = findViewById(R.id.timer_text); // חיבור משתנה הטקסט לתצוגה
        skipButton = findViewById(R.id.skip_button); // חיבור משתנה כפתור הדילוג לתצוגה

        startTimer(); // התחלת הטיימר

        skipButton.setOnClickListener(new View.OnClickListener() { // הגדרת מאזין ללחיצת כפתור הדילוג
            @Override
            public void onClick(View v) {
                skipTimer(); // קריאה לפונקציה לדילוג על הטיימר
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // יצירת אובייקט הטיימר עם זמן סיום של 30 שניות וקריאה כל שנייה
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished; // עדכון הזמן הנותר
                updateTimer(); // עדכון התצוגה של הזמן וההתקדמות
            }

            @Override
            public void onFinish() {
                // התחלת הפעילות החדשה כאשר הטיימר מסתיים
                finish(); // סיום הפעילות הנוכחית
                IntentBack(); // קריאה לפונקציה לעבור לפעילות הבאה
            }
        }.start(); // התחלת הטיימר
    }

    private void updateTimer() {
        int secondsLeft = (int) (timeLeftInMillis / 1000); // חישוב השניות הנותרות
        float progress = (float) ((30000 - timeLeftInMillis) / 300); // חישוב ההתקדמות באחוזים
        progressCircular.setProgress(progress); // עדכון הבר העגול עם ההתקדמות
        timerText.setText(secondsLeft + "s"); // עדכון הטקסט עם השניות הנותרות
    }

    private void skipTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // ביטול הטיימר אם הוא פעיל
        }
        // טיפול בפעולה של דילוג, יתכן והתחלת הפעילות החדשה

        finish(); // סיום הפעילות הנוכחית
        IntentBack(); // קריאה לפונקציה לעבור לפעילות הבאה
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // ביטול הטיימר אם הפעילות נהרסת
        }
    }

    public void IntentBack() {
        // בדיקה אם סוג האימון הוא "strength" והתחלת הפעילות המתאימה
        if(sh.getAllExercises().get(sp.getInt("i",0)).getType().equals("strength")){
            Intent intent = new Intent(TimerActivity.this, strengthSessionActivity.class);
            editor.putInt("i", sp.getInt("i", 0)+1); // עדכון ID האימון הנוכחי
            editor.apply(); // שמירת העדכון
            startActivity(intent); // התחלת הפעילות החדשה
        }
        // בדיקה אם סוג האימון הוא "fitness" והתחלת הפעילות המתאימה
        else if(sh.getAllExercises().get(sp.getInt("i",0)).getType().equals("fitness")){
            Intent intent = new Intent(TimerActivity.this, FitnessSessionActivity.class);
            editor.putInt("i", sp.getInt("i", 0)+1); // עדכון ID האימון הנוכחי
            editor.apply(); // שמירת העדכון
            startActivity(intent); // התחלת הפעילות החדשה
        }
    }
}
