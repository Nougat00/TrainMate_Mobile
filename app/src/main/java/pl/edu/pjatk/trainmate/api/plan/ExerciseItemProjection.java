package pl.edu.pjatk.trainmate.api.plan;

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
