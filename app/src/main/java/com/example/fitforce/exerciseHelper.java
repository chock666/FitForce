package com.example.fitforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class exerciseHelper extends SQLiteOpenHelper {
    // שם מסד הנתונים
    public static final String DATABASENAME = "exercise.db";
    // שם הטבלה
    public static final String TABLE_EXERCISE = "tblexercise";
    // גרסת מסד הנתונים
    public static final int DATABASEVERSION = 1;

    // עמודות הטבלה
    public static final String COLUMN_ID = "exerciseID";
    public static final String COLUMN_NAME = "exerciseName";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MUSCLE_GROUP = "muscleGroup";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_SHORT_EXPLANATION = "shortExplanation";

    // מערך שמכיל את כל העמודות
    String[] allColumns = {
            exerciseHelper.COLUMN_ID,
            exerciseHelper.COLUMN_NAME,
            exerciseHelper.COLUMN_TYPE,
            exerciseHelper.COLUMN_DESCRIPTION,
            exerciseHelper.COLUMN_MUSCLE_GROUP,
            exerciseHelper.COLUMN_LINK,
            exerciseHelper.COLUMN_SHORT_EXPLANATION
    };

    // משתנה למסד הנתונים
    SQLiteDatabase database;

    // מחרוזת SQL ליצירת הטבלה
    private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_EXERCISE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_TYPE + " VARCHAR," +
            COLUMN_DESCRIPTION + " VARCHAR," +
            COLUMN_MUSCLE_GROUP + " VARCHAR," +
            COLUMN_LINK + " VARCHAR," +
            COLUMN_SHORT_EXPLANATION + " VARCHAR" + ");";

    public exerciseHelper(Context context) {
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
        values.put(exerciseHelper.COLUMN_NAME, e.getExerciseName());
        values.put(exerciseHelper.COLUMN_TYPE, e.getType());
        values.put(exerciseHelper.COLUMN_DESCRIPTION, e.getDescription());
        values.put(exerciseHelper.COLUMN_MUSCLE_GROUP, e.getMuscleGroup());
        values.put(exerciseHelper.COLUMN_LINK, e.getLink());
        values.put(exerciseHelper.COLUMN_SHORT_EXPLANATION, e.getShortExplanation());
        long insertId = database.insert(exerciseHelper.TABLE_EXERCISE, null, values);
        e.setExerciseID(insertId);
        return e;
    }

    public long deleteByRow(long rowId) {
        return database.delete(exerciseHelper.TABLE_EXERCISE, exerciseHelper.COLUMN_ID + "=" + rowId, null);
    }

    public long updateByRow(Exercise e) {
        ContentValues values = new ContentValues();
        values.put(exerciseHelper.COLUMN_ID, e.getExerciseID());
        values.put(exerciseHelper.COLUMN_NAME, e.getExerciseName());
        values.put(exerciseHelper.COLUMN_TYPE, e.getType());
        values.put(exerciseHelper.COLUMN_DESCRIPTION, e.getDescription());
        values.put(exerciseHelper.COLUMN_MUSCLE_GROUP, e.getMuscleGroup());
        values.put(exerciseHelper.COLUMN_LINK, e.getLink());
        values.put(exerciseHelper.COLUMN_SHORT_EXPLANATION, e.getShortExplanation());
        return database.update(exerciseHelper.TABLE_EXERCISE, values, exerciseHelper.COLUMN_ID + "=" + e.getExerciseID(), null);
    }

    public Exercise getExerciseById(long rowId) {
        Cursor cursor = database.query(exerciseHelper.TABLE_EXERCISE, allColumns, exerciseHelper.COLUMN_ID + "=" + rowId, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_NAME));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_TYPE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_DESCRIPTION));
            String muscleGroup = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_MUSCLE_GROUP));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_LINK));
            String shortExplanation = cursor.getString(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_SHORT_EXPLANATION));
            Exercise e = new Exercise(id, name, type, description, muscleGroup, link, shortExplanation);
            cursor.close();
            return e;
        }
        cursor.close();
        return null;
    }

    public ArrayList<Exercise> getAllExercises() {
        String orderBy = exerciseHelper.COLUMN_ID + " ASC";
        Cursor cursor = database.query(exerciseHelper.TABLE_EXERCISE, allColumns, null, null, null, null, orderBy);
        ArrayList<Exercise> exercises = convertCursorToList(cursor);
        return exercises;
    }

    public int sizeByType(String type){
        int count=0;
        for (int i = 0; i<getAllExercises().size();i++){
            if (getAllExercises().get(i).getType().equals(type)){
                count++;
            }
        }
        return count;
    }

    private ArrayList<Exercise> convertCursorToList(Cursor cursor) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(exerciseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String muscleGroup = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MUSCLE_GROUP));
                String link = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK));
                String shortExplanation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHORT_EXPLANATION));
                Exercise e = new Exercise(id, name, type, description, muscleGroup, link, shortExplanation);
                exercises.add(e);
            }
        }
        cursor.close();
        return exercises;
    }



    private void prepopulateTable(SQLiteDatabase db) {
        String insertData1 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Push Up', 'A basic chest exercise', 'Chest', 'https://www.youtube.com/watch?v=IODxDxX7oi4', 'Place hands in shoulder-width, lower chest, then push up.- until you cannot do more reps', 'strength');";

        String insertData2 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +
                ") VALUES ('Push Up - wide', 'A  chest exercise that works mainly on the wider parts of the chest', 'chest, shoulders, and upper arms', 'https://www.youtube.com/watch?v=EsIdzx1J0iA', 'Place hands wider than shoulder-width, lower chest, then push up.- until you cannot do more reps', 'strength');";
        String insertData3 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Push Up - narrow', 'A  chest exercise that works mainly on the narrower parts of the chest', 'triceps and chest', 'https://www.youtube.com/watch?v=IcWPQo1i0k4', 'Place hands wider than shoulder-width, lower chest, then push up.- until you cannot do more reps', 'strength');";
        String insertData4 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Squat', 'A basic lower body exercise', 'quadriceps, hamstrings and butt', 'https://www.youtube.com/watch?v=IB_icWRzi4E', 'Stand with feet shoulder-width apart, lower hips back and down, then rise back up.- until you cannot do more reps', 'strength');";
        String insertData5 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('pull up - wide', 'A  back exercise that works mainly on the wider parts of the back', 'back and arms', 'https://www.youtube.com/watch?v=LnyeO61PVBM', 'Grip the bar wide, pull up until your chin is over the bar, then lower back down.- until you cannot do more reps', 'strength');";
        String insertData6 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('pull up', 'A  basic back exercise', 'back and arms', 'https://www.youtube.com/watch?v=eGo4IYlbE5g', 'Grip the bar in shoulders width , pull up until your chin is over the bar, then lower back down.- until you cannot do more reps', 'strength');";
        String insertData7 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('pull up - underhand grip', 'A back and biceps exercise', 'back and biceps', 'https://www.youtube.com/watch?v=9JC1EwqezGY', 'Grab the bar with palms facing you, pull your chin over the bar, then lower yourself down.- until you cannot do more reps', 'strength');";
        String insertData8 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Lunges', 'A basic lower body exercise', 'Legs and butt', 'https://www.youtube.com/watch?v=ugW5I-a5A-Q', 'Step forward with one leg, bending both knees until the front thigh is parallel to the floor, then return to starting position and switch legs.- until you cannot do more reps', 'strength');";
        String insertData9 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Dips', 'A basic triceps exercise', 'triceps', 'https://www.youtube.com/watch?v=l41SoWZiowI', 'Grip bars, lower the body and then extend arms back up.- until you cannot do more reps', 'strength');";
        String insertData10 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Planks', 'A basic abs exercise', 'abs', 'https://www.youtube.com/watch?v=6LqqeBtFn9M', 'Hold a plank by supporting your body on your forearms and toes, keeping your back straight and core engaged.- until you fall', 'strength');";
        String insertData11 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('Burpees', 'a full-body exercise', 'legs, hips, buttocks, abdomen, arms, chest, and shoulders', 'https://www.youtube.com/watch?v=xQdyIrSSFnE', 'Perform a burpee by squatting, kicking back into a plank, doing a push-up, jumping feet to hands, and explosively jumping up. - until you cannot do more reps', 'strength');";
        String insertData12 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('sprints', 'enhancer speed and stamina', 'legs', 'https://www.youtube.com/watch?v=2YogM9wXAJg', 'run as fast as you can for 50 meters and do it again - 15 times', 'fitness');";
        String insertData13 = "INSERT INTO " + TABLE_EXERCISE + " (" +
                COLUMN_NAME + ", " +
                //       COLUMN_ANIMATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_MUSCLE_GROUP + ", " +
                COLUMN_LINK + ", " +
                COLUMN_SHORT_EXPLANATION +", " +
                COLUMN_TYPE +

                ") VALUES ('army crawls', 'enhance core strength, stability, cardiovascular fitness, and coordination.', 'core, shoulders, arms, back, and legs.', 'https://www.youtube.com/watch?v=RG3ik4VacAA', 'start in a prone position, lift your body with toes and forearms, and crawl forward alternating arm and leg movements - do it 10 minutes.', 'fitness');";

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
        db.execSQL(insertData12);
        db.execSQL(insertData13);

    }
}

