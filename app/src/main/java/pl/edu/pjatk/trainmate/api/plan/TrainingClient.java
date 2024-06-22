package pl.edu.pjatk.trainmate.api.plan;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Interface for defining API endpoints related to training operations.
 */
public interface TrainingClient {

    /**
     * Gets the current training units for the authenticated user.
     *
     * @param token The authorization token for authenticating the API request.
     * @return A Call object for the request, containing a list of TrainingUnitProjection objects.
     */
    @GET("training/current")
    Call<List<TrainingUnitProjection>> getCurrentTraining(
        @Header("Authorization") String token
    );
}
