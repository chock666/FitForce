package com.example.fitforce;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class SettingsFragment extends Fragment implements View.OnClickListener {
        ImageView pp;
        TextView firstName, lastName, date;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;

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
            btEditFirstName=view.findViewById(R.id.btEditFirstName);
            btEditLastName=view.findViewById(R.id.btEditLastName);
            btEditBirthDate=view.findViewById(R.id.btEditDateDialog);
            btEditpp=view.findViewById(R.id.btEditPp);
            firstName.setOnClickListener(this);
            btEditFirstName.setOnClickListener(this);
            btEditLastName.setOnClickListener(this);
            btEditpp.setOnClickListener(this);
            btEditBirthDate.setOnClickListener(this);


            // Retrieve the saved first name and last name from SharedPreferences
             preferences = requireActivity().getSharedPreferences("names", MODE_PRIVATE);
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
            if (v == btEditFirstName) {
//
                dialogForChangingStringValue("Change first name", "write the new name down below", "names", "firstName", firstName);
            }

            if (v == btEditLastName) {
                dialogForChangingStringValue("Change last name", "write the new name down below", "names", "lastName", lastName);

            }
            if (v == btEditBirthDate) {
                Calendar systemCalendar = Calendar.getInstance();
                int year = systemCalendar.get(Calendar.YEAR);
                int month = systemCalendar.get(Calendar.MONTH);
                int day = systemCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Increment month since DatePickerDialog returns month from 0-11
                        monthOfYear = monthOfYear + 1;

                        String str = "You selected: " + dayOfMonth + "/" + monthOfYear + "/" + year;
                        // Assuming 'date' is a TextView defined in your fragment
                        date.setText(str);

                        // Save the selected date to SharedPreferences
                        SharedPreferences preferences = requireContext().getSharedPreferences("dates", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("year", year);
                        editor.putInt("month", monthOfYear);
                        editor.putInt("day", dayOfMonth);
                        editor.apply();
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
            if (v==btEditpp){
                // Create a dialog to choose between Camera and Gallery
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Select Option");
                builder.setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Take Photo
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                                break;
                            case 1: // Choose from Gallery
                                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
                                break;
                        }
                    }
                });
                builder.show();

                SharedPreferences imagePreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

                // Get reference to the ImageView in the fragment layout
                pp.setImageBitmap(profileCreationActivity.base64ToBitmap(imagePreferences.getString("profile_image","")));

            }
            }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    saveBitmapToSharedPreferences(this.getContext(), imageBitmap, "profile_image");
                    pp.setImageBitmap(imageBitmap);
                    break;
                case REQUEST_PICK_PHOTO:
                    Uri selectedImage = data.getData();
                    pp.setImageURI(selectedImage);
                    saveUriToSharedPreferences(this.getContext(), selectedImage, "profile_image_uri");
                    break;
            }
        }
    }
    public static   Bitmap base64ToBitmap(String encodedBitmap) {
        byte[] decodedString = Base64.decode(encodedBitmap, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    // Method to save URI in SharedPreferences
    public  void saveUriToSharedPreferences(Context context, Uri uri, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, uri.toString());
        editor.apply();
    }

    public  void saveBitmapToSharedPreferences(Context context, Bitmap bitmap, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, bitmapToBase64(bitmap));
        editor.apply();
    }

    // Method to convert Bitmap to Base64 String
    private  String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
            public void dialogForChangingStringValue (String mainTitleOfDialog,String secondaryTitleOfDialog, String whereToSaveOnSharedPreferance, String inWhatName,TextView WhereToDisplay ){
            AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

            final EditText edittext = new EditText(this.getContext());
            alert.setMessage(secondaryTitleOfDialog);
            alert.setTitle(mainTitleOfDialog);

            alert.setView(edittext);

            alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Retrieve the value from the EditText
                    String newName = edittext.getText().toString();
                    // Do whatever you want with the value
                    preferences = requireContext().getSharedPreferences(whereToSaveOnSharedPreferance, MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString(inWhatName, newName);
                    editor.apply();
                    WhereToDisplay.setText(preferences.getString(inWhatName, ""));
                }
            });

            alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Handle the No option if needed
                    dialog.dismiss(); // Optionally dismiss the dialog
                }
            });

            alert.show();
        }
    }

