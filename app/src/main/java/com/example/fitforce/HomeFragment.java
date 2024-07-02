package com.example.fitforce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class HomeFragment extends Fragment implements View.OnClickListener {
    Button  runningButton, strengthButton, fitnessButton;
    SharedPreferences sp;
    TextView tvName;
    View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


         view = inflater.inflate(R.layout.fragment_home, container, false);
        runningButton = view.findViewById(R.id.running_button);
        strengthButton = view.findViewById(R.id.Strength_button);
        tvName = view.findViewById(R.id.name);
        fitnessButton = view.findViewById(R.id.fitness_button);
        strengthButton.setOnClickListener(this);
        fitnessButton.setOnClickListener(this);
        sp = requireActivity().getSharedPreferences("names", Context.MODE_PRIVATE);
        tvName.setText(sp.getString("firstName",""));

        runningButton.setOnClickListener(this);
        return view;


    }



    @Override
    public void onClick(View view) {

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