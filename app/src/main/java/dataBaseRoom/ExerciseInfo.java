package dataBaseRoom;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class ExerciseInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private String muscleGroup;
    private String link;
    private String shortExplanation;


    public ExerciseInfo(String name, String description, String muscleGroup, String link, String shortExplanation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroup = muscleGroup;
        this.link = link;
        this.shortExplanation = shortExplanation;
    }
    public ExerciseInfo() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShortExplanation() {
        return shortExplanation;
    }

    public void setShortExplanation(String shortExplanation) {
        this.shortExplanation = shortExplanation;
    }
}
