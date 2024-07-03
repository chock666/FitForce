package com.example.fitforce;

import android.webkit.WebView;
import android.widget.ImageView;

public class Exercise {
    private long exerciseID; // מזהה התרגיל
    private String exerciseName;// שם התרגיל
    private String type;// סוג התרגיל - לאיזה תחום הוא שייך
    private String description; // תיאור התרגיל
    private String muscleGroup;// קבוצת השרירים שעובדת בתרגיל
    private String link;// לינק להסבר מפורט יותר על התרגיל
    private String shortExplanation;// הסבר קצר על איך לבצע את התרגיל

    public Exercise(long exerciseID, String exerciseName, String type, String description, String muscleGroup, String link, String shortExplanation) {
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.type = type;
        this.description = description;
        this.muscleGroup = muscleGroup;
        this.link = link;
        this.shortExplanation = shortExplanation;
    }

    // Getters and setters
    public long getExerciseID() { return exerciseID; }
    public void setExerciseID(long exerciseID) { this.exerciseID = exerciseID; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getShortExplanation() { return shortExplanation; }
    public void setShortExplanation(String shortExplanation) { this.shortExplanation = shortExplanation; }
}
