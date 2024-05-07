package com.example.fitforce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profileCreationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivProfile;
    EditText etFirstName, etLastName;
    Button btSubmitProfileCreationPage,btBirthDateChoosing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        btSubmitProfileCreationPage=findViewById(R.id.btSubmitProfileCreationPage);
        btBirthDateChoosing=findViewById(R.id.btBirthDateChoosing);
        ivProfile=findViewById(R.id.ivProfile);
        btSubmitProfileCreationPage.setOnClickListener(this);
        btBirthDateChoosing.setOnClickListener(this);
        ivProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btSubmitProfileCreationPage){
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getString("firstName", etFirstName.toString());
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getString("lastName", etLastName.toString());



            startActivity(new Intent(profileCreationActivity.this, MainActivity.class));

        }
        if (v==btBirthDateChoosing){

        }
        if (v==ivProfile){

        }
    }
}