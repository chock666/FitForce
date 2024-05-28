package com.example.fitforce;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



    public class SettingsFragment extends Fragment implements View.OnClickListener {
        ImageView pp;
        TextView firstName, lastName, date;
        Button btEditFirstName, btEditLastName,btEditpp,btEditBirthDate;

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
            btEditFirstName.findViewById(R.id.btEditFirstName);
            btEditLastName.findViewById(R.id.btEditLastName);
            btEditBirthDate.findViewById(R.id.btEditDateDialog);
            btEditpp.findViewById(R.id.btEditPp);
            firstName.setOnClickListener(this);
            btEditFirstName.setOnClickListener(this);
            btEditLastName.setOnClickListener(this);
            btEditpp.setOnClickListener(this);
            btEditBirthDate.setOnClickListener(this);


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

            // Get reference to the ImageView in the fragment layout
            ImageView imageView ;
            pp.setImageBitmap(profileCreationActivity.base64ToBitmap(imagePreferences.getString("profile_image","")));




            return view;
        }
        public void saveToSharedPreferences(Context context, String key, String value) {
            // Get the SharedPreferences object
            SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);

            // Get the SharedPreferences editor to make changes
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Put the key-value pair in SharedPreferences
            editor.putString(key, value);

            // Apply the changes
            editor.apply();
        }



        @Override
        public void onClick(View v) {
            if (v==btEditFirstName){

            }
            if (v==btEditLastName){

            }


        }
    }

