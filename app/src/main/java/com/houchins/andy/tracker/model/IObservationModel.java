package com.houchins.andy.tracker.model;

import com.houchins.andy.tracker.data.ObservationRecord;

import java.util.List;

public interface IObservationModel {

    void initialize(IObservationModelListener listener);

    boolean isInitialized();

    List<ObservationRecord> getObservationRecords();

    ObservationRecord getObservationRecord(int daysSinceEpoch);
}
