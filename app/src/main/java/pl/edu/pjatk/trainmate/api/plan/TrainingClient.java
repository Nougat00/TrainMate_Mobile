package pl.edu.pjatk.trainmate.api.plan;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TrainingClient {

    @GET("training/current")
    Call<List<TrainingUnitProjection>> getCurrentTraining(
        @Header("Authorization") String token
    );
}
