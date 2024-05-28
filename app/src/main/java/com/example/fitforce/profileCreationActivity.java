package com.example.fitforce;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class profileCreationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivProfile;
    EditText etFirstName, etLastName;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    Button btSubmitProfileCreationPage, btBirthDateChoosing;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btSubmitProfileCreationPage = findViewById(R.id.btSubmitProfileCreationPage);
        btBirthDateChoosing = findViewById(R.id.btBirthDateChoosing);
        ivProfile = findViewById(R.id.ivProfile);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    saveBitmapToSharedPreferences(this, imageBitmap, "profile_image");
                    ivProfile.setImageBitmap(imageBitmap);
                    break;
                case REQUEST_PICK_PHOTO:
                    Uri selectedImage = data.getData();
                    ivProfile.setImageURI(selectedImage);
                    saveUriToSharedPreferences(this, selectedImage, "profile_image_uri");
                    break;
            }
        }
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

    // Method to retrieve Bitmap from SharedPreferences
    public  Bitmap getBitmapFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String encodedBitmap = sharedPreferences.getString(key, null);
        return encodedBitmap != null ? base64ToBitmap(encodedBitmap) : null;
    }

    // Method to convert Base64 String to Bitmap
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

    // Method to retrieve URI from SharedPreferences
    public  Uri getUriFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String uriString = sharedPreferences.getString(key, null);
        return uriString != null ? Uri.parse(uriString) : null;
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
