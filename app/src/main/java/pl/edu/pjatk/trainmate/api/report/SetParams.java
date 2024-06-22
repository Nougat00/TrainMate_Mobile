package pl.edu.pjatk.trainmate.api.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the parameters of a set in an exercise report.
 * Contains information about the repetitions, weight, RIR (Reps in Reserve), and the set number.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetParams {

    Integer reportedRepetitions;
    Integer reportedWeight;
    Integer reportedRir;
    Integer set;
}
