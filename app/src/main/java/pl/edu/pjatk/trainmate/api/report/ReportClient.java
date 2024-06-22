package pl.edu.pjatk.trainmate.api.report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * A client interface for sending exercise reports.
 * Provides a method to send a report for a specific exercise.
 */
public interface ReportClient {

    /**
     * Sends a report for a specific exercise.
     *
     * @param exerciseId the ID of the exercise to which the report belongs.
     * @param token the authorization token required to send the report.
     * @param reportCreateDto the data transfer object containing the report details.
     * @return a {@link Call} object which can be used to send the HTTP request and receive the response.
     */
    @POST("exercise/{exerciseId}/report")
    Call<Void> sendReport(
        @Path("exerciseId") long exerciseId,
        @Header("Authorization") String token,
        @Body ReportCreateDto reportCreateDto
    );
}
