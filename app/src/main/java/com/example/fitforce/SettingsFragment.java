package com.example.fitforce;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
    ImageView pp; // תמונת פרופיל
    TextView firstName, lastName, date;// טקסטים שיציגו את הערכים האחרונים שהכניס המשתמש
    SharedPreferences preferences;//עתיד לשלוף את השמות מהמערכת
    SharedPreferences.Editor editor;//עתיד לשמור את השמות במערכת
    Button btEditFirstName, btEditLastName, btEditpp, btEditBirthDate; // כפתורים שיפתחו דיאלוגים לעריכה
    ActivityResultLauncher<Intent> startCamera;//תחליף לstartActivityForResult בשביל המצלמה
    ActivityResultLauncher<Intent> launcher;//תחליף לstartActivityForResult בשביל הגלריה
    Uri cam_uri; // מיקום התמונה במכשיר
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
        date = view.findViewById(R.id.birthDateOnSettings);
        btEditFirstName = view.findViewById(R.id.btEditFirstName);
        btEditLastName = view.findViewById(R.id.btEditLastName);
        btEditBirthDate = view.findViewById(R.id.btEditDateDialog);
        btEditpp = view.findViewById(R.id.btEditPp);
        firstName.setOnClickListener(this);
        btEditFirstName.setOnClickListener(this);
        btEditLastName.setOnClickListener(this);
        btEditpp.setOnClickListener(this);
        btEditBirthDate.setOnClickListener(this);


        setupImageCaptureAndSelection();

        SharedPreferences imagePreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        preferences = requireActivity().getSharedPreferences("names", MODE_PRIVATE);
        String savedFirstName = preferences.getString("firstName", "");
        String savedLastName = preferences.getString("lastName", "");
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
        }

        pp.setImageURI(Uri.parse(imagePreferences.getString("profile_image", "")));


        return view;
    }




    @Override
    public void onClick(View v) {
        if (v == btEditFirstName) {

            dialogForChangingStringValue("Change first name", "write the new name down below", "names", "firstName", firstName);
        }

        if (v == btEditLastName) {
            dialogForChangingStringValue("Change last name", "write the new name down below", "names", "lastName", lastName);

        }
        if (v == btEditBirthDate) {
            selectDate();
        }
        if (v == btEditpp) {
            // Create a dialog to choose between Camera and Gallery
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Select Option");
            builder.setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Take Photo

                            pickCamera();

                            break;
                        case 1: // Choose from Gallery
                            //Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            launcher.launch(intent);
                            //startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
                            break;
                    }
                }
            });
            builder.show();

            SharedPreferences imagePreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

            // Get reference to the ImageView in the fragment layout
            pp.setImageURI(Uri.parse(imagePreferences.getString("profile_image", "")));

        }

            }
    public void selectDate(){
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
    public String getRealPathFromURI(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        return result;
    }

    public void pickCamera() {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        startCamera.launch(cameraIntent);
    }






    // Method to save URI in SharedPreferences
    public  void saveUriToSharedPreferences(Context context, Uri uri, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, uri.toString());
        editor.apply();
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

        public void setupImageCaptureAndSelection (){
            launcher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {
                            cam_uri = result.getData().getData();
                            String realPath = getRealPathFromURI(getContext(), cam_uri);
                            if (realPath != null) {
                                // Now you can use the real path for your image processing
                                pp.setImageURI(Uri.parse(realPath));
                                saveUriToSharedPreferences(getContext(), Uri.parse(realPath), "profile_image");
                            } else {
                                // Handle the case where the real path could not be found
                                pp.setImageURI(cam_uri);
                                saveUriToSharedPreferences(getContext(), cam_uri, "profile_image");
                            }
                        }
                    }
            );

            startCamera = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            // There are no request codes
                            pp.setImageURI(cam_uri);

                            saveUriToSharedPreferences(getContext(), cam_uri, "profile_image");

                        }
                    }
            );
        }






    }

