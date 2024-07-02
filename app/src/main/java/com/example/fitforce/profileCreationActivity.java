package com.example.fitforce;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class profileCreationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivProfile; // תמונת הפרופיל
    Uri cam_uri; // מיקום התמונה במכשיר
    SharedPreferences count; // מונה אימונים
    SharedPreferences.Editor editorCount;// עורך מונה אימונים

    EditText etFirstName, etLastName;// מקום להכנסת השמות למערכת
    SharedPreferences preferences; // עתיד לשמור את השמות במערכת

    SharedPreferences.Editor editor; // עורך של הנ"ל
    Button btSubmitProfileCreationPage, btBirthDateChoosing;// כפתורים לאישור/סיום ולפתיחת dateDialog

    ActivityResultLauncher<Intent> startCamera; //תחליף לstartActivityForResult בשביל המצלמה
    ActivityResultLauncher<Intent> launcher;//תחליף לstartActivityForResult בשביל הגלריה



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        count = getSharedPreferences("sessionsCounter", MODE_PRIVATE);
        editorCount = count.edit();


        // בודק ומבקש הרשאות
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }


        editorCount.putInt("fitness", 0);
        editorCount.putInt("strength", 0);
        editorCount.putInt("running", 0);
        editorCount.apply();


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btSubmitProfileCreationPage = findViewById(R.id.btSubmitProfileCreationPage);
        btBirthDateChoosing = findViewById(R.id.btBirthDateChoosing);
        ivProfile = findViewById(R.id.ivProfile);
        btSubmitProfileCreationPage.setOnClickListener(this);
        btBirthDateChoosing.setOnClickListener(this);
        ivProfile.setOnClickListener(this);


        setupImageCaptureAndSelection();
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



            startActivity(new Intent(profileCreationActivity.this, MainActivity.class));
        }
        if (v == btBirthDateChoosing) {
            Calendar systemCalender = Calendar.getInstance();
            int year = systemCalender.get(Calendar.YEAR);
            int month = systemCalender.get(Calendar.MONTH);
            int day = systemCalender.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new SetDate(), year, month, day);
            datePickerDialog.show();

        }
        if (v == ivProfile) {


            // Create a dialog to choose between Camera and Gallery
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Option");
            builder.setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Take Photo
                            pickCamera();
                            break;
                        case 1: // Choose from Gallery
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            launcher.launch(intent);
                            break;
                    }
                }
            });
            builder.show();
        }
    }

    public String getRealPathFromURI(Context context, Uri uri) {
        // Initialize a String variable to hold the resulting path
        String result = null;

        // Define a projection that specifies which columns from the database you will use after this query
        String[] proj = {MediaStore.Images.Media.DATA};

        // Query the content resolver with the URI to get a cursor that points to the result set of the query
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        // Check if the cursor is not null (i.e., the query returned some results)
        if (cursor != null) {
            // Move the cursor to the first row of the result set
            if (cursor.moveToFirst()) {
                // Get the index of the column for the file path in the result set
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                // Get the value of the file path from the cursor at the specified column index
                result = cursor.getString(column_index);
            }
            // Close the cursor to release resources
            cursor.close();
        }

        // Return the file path as a string, or null if it wasn't found
        return result;
    }


    public void pickCamera() {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri =this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        startCamera.launch(cameraIntent);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case 1://צילום תמונה
//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    saveBitmapToSharedPreferences(this, imageBitmap, "profile_image");
//                    ivProfile.setImageBitmap(imageBitmap);
//                    break;
//                case 2://בחירת תמונה מגלריה
//                    Uri selectedImage = data.getData();
//                    ivProfile.setImageURI(selectedImage);
//                    saveUriToSharedPreferences(this, selectedImage, "profile_image_uri");
//                    break;
//            }
//        }
//    }





    public  void saveUriToSharedPreferences(Context context, Uri uri, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, uri.toString());
        editor.apply();
    }

    public void setupImageCaptureAndSelection(){
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK
                            && result.getData() != null) {
                        cam_uri = result.getData().getData();
                        String realPath = getRealPathFromURI(this, cam_uri);
                        if (realPath != null) {
                            // Now you can use the real path for your image processing
                            ivProfile.setImageURI(Uri.parse(realPath));
                            saveUriToSharedPreferences(this, Uri.parse(realPath), "profile_image");
                        } else {
                            // Handle the case where the real path could not be found
                            ivProfile.setImageURI(cam_uri);
                            saveUriToSharedPreferences(this, cam_uri, "profile_image");
                        }
                    }
                }
        );

        startCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        ivProfile.setImageURI(cam_uri);

                        saveUriToSharedPreferences(profileCreationActivity.this, cam_uri, "profile_image");

                    }
                }
        );
    }




    public class SetDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;

            String str = "You selected :" + dayOfMonth + "/" + monthOfYear + "/" + year;
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
