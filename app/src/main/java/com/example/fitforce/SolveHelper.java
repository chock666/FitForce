package com.example.fitforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.webkit.WebView;

import java.util.ArrayList;

public class SolveHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME = "exercise.db";
    public static final String TABLE_EXERCISE = "tblexercise";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "exerciseID";
    public static final String COLUMN_NAME = "exerciseName";
   // public static final String COLUMN_ANIMATION = "animation";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MUSCLE_GROUP = "muscleGroup";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_SHORT_EXPLANATION = "shortExplanation";

    String[] allColumns = {
            SolveHelper.COLUMN_ID,
            SolveHelper.COLUMN_NAME,
          //  SolveHelper.COLUMN_ANIMATION,
            SolveHelper.COLUMN_DESCRIPTION,
            SolveHelper.COLUMN_MUSCLE_GROUP,
            SolveHelper.COLUMN_LINK,
            SolveHelper.COLUMN_SHORT_EXPLANATION
    };

    SQLiteDatabase database;

    private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_EXERCISE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " VARCHAR," +
         //   COLUMN_ANIMATION + " VARCHAR," +
            COLUMN_DESCRIPTION + " VARCHAR," +
            COLUMN_MUSCLE_GROUP + " VARCHAR," +
            COLUMN_LINK + " VARCHAR," +
            COLUMN_SHORT_EXPLANATION + " VARCHAR" + ");";

    public SolveHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISE);
        prepopulateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
    }

    public Exercise createExercise(Exercise e) {
        ContentValues values = new ContentValues();
        values.put(SolveHelper.COLUMN_NAME, e.getExerciseName());
      //  values.put(SolveHelper.COLUMN_ANIMATION, e.getAnimation().toString());
        values.put(SolveHelper.COLUMN_DESCRIPTION, e.getDescription());
        values.put(SolveHelper.COLUMN_MUSCLE_GROUP, e.getMuscleGroup());
        values.put(SolveHelper.COLUMN_LINK, e.getLink());
        values.put(SolveHelper.COLUMN_SHORT_EXPLANATION, e.getShortExplanation());
        long insertId = database.insert(SolveHelper.TABLE_EXERCISE, null, values);
        e.setExerciseID(insertId);
        return e;
    }

    public long deleteByRow(long rowId) {
        return database.delete(SolveHelper.TABLE_EXERCISE, SolveHelper.COLUMN_ID + "=" + rowId, null);
    }

    public long updateByRow(Exercise e) {
        ContentValues values = new ContentValues();
        values.put(SolveHelper.COLUMN_ID, e.getExerciseID());
        values.put(SolveHelper.COLUMN_NAME, e.getExerciseName());
     //   values.put(SolveHelper.COLUMN_ANIMATION, e.getAnimation().toString());
        values.put(SolveHelper.COLUMN_DESCRIPTION, e.getDescription());
        values.put(SolveHelper.COLUMN_MUSCLE_GROUP, e.getMuscleGroup());
        values.put(SolveHelper.COLUMN_LINK, e.getLink());
        values.put(SolveHelper.COLUMN_SHORT_EXPLANATION, e.getShortExplanation());
        return database.update(SolveHelper.TABLE_EXERCISE, values, SolveHelper.COLUMN_ID + "=" + e.getExerciseID(), null);
    }

    public Exercise getExerciseById(long rowId) {
        Cursor cursor = database.query(SolveHelper.TABLE_EXERCISE, allColumns, SolveHelper.COLUMN_ID + "=" + rowId, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_NAME));
         //   WebView animation = new WebView(); // Placeholder: Replace with proper WebView handling
            String description = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_DESCRIPTION));
            String muscleGroup = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_MUSCLE_GROUP));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_LINK));
            String shortExplanation = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_SHORT_EXPLANATION));
            Exercise e = new Exercise(id, name,  description, muscleGroup, link, shortExplanation);
            return e;
        }
        return null;
    }

    public ArrayList<Exercise> getAllExercises() {
        String orderBy = SolveHelper.COLUMN_ID + " ASC";
        Cursor cursor = database.query(SolveHelper.TABLE_EXERCISE, allColumns, null, null, null, null, orderBy);
        ArrayList<Exercise> exercises = convertCursorToList(cursor);
        return exercises;
    }

    private ArrayList<Exercise> convertCursorToList(Cursor cursor) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_NAME));
//                WebView animation = new WebView(null); // Placeholder: Replace with proper WebView handling
                String description = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_DESCRIPTION));
                String muscleGroup = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_MUSCLE_GROUP));
                String link = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_LINK));
                String shortExplanation = cursor.getString(cursor.getColumnIndexOrThrow(SolveHelper.COLUMN_SHORT_EXPLANATION));
                Exercise e = new Exercise(id, name,  description, muscleGroup, link, shortExplanation);
                exercises.add(e);
            }
        }
        return exercises;
    }

    private void prepopulateTable(SQLiteDatabase db) {
        String insertData1 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
     //           COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Push Up', 'A basic chest exercise', 'Chest', 'http://example.com/pushup', 'Place hands in shoulder-width, lower chest, then push up.');";

        String insertData2 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
         //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Push Up - wide', 'A  chest exercise that works mainly on the wider parts of the chest', 'chest, shoulders, and upper arms', 'http://example.com/squat', 'Place hands wider than shoulder-width, lower chest, then push up.');";
        String insertData3 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Push Up - narrow', 'A  chest exercise that works mainly on the narrower parts of the chest', 'triceps and chest', 'http://example.com/squat', 'Place hands wider than shoulder-width, lower chest, then push up.');";
        String insertData4 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Squat', 'A basic lower body exercise', 'quadriceps, hamstrings and butt', 'http://example.com/squat', 'Stand with feet shoulder-width apart, lower hips back and down, then rise back up.');";
        String insertData5 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('pull up - wide', 'A  back exercise that works mainly on the wider parts of the back', 'back and arms', 'http://example.com/squat', 'Grip the bar wide, pull up until your chin is over the bar, then lower back down.');";
        String insertData6 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('pull up', 'A  basic back exercise', 'back and arms', 'http://example.com/squat', 'Grip the bar in shoulders width , pull up until your chin is over the bar, then lower back down.');";
        String insertData7 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('pull up - underhand grip', 'A back and biceps exercise', 'back and biceps', 'http://example.com/squat', 'Grab the bar with palms facing you, pull your chin over the bar, then lower yourself down.');";
        String insertData8 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Lunges', 'A basic lower body exercise', 'Legs and butt', 'http://example.com/squat', 'Step forward with one leg, bending both knees until the front thigh is parallel to the floor, then return to starting position and switch legs.');";
        String insertData9 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Dips', 'A basic triceps exercise', 'triceps', 'http://example.com/squat', 'Grip bars, lower the body and then extend arms back up.');";
        String insertData10 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Planks', 'A basic abs exercise', 'abs', 'http://example.com/squat', 'Hold a plank by supporting your body on your forearms and toes, keeping your back straight and core engaged.');";
        String insertData11 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +
                ") VALUES ('Burpees', 'a full-body exercise', 'legs, hips, buttocks, abdomen, arms, chest, and shoulders', 'http://example.com/squat', 'Perform a burpee by squatting, kicking back into a plank, doing a push-up, jumping feet to hands, and explosively jumping up.');";

        db.execSQL(insertData1);
        db.execSQL(insertData2);
        db.execSQL(insertData3);
        db.execSQL(insertData4);
        db.execSQL(insertData5);
        db.execSQL(insertData6);
        db.execSQL(insertData7);
        db.execSQL(insertData8);
        db.execSQL(insertData9);
        db.execSQL(insertData10);
        db.execSQL(insertData11);

    }
}

