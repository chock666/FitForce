package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InStrengthActivity extends TrainingActivity implements View.OnClickListener {

    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_strength);
        btStart = findViewById(R.id.btStartStrengthSession);
        btStart.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == btStart){
            startActivity(new Intent(InStrengthActivity.this,strengthSessionActivity.class));
        }
    }
}