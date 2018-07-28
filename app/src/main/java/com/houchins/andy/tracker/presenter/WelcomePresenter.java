package com.houchins.andy.tracker.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.activity.TrackerActivity;
import com.houchins.andy.tracker.model.DateHelper;
import com.houchins.andy.tracker.model.Observation;
import com.houchins.andy.tracker.store.IObservationStore;
import com.houchins.andy.tracker.store.IObservationStoreListener;

/**
 * Presenter for the Welcome screen
 */

public class WelcomePresenter implements IPresenter, IObservationStoreListener {
    private static final String LOG_TAG = "WelcomePresenter";
    private IObservationStore observationStore;
    private View view;
    private TextView subtitle;

    public WelcomePresenter(IObservationStore observationStore) {
        this.observationStore = observationStore;
        this.observationStore.setListener(this);
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.welcome_activity, null);
            subtitle = view.findViewById(R.id.welcome_subtitle);
        }
        return view;
    }

    public void initialize() {
        subtitle.setText(R.string.subtitle_loading_text);
        observationStore.initialize();
    }


    @Override
    public void onInitialized() {
        subtitle.setText(R.string.subtitle_loading_complete_text);
        Observation observation;
        SparseArray<Observation> observations = observationStore.getObservations();
        int size = observations.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                observation = observations.get(observations.keyAt(i));
                Log.d(LOG_TAG, "RECORD: " + DateHelper.getDate(observation.getDaysSinceEpoch()) +
                        " " + observation.toString());
            }
        }
    }

    /**
     * export data to file
     *
     * @param context the application context
     */
    public void exportData(Context context) {
        observationStore.exportData(context);
    }

    public void startTracker(Activity activity) {
        activity.startActivity(new Intent(activity, TrackerActivity.class));
    }
}
