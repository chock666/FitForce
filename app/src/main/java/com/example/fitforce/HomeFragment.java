package com.example.fitforce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment implements View.OnClickListener {
    Button swimmingButton, runningButton, strengthButton, fitnessButton;
    View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


         view = inflater.inflate(R.layout.fragment_home, container, false);
        swimmingButton = view.findViewById(R.id.swimming_button);
        runningButton = view.findViewById(R.id.running_button);
        strengthButton = view.findViewById(R.id.Strength_button);
        fitnessButton = view.findViewById(R.id.fitness_button);
        strengthButton.setOnClickListener(this);
        fitnessButton.setOnClickListener(this);

        runningButton.setOnClickListener(this);
        swimmingButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {
        if (view == swimmingButton){
            Intent switchActivityIntent = new Intent(getContext(), inSwimmingActivity.class);
            startActivity(switchActivityIntent);
        }
        if (view == runningButton){
            Intent switchActivityIntent = new Intent(getContext(), InRunningActivity.class);
            startActivity(switchActivityIntent);
        }
        if (view == strengthButton){
            Intent switchActivityIntent = new Intent(getContext(), InStrengthActivity.class);
            startActivity(switchActivityIntent);
        }
        if (view == fitnessButton){
            Intent switchActivityIntent = new Intent(getContext(), InFitnessActivity.class);
            startActivity(switchActivityIntent);
        }

    }


}