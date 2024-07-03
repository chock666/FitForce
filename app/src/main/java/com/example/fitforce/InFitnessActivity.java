package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InFitnessActivity extends TrainingActivity implements View.OnClickListener {
    Button btStart; // כפתור למעבר לאימון עצמו

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_fitness);
        btStart=findViewById(R.id.btStartFitnessSession);
        btStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==btStart){
            startActivity(new Intent(InFitnessActivity.this,FitnessSessionActivity.class));

        }
    }
}