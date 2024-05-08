package com.example.fitforce;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;import java.util.Calendar;


public class profileCreationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivProfile;
    EditText etFirstName, etLastName;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    Button btSubmitProfileCreationPage,btBirthDateChoosing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);
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
        if (v == btSubmitProfileCreationPage) {
            // Save first name and last name to SharedPreferences
            preferences = getSharedPreferences("names", MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("firstName", etFirstName.getText().toString());
            editor.putString("lastName", etLastName.getText().toString());
            editor.apply();

            // Save image resource ID to SharedPreferences
            preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            editor = preferences.edit();
            editor.putInt("imageResourceId", R.drawable.profile_pic); // Replace "profile_pic" with your image resource
            editor.apply();

            // Retrieve image resource ID from SharedPreferences
            int savedImageResourceId = preferences.getInt("imageResourceId", -1);

            if (savedImageResourceId != -1) {
                // Set ImageView to display the saved image
                ivProfile.setImageResource(savedImageResourceId);
            } else {
                // Handle case where no image is saved
            }

            startActivity(new Intent(profileCreationActivity.this, MainActivity.class));
        }
        if (v==btBirthDateChoosing){
            Calendar systemCalender = Calendar.getInstance();
            int year = systemCalender.get(Calendar.YEAR);
            int month = systemCalender.get(Calendar.MONTH);
            int day = systemCalender.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,new SetDate(),year,month,day);
            datePickerDialog.show();

        }
        if (v==ivProfile){

        }
    }

    public  class SetDate implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear +1;

            String str = "You selected :" + dayOfMonth + "/" + monthOfYear +"/" + year;
            btBirthDateChoosing.setText(str);
            preferences = getSharedPreferences("dates", MODE_PRIVATE);
            editor = preferences.edit();
            editor.putInt("year", year);
            editor.putInt("month", monthOfYear);
            editor.putInt("day", dayOfMonth);
            editor.apply();

        }
    }


}