package com.houchins.andy.tracker.store;

import android.content.Context;
import android.util.SparseArray;

import com.houchins.andy.tracker.model.Observation;

public interface IObservationStore {
    void setListener(IObservationStoreListener listener);

    void initialize();

    SparseArray<Observation> getObservations();

    Observation getObservation(int daysSinceEpoch);

    void saveObservation(Observation observation);

    boolean exportData(Context context);
}
