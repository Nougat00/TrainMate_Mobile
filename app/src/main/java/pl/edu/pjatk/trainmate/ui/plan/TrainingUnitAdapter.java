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

/**
 * Adapter class for managing the display of training units in a RecyclerView.
 */
public class TrainingUnitAdapter extends RecyclerView.Adapter<TrainingPlanViewHolder> {

    private final List<TrainingUnitProjection> trainingList;

    /**
     * Constructor for initializing the adapter with a list of training units.
     *
     * @param trainingList List of training units to be displayed.
     */
    public TrainingUnitAdapter(List<TrainingUnitProjection> trainingList) {
        this.trainingList = trainingList;
    }

    /**
     * Creates a new ViewHolder for displaying a training unit.
     *
     * @param parent   The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new TrainingPlanViewHolder that holds the View for a training unit.
     */
    @NonNull
    @Override
    public TrainingPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        return new TrainingPlanViewHolder(view);
    }

    /**
     * Binds the data of a training unit to the provided ViewHolder.
     *
     * @param holder   The ViewHolder that should be updated to represent the contents of the training unit.
     * @param position The position of the training unit within the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull TrainingPlanViewHolder holder, int position) {
        TrainingUnitProjection training = trainingList.get(position);
        holder.tvDayOfWeek.setText(training.getDayOfWeek().toString());

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(training.getExercises());
        holder.rvExercises.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvExercises.setAdapter(exerciseAdapter);
    }

    /**
     * Returns the total number of training units in the dataset.
     *
     * @return The number of training units.
     */
    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    /**
     * ViewHolder class for managing the individual item views for training units.
     */
    static class TrainingPlanViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDayOfWeek;
        private final RecyclerView rvExercises;

        /**
         * Constructor for initializing the ViewHolder with the item view.
         *
         * @param itemView The view of the individual item.
         */
        public TrainingPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            rvExercises = itemView.findViewById(R.id.rvExercises);
        }
    }
}
