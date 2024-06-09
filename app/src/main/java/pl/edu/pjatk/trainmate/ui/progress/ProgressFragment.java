package pl.edu.pjatk.trainmate.ui.progress;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.pjatk.trainmate.api.RetrofitApiClient.getRetrofitInstance;
import static pl.edu.pjatk.trainmate.utils.Const.API_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.API_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PARTS;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_ABDOMEN;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_BODY_FAT;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_CHEST;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_HIPS;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_LEFT_BICEPS;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_LEFT_CALF;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_LEFT_FOREARM;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_LEFT_THIGH;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_RIGHT_BICEPS;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_RIGHT_CALF;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_RIGHT_FOREARM;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_RIGHT_THIGH;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_SHOULDERS;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_WAIST;
import static pl.edu.pjatk.trainmate.utils.Const.BODY_PART_WEIGHT;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_ACCESS_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_TYPE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.List;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.api.progress.ProgressClient;
import pl.edu.pjatk.trainmate.api.progress.ProgressProjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressFragment extends Fragment {

    private LinearLayout chartContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        chartContainer = view.findViewById(R.id.chartContainer);

        ProgressClient progressClient = getRetrofitInstance().create(ProgressClient.class);
        var token = view.getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_ACCESS_TOKEN, DEFAULT_STRING_VALUE);

        Call<List<ProgressProjection>> call = progressClient.getAllProgressReports(TOKEN_TYPE + token);
        call.enqueue(new Callback<List<ProgressProjection>>() {
            @Override
            public void onResponse(Call<List<ProgressProjection>> call, Response<List<ProgressProjection>> response) {
                if (response.isSuccessful()) {
                    List<ProgressProjection> progressList = response.body();
                    if (progressList != null) {
                        displayProgressCharts(progressList);
                    }
                } else {
                    Log.e(API_TAG, API_FAIL + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ProgressProjection>> call, Throwable t) {
                Log.e(API_TAG, API_FAIL + t.getMessage());
            }
        });

        return view;
    }

    private void displayProgressCharts(List<ProgressProjection> progressList) {

        for (String bodyPart : BODY_PARTS) {
            LineChart chart = new LineChart(getContext());
            List<Entry> entries = new ArrayList<>();
            List<String> dates = new ArrayList<>();

            for (int i = 0; i < progressList.size(); i++) {
                ProgressProjection progress = progressList.get(i);
                dates.add(progress.getCreatedDate());

                double value = switch (bodyPart) {
                    case BODY_PART_WEIGHT -> progress.getWeight() != null ? progress.getWeight() : 0;
                    case BODY_PART_BODY_FAT -> progress.getBodyFat() != null ? progress.getBodyFat() : 0;
                    case BODY_PART_LEFT_BICEPS -> progress.getLeftBiceps() != null ? progress.getLeftBiceps() : 0;
                    case BODY_PART_RIGHT_BICEPS -> progress.getRightBiceps() != null ? progress.getRightBiceps() : 0;
                    case BODY_PART_LEFT_FOREARM -> progress.getLeftForearm() != null ? progress.getLeftForearm() : 0;
                    case BODY_PART_RIGHT_FOREARM -> progress.getRightForearm() != null ? progress.getRightForearm() : 0;
                    case BODY_PART_LEFT_THIGH -> progress.getLeftThigh() != null ? progress.getLeftThigh() : 0;
                    case BODY_PART_RIGHT_THIGH -> progress.getRightThigh() != null ? progress.getRightThigh() : 0;
                    case BODY_PART_LEFT_CALF -> progress.getLeftCalf() != null ? progress.getLeftCalf() : 0;
                    case BODY_PART_RIGHT_CALF -> progress.getRightCalf() != null ? progress.getRightCalf() : 0;
                    case BODY_PART_SHOULDERS -> progress.getShoulders() != null ? progress.getShoulders() : 0;
                    case BODY_PART_CHEST -> progress.getChest() != null ? progress.getChest() : 0;
                    case BODY_PART_WAIST -> progress.getWaist() != null ? progress.getWaist() : 0;
                    case BODY_PART_ABDOMEN -> progress.getAbdomen() != null ? progress.getAbdomen() : 0;
                    case BODY_PART_HIPS -> progress.getHips() != null ? progress.getHips() : 0;
                    default -> 0;
                };

                entries.add(new Entry(i, (float) value));
            }

            LineDataSet dataSet = new LineDataSet(entries, bodyPart);
            dataSet.setLineWidth(3f);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);

            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
            xAxis.setGranularity(1f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            chart.invalidate();

            chartContainer.addView(chart, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                600
            ));
        }
    }
}