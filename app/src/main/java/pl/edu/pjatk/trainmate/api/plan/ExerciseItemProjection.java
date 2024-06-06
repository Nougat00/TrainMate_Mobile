package pl.edu.pjatk.trainmate.api.plan;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItemProjection {

    @SerializedName("access_token")
    Long id;
    @SerializedName("repetitions")
    Integer repetitions;
    @SerializedName("tempo")
    String tempo;
    @SerializedName("weight")
    Integer weight;
    @SerializedName("rir")
    Integer rir;
    @SerializedName("sets")
    Integer sets;
    @SerializedName("muscleInvolved")
    Muscle muscleInvolved;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("url")
    String url;

    @Override
    public String toString() {
        return "ExerciseItemProjection{" +
            "id=" + id +
            ", repetitions=" + repetitions +
            ", tempo='" + tempo + '\'' +
            ", weight=" + weight +
            ", rir=" + rir +
            ", sets=" + sets +
            ", muscleInvolved=" + muscleInvolved +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}
