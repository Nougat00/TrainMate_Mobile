package pl.edu.pjatk.trainmate.api.plan;

import com.google.gson.annotations.SerializedName;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitProjection {

    @SerializedName("id")
    Long id;
    @SerializedName("dayOfWeek")
    DayOfWeek dayOfWeek;
    @SerializedName("weekNumber")
    Long weekNumber;
    @SerializedName("exercises")
    List<ExerciseItemProjection> exercises = new ArrayList<>();

    @Override
    public String toString() {
        return "TrainingUnitProjection{" +
            "id=" + id +
            ", dayOfWeek=" + dayOfWeek +
            ", weekNumber=" + weekNumber +
            ", exercises=" + exercises +
            '}';
    }
}
