package pl.edu.pjatk.trainmate.api.plan;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum representing different muscles and their corresponding muscle groups.
 */
public enum Muscle {

    BICEPS(MuscleGroup.ARMS),
    TRICEPS(MuscleGroup.ARMS),
    FOREARM(MuscleGroup.ARMS),

    FRONTAL_DELTOID(MuscleGroup.SHOULDERS),
    MIDDLE_DELTOID(MuscleGroup.SHOULDERS),
    REAR_DELTOID(MuscleGroup.SHOULDERS),

    UPPER_CHEST(MuscleGroup.CHEST),
    MIDDLE_CHEST(MuscleGroup.CHEST),
    LOWER_CHEST(MuscleGroup.CHEST),

    LATS(MuscleGroup.BACK),
    RHOMBOID(MuscleGroup.BACK),
    DELTOID(MuscleGroup.BACK),
    TRAPEZIUS(MuscleGroup.BACK),
    LOWER_BACK(MuscleGroup.BACK),

    HAMSTRINGS(MuscleGroup.LEGS),
    GLUTES(MuscleGroup.LEGS),
    CALVES(MuscleGroup.LEGS),
    QUADS(MuscleGroup.LEGS),

    OBLIQUE(MuscleGroup.STOMACH),
    ABS(MuscleGroup.STOMACH);

    private final MuscleGroup muscleGroup;

    /**
     * Constructor for the Muscle enum.
     *
     * @param muscleGroup The muscle group to which this muscle belongs.
     */
    Muscle(MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    /**
     * Checks if the muscle belongs to the given muscle group.
     *
     * @param group The muscle group to check against.
     * @return true if the muscle belongs to the given group, false otherwise.
     */
    public boolean isInGroup(MuscleGroup group) {
        return this.muscleGroup.equals(group);
    }

    /**
     * Retrieves all muscles belonging to a specific muscle group.
     *
     * @param group The muscle group to filter muscles by.
     * @return A list of muscles that belong to the specified muscle group.
     */
    public static List<Muscle> getAllMusclesByGroup(MuscleGroup group) {
        return Arrays.stream(Muscle.values())
            .filter(it -> it.isInGroup(group))
            .collect(Collectors.toList());
    }

    /**
     * Enum representing different muscle groups.
     */
    public enum MuscleGroup {
        ARMS,
        SHOULDERS,
        CHEST,
        BACK,
        LEGS,
        STOMACH
    }
}
