package pl.edu.pjatk.trainmate.ui.plan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for the Plan screen in the TrainMate application.
 * This ViewModel manages and provides data for the UI, handling configuration changes and lifecycle events.
 */
public class PlanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Constructor that initializes the MutableLiveData object.
     */
    public PlanViewModel() {
        mText = new MutableLiveData<>();
    }

    /**
     * Returns the LiveData object containing the text data.
     *
     * @return A LiveData object containing a String.
     */
    public LiveData<String> getText() {
        return mText;
    }

}