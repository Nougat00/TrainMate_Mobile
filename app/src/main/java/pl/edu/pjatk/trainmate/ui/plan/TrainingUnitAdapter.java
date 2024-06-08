package pl.edu.pjatk.trainmate.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.api.plan.TrainingUnitProjection;
import pl.edu.pjatk.trainmate.ui.plan.TrainingUnitAdapter.TrainingPlanViewHolder;

public class TrainingUnitAdapter extends RecyclerView.Adapter<TrainingPlanViewHolder> {

    private List<TrainingUnitProjection> trainingList;

    public TrainingUnitAdapter(List<TrainingUnitProjection> trainingList) {
        this.trainingList = trainingList;
    }

    @NonNull
    @Override
    public TrainingPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new TrainingPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingPlanViewHolder holder, int position) {
        TrainingUnitProjection training = trainingList.get(position);
        holder.tvDayOfWeek.setText(training.getDayOfWeek().toString());

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(training.getExercises());
        holder.rvExercises.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvExercises.setAdapter(exerciseAdapter);
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    class TrainingPlanViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDayOfWeek;
        private final RecyclerView rvExercises;

        public TrainingPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            rvExercises = itemView.findViewById(R.id.rvExercises);
        }
    }
}