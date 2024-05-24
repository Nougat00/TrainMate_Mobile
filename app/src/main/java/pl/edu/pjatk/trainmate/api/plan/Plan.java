package pl.edu.pjatk.trainmate.api.plan;

import java.util.ArrayList;
import java.util.List;

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
