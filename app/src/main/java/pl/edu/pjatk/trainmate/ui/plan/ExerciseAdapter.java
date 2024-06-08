package pl.edu.pjatk.trainmate.ui.plan;

import static pl.edu.pjatk.trainmate.utils.Const.ADD_REPORT_BUTTON_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.CLOSE_REPORT_BUTTON_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.REPS_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.REPS_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.RIR_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.RIR_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.SETS_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.TEMPO_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.WEIGHT_FOR_SERIES_TEXT;
import static pl.edu.pjatk.trainmate.utils.Const.WEIGHT_TEXT;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.api.plan.ExerciseItemProjection;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<ExerciseItemProjection> exerciseList;
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

        holder.btnAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout(holder, v, exercise.getSets());
            }
        });

        holder.btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < holder.layoutReports.getChildCount(); i++) {
                    View view = holder.layoutReports.getChildAt(i);
                    if (view instanceof EditText) {
                        String report = ((EditText) view).getText().toString();
                        // Przetwarzanie raportu
                    }
                }
                holder.layoutReports.setVisibility(View.GONE);
                holder.btnSendReport.setVisibility(View.GONE);
                holder.btnAddReport.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
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

    private void createReportWindow(ExerciseViewHolder holder, View v, int sets) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;
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
            rowLayout.addView(weightEditText);

            EditText repetitionsEditText = new EditText(v.getContext());
            repetitionsEditText.setHint(REPS_FOR_SERIES_TEXT + (i + 1));
            repetitionsEditText.setLayoutParams(params);
            rowLayout.addView(repetitionsEditText);

            EditText rirEditText = new EditText(v.getContext());
            rirEditText.setHint(RIR_FOR_SERIES_TEXT + (i + 1));
            rirEditText.setLayoutParams(params);
            rowLayout.addView(rirEditText);
            holder.layoutReports.addView(rowLayout);
        }
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