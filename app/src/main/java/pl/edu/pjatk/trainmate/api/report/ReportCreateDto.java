package pl.edu.pjatk.trainmate.api.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateDto {

    Long exerciseItemId;
    List<SetParams> sets;
    String remarks;
}
