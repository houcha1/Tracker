package com.houchins.andy.tracker.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.Observation;
import com.houchins.andy.tracker.store.IObservationStore;
import com.houchins.andy.tracker.view.ObservationAdapter;
import com.houchins.andy.tracker.view.ObservationView;

import java.util.List;

public class TrackerPresenter implements IPresenter {
    private static final String LOG_TAG = "TRACKER";
    private static final int OBSERVATION_COUNT = 7;
    private IObservationStore observationStore;
    private View view;
    private RecyclerView recyclerView;
    private ObservationView[] observationViews = new ObservationView[OBSERVATION_COUNT];

    public TrackerPresenter(IObservationStore observationStore) {
        this.observationStore = observationStore;
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_tracker, null);
            recyclerView = view.findViewById(R.id.tracker_recycler_view);
            observationViews[0] = view.findViewById(R.id.observation_1);
            observationViews[1] = view.findViewById(R.id.observation_2);
            observationViews[2] = view.findViewById(R.id.observation_3);
            observationViews[3] = view.findViewById(R.id.observation_4);
            observationViews[4] = view.findViewById(R.id.observation_5);
            observationViews[5] = view.findViewById(R.id.observation_6);
            observationViews[6] = view.findViewById(R.id.observation_7);
            initialize(context);
        }
        return view;
    }

    private void initialize(Context context) {
//        final float MAX_TEMP = 99.0f;
//        final float MIN_TEMP = 95.0f;
//        if (observationStore != null) {
//            List<Observation> observations = observationStore.getObservations();
//            int dataOffset = 3;
//            for (int i = 0; i < observations.size() && i < OBSERVATION_COUNT; i++) {
//                observationViews[i].setTemperatureRange(MIN_TEMP, MAX_TEMP);
//                observationViews[i].setTemperature(
//                        getStartTemperature(observations, i + dataOffset, i == 0),
//                        getMiddleTemperature(observations, i + dataOffset),
//                        getEndTemperature(observations, i + dataOffset, i == OBSERVATION_COUNT - 1));
//            }
//        }
        recyclerView.setAdapter(new ObservationAdapter(observationStore.getObservations()));
        recyclerView.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     */
    public void setRecyclerViewLayoutManager(Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private double getStartTemperature(List<Observation> observations, int index, boolean isFirst) {
        double temperature = Double.NaN;
        if (!isFirst && index > 0) {
            temperature = (observations.get(index - 1).getTemperature() +
                    observations.get(index).getTemperature()) / 2.0;
        }
        return temperature;
    }

    private double getMiddleTemperature(List<Observation> observations, int index) {
        return observations.get(index).getTemperature();
    }

    private double getEndTemperature(List<Observation> observations, int index, boolean isLast) {
        double temperature = Double.NaN;
        if (!isLast && index < observations.size() - 1) {
            temperature = (observations.get(index + 1).getTemperature() +
                    observations.get(index).getTemperature()) / 2.0;
        }
        return temperature;
    }
}
