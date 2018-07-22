package com.houchins.andy.tracker.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ToggleButton;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.Mucus;
import com.houchins.andy.tracker.model.Observation;
import com.houchins.andy.tracker.store.IObservationStore;

public class EditObservationPresenter implements IPresenter {

    private static final String LOG_TAG = "EditObservationPresenter";
    private View view;
    private ToggleButton mucusDryView;
    private ToggleButton mucusMView;
    private ToggleButton mucusEwmView;
    private IObservationStore observationStore;
    private int position;

    public EditObservationPresenter(IObservationStore observationStore, int position) {
        this.observationStore = observationStore;
        this.position = position;
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.edit_observation_activity, null);
            initialize(context);
        }
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mucus_dry:
                setMucus(Mucus.DRY);
                break;
            case R.id.mucus_m:
                setMucus(Mucus.M);
                break;
            case R.id.mucus_ewm:
                setMucus(Mucus.EWM);
                break;
        }
    }

    private void initialize(Context context) {
        mucusDryView = view.findViewById(R.id.mucus_dry);
        mucusMView = view.findViewById(R.id.mucus_m);
        mucusEwmView = view.findViewById(R.id.mucus_ewm);
        updateMucusViews();
    }

    private void setMucus(Mucus mucus) {
        if(position >= 0) {
            //TODO: save in observation
            updateMucusViews();
        }
    }

    private void updateMucusViews() {
        // TODO: update view based on observation
//        mucusDryView.setChecked(observation.getMucus() == Mucus.DRY);
//        mucusMView.setChecked(observation.getMucus() == Mucus.M);
//        mucusEwmView.setChecked(observation.getMucus() == Mucus.EWM);
    }
}
