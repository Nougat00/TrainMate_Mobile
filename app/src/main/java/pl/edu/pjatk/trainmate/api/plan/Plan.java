package pl.edu.pjatk.trainmate.api.plan;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    Long id;
    String name;
    String category;
    List<TrainingUnitProjection> trainingUnits = new ArrayList<>();

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
