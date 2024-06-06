package pl.edu.pjatk.trainmate.ui.plan;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_ACCESS_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TOKEN_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_TYPE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pl.edu.pjatk.trainmate.api.RetrofitApiClient;
import pl.edu.pjatk.trainmate.api.plan.Plan;
import pl.edu.pjatk.trainmate.api.plan.PlanClient;
import pl.edu.pjatk.trainmate.databinding.FragmentPlanBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        PlanViewModel planViewModel =
            new ViewModelProvider(this).get(PlanViewModel.class);

        binding = FragmentPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlan;
        Button button = binding.button;
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                var planClient = RetrofitApiClient.getRetrofitInstance().create(PlanClient.class);
                var token = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_ACCESS_TOKEN, DEFAULT_STRING_VALUE);
                planClient.getPlan(1, TOKEN_TYPE + token).enqueue(new Callback<Plan>() {
                    @Override
                    public void onResponse(Call<Plan> call, Response<Plan> response) {
                        if (response.isSuccessful()) {
                            textView.setText(response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Plan> call, Throwable throwable) {
                        Log.w(REFRESH_TAG, REFRESH_TOKEN_FAIL);
                    }
                });

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}