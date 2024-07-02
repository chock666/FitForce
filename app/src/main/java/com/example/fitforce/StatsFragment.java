package com.example.fitforce;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {

    private BarChartView strengthBarChart;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private TextView tvAllCount, tvRunningCount, tvFitnessCount,tvStrengthCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        strengthBarChart = view.findViewById(R.id.strengthBarChart);
        tvAllCount = view.findViewById(R.id.allCount);
        tvRunningCount = view.findViewById(R.id.runningCount);
        tvFitnessCount = view.findViewById(R.id.fitnessCount);
        tvStrengthCount = view.findViewById(R.id.strengthCount);
        sp = requireActivity().getSharedPreferences("sessionsCounter", Context.MODE_PRIVATE);
        editor = sp.edit();
        if (String.valueOf(sp.getInt("running", -1))=="-1"){
            tvRunningCount.setText("0");
        }
        else {
            tvRunningCount.setText(String.valueOf(sp.getInt("running", -1)));

        }

        if (String.valueOf(sp.getInt("strength", -1))=="-1"){
            tvStrengthCount.setText("0");
        }
        else {
            tvStrengthCount.setText(String.valueOf(sp.getInt("strength", -1)));

        }
        if (String.valueOf(sp.getInt("fitness", -1))=="-1"){
            tvFitnessCount.setText("0");
        }
        else {
            tvFitnessCount.setText(String.valueOf(sp.getInt("fitness", -1)));

        }
        tvAllCount.setText(String.valueOf((sp.getInt("fitness", -1)+sp.getInt("strength", -1)+sp.getInt("running", -1))));



        // Example data
        float[] CharmData = {sp.getInt("running", -1), sp.getInt("strength", -1), sp.getInt("fitness", -1)};


        // Set data for charts
        strengthBarChart.setData(CharmData);

        return view;
    }
}
