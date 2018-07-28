package com.houchins.andy.tracker.dataconverter;

import com.houchins.andy.tracker.data.ObservationRecord;
import com.houchins.andy.tracker.model.Observation;

import static com.houchins.andy.tracker.model.Observation.INVALID_TEMPERATURE;

/**
 * Converts between {@link Observation} and {@link ObservationRecord} types
 */

public class ObservationConverter {
    public static Observation convert(ObservationRecord observationRecord) {
        Observation observation = new Observation();
        if (observationRecord != null) {
            observation.setDaysSinceEpoch(observationRecord.getDaysSinceEpoch());
            observation.setTemperature((double) observationRecord.getTemperature());
            observation.setFlags(observationRecord.getFlags());
        }
        return observation;
    }

    public static void fillRecord(ObservationRecord record, Observation observation) {
        record.setFlags(observation.getFlags());
        record.setDaysSinceEpoch(observation.getDaysSinceEpoch());
        double temperature = observation.getTemperature();
        if (Double.isNaN(temperature)) {
            temperature = INVALID_TEMPERATURE;
        }
        record.setTemperature((float) temperature);
    }
}
