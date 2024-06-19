package pl.edu.pjatk.trainmate.api.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItemProjection {

    Long id;
    Integer repetitions;
    String tempo;
    Integer weight;
    Integer rir;
    Integer sets;
    Muscle muscleInvolved;
    String name;
    String description;
    String url;
    boolean reported;

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
