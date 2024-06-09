package pl.edu.pjatk.trainmate.api.progress;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProgressClient {

    @GET("periodical/all-reports")
    Call<List<ProgressProjection>> getAllProgressReports(
        @Header("Authorization") String token
    );
}
