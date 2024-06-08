package pl.edu.pjatk.trainmate.api.report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportClient {

    @POST("exercise/{exerciseId}/report")
    Call<Void> sendReport(
        @Path("exerciseId") long exerciseId,
        @Header("Authorization") String token,
        @Body ReportCreateDto reportCreateDto
    );
}
