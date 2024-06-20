package dataBaseRoom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseInfoDao {
    @Insert
    void insert (ExerciseInfo exerciseInfo);

    @Insert
    void insertAll(ExerciseInfo... exerciseInfos);

    @Query("SELECT * FROM ExerciseInfo")
    List<ExerciseInfo> getAllExercises();

    @Query("DELETE FROM ExerciseInfo")
    void deleteAllExercises();
}
