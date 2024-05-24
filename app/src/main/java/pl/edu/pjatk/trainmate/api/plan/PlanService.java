package pl.edu.pjatk.trainmate.api.plan;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_TEST;

import android.content.Context;
import pl.edu.pjatk.trainmate.api.RetrofitApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanService {

    public Plan getPlan(Context context) {
        var dupa = RetrofitApiClient.getRetrofitInstance().create(PlanClient.class);
        var sharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        var token = TOKEN_TEST;
        final Plan[] odp = new Plan[1];
        try {
            dupa.getPlan(1, "Bearer " + token).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dupa.getPlan(1, "Bearer " + token).enqueue(new Callback<Plan>() {
            @Override
            public void onResponse(Call<Plan> call, Response<Plan> response) {
                if (response.isSuccessful()) {
                    odp[0] = response.body();
                }
            }

            @Override
            public void onFailure(Call<Plan> call, Throwable throwable) {
                System.out.println("TEST");
            }
        });
        return odp[0];
    }
}
