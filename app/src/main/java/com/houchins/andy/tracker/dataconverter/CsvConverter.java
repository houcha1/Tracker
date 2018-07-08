package com.houchins.andy.tracker.dataconverter;

import com.houchins.andy.tracker.data.ObservationRecord;

import java.util.List;

/**
 * Converts records to CSV format
 */

public class CsvConverter {
    public static String convertToCsv(List<ObservationRecord> observationRecords) {
        StringBuilder builder = new StringBuilder();
        for (ObservationRecord observationRecord : observationRecords) {
            appendCsv(builder, observationRecord);
        }
        return builder.toString();
    }

    private static void appendCsv(StringBuilder builder, ObservationRecord observationRecord) {
        builder.append(observationRecord.getDaysSinceEpoch());
        builder.append(',');
        builder.append(observationRecord.getTemperature());
        builder.append(',');
        builder.append(observationRecord.getFlags());
        builder.append('\n');
    }
}
