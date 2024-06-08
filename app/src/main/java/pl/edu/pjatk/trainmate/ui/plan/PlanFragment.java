package pl.edu.pjatk.trainmate.ui.plan;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.pjatk.trainmate.utils.Const.API_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.API_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_ACCESS_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_TYPE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.api.RetrofitApiClient;
import pl.edu.pjatk.trainmate.api.plan.TrainingClient;
import pl.edu.pjatk.trainmate.api.plan.TrainingUnitProjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrainingUnitAdapter trainingUnitAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        var trainingClient = RetrofitApiClient.getRetrofitInstance().create(TrainingClient.class);
        var token = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_ACCESS_TOKEN, DEFAULT_STRING_VALUE);
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        trainingClient.getCurrentTraining(TOKEN_TYPE + token).enqueue(new Callback<List<TrainingUnitProjection>>() {
            @Override
            public void onResponse(Call<List<TrainingUnitProjection>> call, Response<List<TrainingUnitProjection>> response) {
                if (response.isSuccessful()) {
                    setAll(response, view);
                    System.out.println(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<TrainingUnitProjection>> call, Throwable throwable) {
                Log.w(API_TAG, API_FAIL);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setAll(Response<List<TrainingUnitProjection>> response, View view) {
        trainingUnitAdapter = new TrainingUnitAdapter(response.body());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(trainingUnitAdapter);
    }
}