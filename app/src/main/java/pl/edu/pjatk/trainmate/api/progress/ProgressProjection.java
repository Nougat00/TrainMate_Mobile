package pl.edu.pjatk.trainmate.api.progress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressProjection {

    boolean initial;
    boolean reviewed;
    Double weight;
    Integer bodyFat;
    Double leftBiceps;
    Double rightBiceps;
    Double leftForearm;
    Double rightForearm;
    Double leftThigh;
    Double rightThigh;
    Double leftCalf;
    Double rightCalf;
    Double shoulders;
    Double chest;
    Double waist;
    Double abdomen;
    Double hips;
    String createdDate;
}
