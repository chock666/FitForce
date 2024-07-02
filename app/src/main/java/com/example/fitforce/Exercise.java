package com.example.fitforce;

import android.webkit.WebView;
import android.widget.ImageView;

public class Exercise {
    private long exerciseID;
    private String exerciseName;
    private String type;
    private String description;
    private String muscleGroup;
    private String link;
    private String shortExplanation;

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
