package pl.edu.pjatk.trainmate.api.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for creating a report.
 * Contains information about the exercise item, the sets performed, and any additional remarks.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateDto {

    Long exerciseItemId;
    List<SetParams> sets;
    String remarks;
}
