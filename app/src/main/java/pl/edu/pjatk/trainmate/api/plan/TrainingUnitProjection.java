package pl.edu.pjatk.trainmate.api.plan;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class TrainingUnitProjection {

    Long id;
    DayOfWeek dayOfWeek;
    Long weekNumber;
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
