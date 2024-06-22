package pl.edu.pjatk.trainmate.api.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Projection class representing an exercise item.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItemProjection {

    private Long id;
    private Integer repetitions;
    private String tempo;
    private Integer weight;
    private Integer rir;
    private Integer sets;
    private Muscle muscleInvolved;
    private String name;
    private String description;
    private String url;
    private boolean reported;

    /**
     * Returns a string representation of the ExerciseItemProjection.
     *
     * @return A string containing the exercise item details.
     */
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
