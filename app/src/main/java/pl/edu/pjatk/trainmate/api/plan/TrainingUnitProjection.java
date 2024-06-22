package pl.edu.pjatk.trainmate.api.plan;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a training unit projection.
 * A TrainingUnitProjection contains an id, the day of the week, the week number,
 * and a list of exercises for the training unit.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitProjection {

    Long id;
    DayOfWeek dayOfWeek;
    Long weekNumber;
    List<ExerciseItemProjection> exercises = new ArrayList<>();

    /**
     * Returns a string representation of the TrainingUnitProjection.
     *
     * @return A string that represents the TrainingUnitProjection.
     */
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
