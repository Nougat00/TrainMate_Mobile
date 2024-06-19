package pl.edu.pjatk.trainmate.ui.plan;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.pjatk.trainmate.utils.Const.ADD_REPORT_BUTTON_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.API_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.API_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.CLOSE_REPORT_BUTTON_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.DASH;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.EMPTY_FIELD_ERROR;
import static pl.edu.pjatk.trainmate.utils.Const.INVALID_NUMBER_ERROR;
import static pl.edu.pjatk.trainmate.utils.Const.NEGATIVE_NUMBER_ERROR;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_ACCESS_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.REMARKS_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.REPS_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.REPS_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.RIR_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.RIR_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.SETS_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.TEMPO_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_TYPE;
import static pl.edu.pjatk.trainmate.utils.Const.WEIGHT_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.WEIGHT_TEXT;

import android.content.Intent;
import android.net.Uri;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.api.RetrofitApiClient;
import pl.edu.pjatk.trainmate.api.plan.ExerciseItemProjection;
import pl.edu.pjatk.trainmate.api.report.ReportClient;
import pl.edu.pjatk.trainmate.api.report.ReportCreateDto;
import pl.edu.pjatk.trainmate.api.report.SetParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private final List<ExerciseItemProjection> exerciseList;
    boolean isReportVisible = false;


    public ExerciseAdapter(List<ExerciseItemProjection> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseItemProjection exercise = exerciseList.get(position);
        holder.tvExerciseName.setText(exercise.getName());
        holder.tvExerciseDetails.setText(
            REPS_TEXT + exercise.getRepetitions() + WEIGHT_TEXT + exercise.getWeight() + SETS_TEXT + exercise.getSets() + TEMPO_TEXT + exercise.getTempo()
                + RIR_TEXT + exercise.getRir());
        holder.tvExerciseLink.setText(exercise.getUrl());
        holder.tvExerciseLink.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(exercise.getUrl()));
            holder.tvExerciseLink.getContext().startActivity(browserIntent);
        });
        holder.bind(exercise);

        if (exercise.isReported()) {
            holder.btnAddReport.setVisibility(View.GONE);
        } else {
            holder.btnAddReport.setVisibility(View.VISIBLE);
        }

        holder.btnAddReport.setOnClickListener(v -> changeLayout(holder, v, exercise.getSets()));

        holder.btnSendReport.setOnClickListener(v -> {
            if (validAllFields(holder)) {
                var reportClient = RetrofitApiClient.getRetrofitInstance().create(ReportClient.class);
                var token = v.getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_ACCESS_TOKEN, DEFAULT_STRING_VALUE);
                ReportCreateDto report = getPreparedReport(holder, exercise);
                reportClient.sendReport(report.getExerciseItemId(), TOKEN_TYPE + token, report).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        changeLayout(holder, v, exercise.getSets());
                        deleteSendButton(holder);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.e(API_TAG, API_FAIL + throwable.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    private ReportCreateDto getPreparedReport(ExerciseViewHolder holder, ExerciseItemProjection exercise) {
        List<SetParams> setParamsList = new ArrayList<>();
        String remarks = StringUtils.EMPTY;
        for (int i = 0; i < holder.layoutReports.getChildCount(); i++) {
            View view = holder.layoutReports.getChildAt(i);
            if (view instanceof LinearLayout row) {
                EditText weightEditText = (EditText) row.getChildAt(0);
                EditText repetitionsEditText = (EditText) row.getChildAt(1);
                EditText rirEditText = (EditText) row.getChildAt(2);

                int weight = Integer.parseInt(weightEditText.getText().toString());
                int repetitions = Integer.parseInt(repetitionsEditText.getText().toString());
                int rir = Integer.parseInt(rirEditText.getText().toString());

                setParamsList.add(new SetParams(repetitions, weight, rir, i));
            } else if (view instanceof EditText) {
                remarks = ((EditText) view).getText().toString();
            }
        }
        return new ReportCreateDto(exercise.getId(), setParamsList, remarks);
    }

    private void cleanAllFields(ExerciseViewHolder holder) {
        for (int i = 0; i < holder.layoutReports.getChildCount(); i++) {
            View childView = holder.layoutReports.getChildAt(i);
            if (childView != holder.btnSendReport) {
                holder.layoutReports.removeView(childView);
            }
        }
    }

    private void changeLayout(ExerciseViewHolder holder, View v, int sets) {
        cleanAllFields(holder);
        if (isReportVisible) {
            holder.btnAddReport.setText(ADD_REPORT_BUTTON_TEXT);
            holder.layoutReports.setVisibility(View.GONE);
            holder.btnSendReport.setVisibility(View.GONE);
            isReportVisible = false;
        } else {
            holder.btnAddReport.setText(CLOSE_REPORT_BUTTON_TEXT);
            holder.btnSendReport.setVisibility(View.VISIBLE);
            holder.layoutReports.setVisibility(View.VISIBLE);
            createReportWindow(holder, v, sets);
            isReportVisible = true;
        }
    }

    private void deleteSendButton(ExerciseViewHolder holder) {
        holder.btnAddReport.setVisibility(View.GONE);
    }

    private boolean validAllFields(ExerciseViewHolder holder) {
        boolean isValid = true;
        for (int i = 0; i < holder.layoutReports.getChildCount(); i++) {
            View view = holder.layoutReports.getChildAt(i);
            if (view instanceof LinearLayout row) {
                EditText weightEditText = (EditText) row.getChildAt(0);
                EditText repetitionsEditText = (EditText) row.getChildAt(1);
                EditText rirEditText = (EditText) row.getChildAt(2);

                if (weightEditText.getText().toString().trim().isEmpty()) {
                    weightEditText.setError(EMPTY_FIELD_ERROR);
                    isValid = false;
                }

                if (repetitionsEditText.getText().toString().trim().isEmpty()) {
                    repetitionsEditText.setError(EMPTY_FIELD_ERROR);
                    isValid = false;
                }

                if (rirEditText.getText().toString().trim().isEmpty()) {
                    rirEditText.setError(EMPTY_FIELD_ERROR);
                    isValid = false;
                }

                try {
                    int weight = Integer.parseInt(weightEditText.getText().toString().trim());
                    if (weight < 0) {
                        weightEditText.setError(NEGATIVE_NUMBER_ERROR);
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    weightEditText.setError(INVALID_NUMBER_ERROR);
                    isValid = false;
                }

                try {
                    int repetitions = Integer.parseInt(repetitionsEditText.getText().toString().trim());
                    if (repetitions < 0) {
                        repetitionsEditText.setError(NEGATIVE_NUMBER_ERROR);
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    repetitionsEditText.setError(INVALID_NUMBER_ERROR);
                    isValid = false;
                }

                try {
                    int rir = Integer.parseInt(rirEditText.getText().toString().trim());
                    if (rir < 0) {
                        rirEditText.setError(NEGATIVE_NUMBER_ERROR);
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    rirEditText.setError(INVALID_NUMBER_ERROR);
                    isValid = false;
                }
            }
        }
        return isValid;
    }


    private void createReportWindow(ExerciseViewHolder holder, View v, int sets) {
        cleanAllFields(holder);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;

        InputFilter negativeFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (source.charAt(i) == DASH) {
                    return StringUtils.EMPTY;
                }
            }
            return null;
        };

        for (int i = 0; i < sets; i++) {
            LinearLayout rowLayout = new LinearLayout(v.getContext());
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            EditText weightEditText = new EditText(v.getContext());
            weightEditText.setHint(WEIGHT_FOR_SERIES_TEXT + (i + 1));
            weightEditText.setLayoutParams(params);
            weightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            weightEditText.setFilters(new InputFilter[]{negativeFilter});

            rowLayout.addView(weightEditText);

            EditText repetitionsEditText = new EditText(v.getContext());
            repetitionsEditText.setHint(REPS_FOR_SERIES_TEXT + (i + 1));
            repetitionsEditText.setLayoutParams(params);
            repetitionsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            repetitionsEditText.setFilters(new InputFilter[]{negativeFilter});

            rowLayout.addView(repetitionsEditText);

            EditText rirEditText = new EditText(v.getContext());
            rirEditText.setHint(RIR_FOR_SERIES_TEXT + (i + 1));
            rirEditText.setLayoutParams(params);
            rirEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            rirEditText.setFilters(new InputFilter[]{negativeFilter});

            rowLayout.addView(rirEditText);
            holder.layoutReports.addView(rowLayout);
        }
        EditText remarks = new EditText(v.getContext());
        remarks.setHint(REMARKS_TEXT);
        holder.layoutReports.addView(remarks);
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView tvExerciseName, tvExerciseDetails, tvExerciseLink;
        Button btnAddReport;
        Button btnSendReport;
        LinearLayout layoutReports;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvExerciseDetails = itemView.findViewById(R.id.tvExerciseDetails);
            tvExerciseLink = itemView.findViewById(R.id.tvExerciseLink);
            btnAddReport = itemView.findViewById(R.id.btnAddReport);
            layoutReports = itemView.findViewById(R.id.layoutReports);
            btnSendReport = itemView.findViewById(R.id.btnSendReport);
        }

        public void bind(ExerciseItemProjection exercise) {
            tvExerciseName.setText(exercise.getName());
        }
    }
}