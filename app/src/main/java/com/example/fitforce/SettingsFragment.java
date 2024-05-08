package com.example.fitforce;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



    public class SettingsFragment extends Fragment implements View.OnClickListener {
        ImageView pp;
        TextView firstName, lastName, date;

        public SettingsFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_settings, container, false);
            pp = view.findViewById(R.id.ivProfile_on_settings);
            firstName = view.findViewById(R.id.first_name_on_settings);
            lastName = view.findViewById(R.id.lastNameOnSettings);
            date=view.findViewById(R.id.birthDateOnSettings);
            firstName.setOnClickListener(this);

            lastName.setOnClickListener(this);
            pp.setOnClickListener(this);

            // Retrieve the saved first name and last name from SharedPreferences
            SharedPreferences preferences = requireActivity().getSharedPreferences("names", MODE_PRIVATE);
            String savedFirstName = preferences.getString("firstName", "");
            String savedLastName = preferences.getString("lastName", "");

            // Set the TextViews to display the saved first name and last name
            firstName.setText(savedFirstName);
            lastName.setText(savedLastName);
            SharedPreferences datePreferences = requireActivity().getSharedPreferences("dates", MODE_PRIVATE);
            int savedYear = datePreferences.getInt("year", -1);
            int savedMonth = datePreferences.getInt("month", -1);
            int savedDay = datePreferences.getInt("day", -1);

            if (savedYear != -1 && savedMonth != -1 && savedDay != -1) {
                // Format the date string
                String formattedDate = savedDay + "/" + savedMonth + "/" + savedYear;
                // Set the TextView to display the saved date
                date.setText(formattedDate);
            } else {
                // Handle case where no date is saved
            }
            // Retrieve the saved image resource ID from SharedPreferences
            SharedPreferences imagePreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            int savedImageResourceId = imagePreferences.getInt("imageResourceId", -1);

            // Get reference to the ImageView in the fragment layout
            ImageView imageView = view.findViewById(R.id.ivProfile_on_settings);

            if (savedImageResourceId != -1) {
                // Set the ImageView to display the saved image
                imageView.setImageResource(savedImageResourceId);
            } else {
                // Handle case where no image is saved
            }

            // Inflate the layout for this fragment
            return view;
        }

        @Override
        public void onClick(View v) {
            // Handle click events if needed
        }
    }

