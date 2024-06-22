package pl.edu.pjatk.trainmate.api.progress;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * A client interface for accessing progress reports.
 * Provides methods to retrieve all periodical progress reports from the server.
 */
public interface ProgressClient {

    /**
     * Retrieves all periodical progress reports.
     *
     * @param token the authorization token required to access the reports.
     * @return a {@link Call} object which can be used to send the HTTP request and receive the response.
     */
    @GET("periodical/all-reports")
    Call<List<ProgressProjection>> getAllProgressReports(
        @Header("Authorization") String token
    );
}
