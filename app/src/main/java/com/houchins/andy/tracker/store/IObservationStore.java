package com.houchins.andy.tracker.store;

import android.content.Context;

import com.houchins.andy.tracker.model.Observation;

import java.util.List;

public interface IObservationStore {
    void setListener(IObservationStoreListener listener);

    void initialize();

    List<Observation> getObservations();

    boolean exportData(Context context);
}
