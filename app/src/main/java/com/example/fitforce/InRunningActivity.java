package com.example.fitforce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InRunningActivity extends AppCompatActivity implements View.OnClickListener {
    String [] times = {"3:00 mpk", "3:20 mpk","3:40 mpk","4:00 mpk", "4:20 mpk"
            ,"4:40 mpk","5:00 mpk", "5:20 mpk","5:40 mpk","6:00 mpk",
            "6:20 mpk","6:40 mpk","7:00 mpk"};

    String [] distances = {"1 km", "1.5 km", "2 km","2.5 km", "3 km", "3.5 km","4 km", "4.5 km", "5 km",
            "5.5 km", "6 km","6.5 km", "7 km", "7.5 km"};
    int sessionsCounter;
    Button btRunQuicker, btRunSlower,btRunFurther,btRunLess, btRunningEnd, btCancel;
    TextView pace, distance;

    SharedPreferences sp, checker;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_running);
        btRunQuicker = findViewById(R.id.btRunQuicker);
        btRunSlower = findViewById(R.id.btRunSlower);
        btRunningEnd = findViewById(R.id.btRunningEnd);
        btCancel = findViewById(R.id.btRunningCancel);
        pace = findViewById(R.id.RunningPace);
        distance = findViewById(R.id.RunningDistance);
        btRunFurther = findViewById(R.id.btRunFurther);
        btRunLess = findViewById(R.id.btRunLess);
        btRunQuicker.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btRunLess.setOnClickListener(this);
        btRunFurther.setOnClickListener(this);
        btRunSlower.setOnClickListener(this);
        btRunningEnd.setOnClickListener(this);
        sp = getSharedPreferences("sessionsCounter", MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
        checker = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        try {
            if (sp.getInt("running", 0)%4-2==0){
                for (int i=0; i<=distances.length; i++){
                    if(distances[i].equals(sp.getString("distances", "").toString())){
                        if(i<times.length){
                            editor.putString("distances",distances[i+1]);
                            editor.apply();

                            distance.setText(sp.getString("distances", "").toString());
                        }

                        break;
                    }

                }

            }
            if (sp.getInt("running", 0)%4==0){

                for (int i=times.length-1; i>=0; i--){
                    if(times[i].equals(sp.getString("Times", "").toString())){
                        if(i>0){
                            editor.putString("Times",times[i-1]);
                            editor.apply();

                            pace.setText(sp.getString("Times", "").toString());
                        }

                        break;
                    }


                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            editor.putString("Times", times[7]);
            editor.putString("distances", distances[4]);
            editor.apply();
        }




        pace.setText(sp.getString("Times","" ).toString());
        distance.setText(sp.getString("distances","" ).toString());

        if (sp.getString("Times","" ).toString().equals("")||sp.getString("distances","" ).toString().equals("")){

            pace.setText(times[7].toString());
            distance.setText(distances[4].toString());
            editor.putString("Times", times[7]);
            editor.putString("distances", distances[4]);
            editor.apply();
        }

    }

    @Override
    public void onClick(View v) {
        if(v== btRunQuicker){
            for (int i=times.length-1; i>=0; i--){
                if(times[i].equals(sp.getString("Times", "").toString())){
                    if(i>0){
                        editor.putString("Times",times[i-1]);
                        editor.apply();

                        pace.setText(sp.getString("Times", "").toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You're in the highest pace available", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }

        if(v== btRunSlower){
            for (int i=0; i<times.length; i++){
                if(times[i].equals(sp.getString("Times", "").toString())){
                    if(i<times.length-1){
                        editor.putString("Times",times[i+1]);
                        editor.apply();

                        pace.setText(sp.getString("Times", "").toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You're in the slowest pace available", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }
        if(v== btRunFurther){
            for (int i=0; i<=distances.length; i++){
                if(distances[i].equals(sp.getString("distances", "").toString())){
                    if(i<times.length){
                        editor.putString("distances",distances[i+1]);
                        editor.apply();

                        distance.setText(sp.getString("distances", "").toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "There is no need to run further than 7.5 km", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }

        if(v== btRunLess){
            for (int i=distances.length-1; i>=0; i--){
                if(distances[i].equals(sp.getString("distances", "").toString())){
                    if(i>0){
                        editor.putString("distances",distances[i-1]);
                        editor.apply();

                        distance.setText(sp.getString("distances", "").toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "1KM is the shortest distance available", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }
        if (v==btRunningEnd){
            startActivity(new Intent(InRunningActivity.this, MainActivity.class));
            addOneToSessionsCounter();
        }

        if (v==btCancel){
            startActivity(new Intent(InRunningActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Session is canceled", Toast.LENGTH_LONG).show();
        }


    }

    public void addOneToSessionsCounter (){
        sessionsCounter = sp.getInt("running", 0);
        editor.putInt("running", sessionsCounter+1);
        editor.apply();
    }
}