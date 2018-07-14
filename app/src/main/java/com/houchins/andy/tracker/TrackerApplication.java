package com.houchins.andy.tracker;

import android.app.Application;

import com.houchins.andy.tracker.model.ObservationModel;
import com.houchins.andy.tracker.store.IObservationStore;
import com.houchins.andy.tracker.store.IStoreProvider;
import com.houchins.andy.tracker.store.ObservationStore;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * The tracker application class
 */

public class TrackerApplication extends Application implements IStoreProvider {

    private ObservationModel observationModel;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the database manager
        FlowManager.init(getApplicationContext());

        // Create models
        createModels();
    }

    @Override
    public IObservationStore getObservationStore() {
        return new ObservationStore(observationModel);
    }

    private void createModels() {
        observationModel = new ObservationModel();
    }
}
