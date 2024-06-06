package pl.edu.pjatk.trainmate.api.plan;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @SerializedName("id")
    Long id;
    @SerializedName("name")
    String name;
    @SerializedName("category")
    String category;
    @SerializedName("trainingUnits")
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
