package com.houchins.andy.tracker.dataconverter;

import com.houchins.andy.tracker.data.ObservationRecord;
import com.houchins.andy.tracker.model.DateHelper;
import com.houchins.andy.tracker.model.Observation;

/**
 * Converts between {@link Observation} and {@link ObservationRecord} types
 */

public class ObservationConverter {
    public static Observation convert(ObservationRecord observationRecord) {
        Observation observation = new Observation();
        if (observationRecord != null) {
            observation.setDate(DateHelper.getDate(observationRecord.getDaysSinceEpoch()));
            observation.setTemperature((double) observationRecord.getTemperature());
            observation.setFlags(observationRecord.getFlags());
        }
        return observation;
    }
}
