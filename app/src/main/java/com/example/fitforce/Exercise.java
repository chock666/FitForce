package com.example.fitforce;

import android.webkit.WebView;
import android.widget.ImageView;

public class Exercise {
    private long exerciseID;
    private String exerciseName;
    private String description;
    private String muscleGroup;
    private String link;
    private String shortExplanation;
    WebView animation;

  public Exercise(long exerciseID, String exerciseName, String description, String muscleGroup, String link, String shortExplanation){
      //this.animation=animation;
      this.exerciseID=exerciseID;
      this.exerciseName=exerciseName;
      this.description=description;
      this.muscleGroup=muscleGroup;
      this.link=link;
      this.shortExplanation=shortExplanation;

  }
    public Exercise( String exerciseName,  String description, String muscleGroup, String link, String shortExplanation){
       // this.animation=animation;
        this.exerciseID=exerciseID;
        this.exerciseName=exerciseName;
        this.description=description;
        this.muscleGroup=muscleGroup;
        this.link=link;
        this.shortExplanation=shortExplanation;

    }
    public long getExerciseID() {
        return exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

//    public WebView getAnimation() {
//        return animation;
//    }

    public String getDescription() {
        return description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getLink() {
        return link;
    }

    public String getShortExplanation() {
        return shortExplanation;
    }

    // Setters
    public void setExerciseID(long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

//    public void setAnimation(WebView animation) {
//        this.animation = animation;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setShortExplanation(String shortExplanation) {
        this.shortExplanation = shortExplanation;
    }


}
