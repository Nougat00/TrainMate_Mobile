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
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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

/**
 * Fragment to display progress charts for various body measurements.
 */
public class ProgressFragment extends Fragment {

    private LinearLayout chartContainer;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        chartContainer = view.findViewById(R.id.chartContainer);

        // Retrieve access token from SharedPreferences
        ProgressClient progressClient = getRetrofitInstance().create(ProgressClient.class);
        String token = view.getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_ACCESS_TOKEN, DEFAULT_STRING_VALUE);

        // Make an asynchronous request to get all progress reports
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

    /**
     * Displays progress charts for each body part.
     *
     * @param progressList List of progress projections containing measurement data.
     */
    protected void displayProgressCharts(List<ProgressProjection> progressList) {
        for (String bodyPart : BODY_PARTS) {
            addTitle(bodyPart);
            LineChart chart = createChart(bodyPart, progressList);
            chartContainer.addView(chart, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                800
            ));
            addSeparator();
        }
    }

    /**
     * Adds a title for each body part above the corresponding chart.
     *
     * @param bodyPart The name of the body part.
     */
    protected void addTitle(String bodyPart) {
        TextView title = new TextView(getContext());
        title.setText(bodyPart);
        title.setTextSize(18f);
        title.setPadding(0, 20, 0, 10);
        chartContainer.addView(title);
    }

    /**
     * Creates a line chart for a specific body part using the progress data.
     *
     * @param bodyPart The name of the body part.
     * @param progressList List of progress projections containing measurement data.
     * @return A configured LineChart object.
     */
    LineChart createChart(String bodyPart, List<ProgressProjection> progressList) {
        LineChart chart = new LineChart(getContext());
        List<Entry> entries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        // Populate entries and dates from progress data
        for (int i = 0; i < progressList.size(); i++) {
            ProgressProjection progress = progressList.get(i);
            dates.add(progress.getCreatedDate());
            double value = getValueForBodyPart(bodyPart, progress);
            entries.add(new Entry(i, (float) value));
        }

        // Configure the data set and chart appearance
        LineDataSet dataSet = new LineDataSet(entries, bodyPart);
        dataSet.setLineWidth(5f);
        dataSet.setValueTextSize(14f);
        dataSet.setColor(getResources().getColor(R.color.secondary));
        dataSet.setCircleColor(getResources().getColor(R.color.secondary));
        LineData lineData = new LineData(dataSet);

        chart.setDescription(new Description());
        chart.setData(lineData);
        configureChartAxes(chart, dates);
        chart.invalidate();

        return chart;
    }

    /**
     * Retrieves the value for a specific body part from a progress projection.
     *
     * @param bodyPart The name of the body part.
     * @param progress The progress projection containing the measurement data.
     * @return The value of the measurement for the specified body part.
     */
    protected double getValueForBodyPart(String bodyPart, ProgressProjection progress) {
        return switch (bodyPart) {
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
    }

    /**
     * Configures the X and Y axes for the chart.
     *
     * @param chart The LineChart object to configure.
     * @param dates List of dates for the X-axis labels.
     */
    private void configureChartAxes(LineChart chart, List<String> dates) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates) {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index % 2 == 0 && index < dates.size()) {
                    return dates.get(index);
                } else {
                    return "";
                }
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setLabelCount((dates.size() + 1) / 2, true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextSize(14f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTextSize(14f);
    }

    /**
     * Adds a separator line between charts.
     */
    protected void addSeparator() {
        View separator = new View(getContext());
        LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            2
        );
        separatorParams.setMargins(0, 20, 0, 20);
        separator.setLayoutParams(separatorParams);
        separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        chartContainer.addView(separator);
    }
}
