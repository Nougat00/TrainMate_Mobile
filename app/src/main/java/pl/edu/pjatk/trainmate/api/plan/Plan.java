package pl.edu.pjatk.trainmate.api.plan;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a training plan.
 * A Plan contains an id, name, category, and a list of training units.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    Long id;
    String name;
    String category;
    List<TrainingUnitProjection> trainingUnits = new ArrayList<>();

    /**
     * Returns a string representation of the Plan.
     *
     * @return A string that represents the Plan.
     */
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", trainingUnits=" + trainingUnits +
            '}';
    }
}
