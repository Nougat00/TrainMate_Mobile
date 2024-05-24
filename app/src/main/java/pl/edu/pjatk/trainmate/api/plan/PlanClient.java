package pl.edu.pjatk.trainmate.api.plan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PlanClient {

    @GET("workout-plan/{id}")
    Call<Plan> getPlan(
        @Path("id") long id,
        @Header("Authorization") String token
    );
}