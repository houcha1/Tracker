package com.houchins.andy.tracker.store;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.houchins.andy.tracker.data.ObservationRecord;
import com.houchins.andy.tracker.dataconverter.CsvConverter;
import com.houchins.andy.tracker.dataconverter.ObservationConverter;
import com.houchins.andy.tracker.model.IObservationModel;
import com.houchins.andy.tracker.model.IObservationModelListener;
import com.houchins.andy.tracker.model.Observation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ObservationStore implements IObservationStore, IObservationModelListener {
    private static final String LOG_TAG = "TRACKER";

    private IObservationModel observationModel;
    private IObservationStoreListener listener;

    /**
     * Creates the observation store
     *
     * @param observationModel the observation model to use
     */
    public ObservationStore(IObservationModel observationModel) {
        this.observationModel = observationModel;
    }

    @Override
    public void setListener(IObservationStoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize() {
        observationModel.initialize(this);
    }

    @Override
    public void onInitialized() {
        notifyInitialized();
    }

    /**
     * Gets the list of observations; returns an empty list if the store has not been initialized
     *
     * @return observations
     */
    @Override
    public List<Observation> getObservations() {
        List<Observation> observations = new ArrayList<>();
        if (observationModel != null) {
            List<ObservationRecord> observationRecords = observationModel.getObservationRecords();
            if (observationRecords != null) {
                for (ObservationRecord observationRecord : observationRecords) {
                    observations.add(ObservationConverter.convert(observationRecord));
                }
            }
        }
        return observations;
    }

    /**
     * exports observation data to a file
     *
     * @param context the application context
     * @return true if export was successful; false otherwise
     */
    @Override
    public boolean exportData(Context context) {
        boolean success = false;
        if (observationModel != null && observationModel.isInitialized()) {
            File file = getExportFile(context, getFileName());
            if (file != null && isExternalStorageWritable()) {
                try {
                    writeDataToFile(file);
                    success = true;
                } catch (IOException e) {
                    Log.e(LOG_TAG, "export data failed", e);
                }
            }
        }
        return success;
    }

    /**
     * writes observation data to the eport file
     *
     * @param file the export file
     * @throws IOException thrown if there si an issue writing to the file
     */
    private void writeDataToFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file)));
        writer.write(CsvConverter.convertToCsv(observationModel.getObservationRecords()));
        writer.flush();
        writer.close();
    }

    /**
     * Gets a file to use for exporting data
     *
     * @param context  the application context
     * @param fileName file name to use
     * @return export file
     */
    private File getExportFile(Context context, String fileName) {
        File file = new File(context.getExternalFilesDir(null), fileName);
        try {
            if (!file.createNewFile()) {
                Log.e(LOG_TAG, String.format("File %s could not be created", file.getAbsolutePath()));
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, String.format("Error creating file %s", file.getAbsolutePath()), e);
            file = null;
        }
        return file;
    }

    /**
     * Gets the file name to use for exporting data
     *
     * @return the export file name based on the current time
     */
    private String getFileName() {
        final String filePrefix = "data";
        final String fileExtension = ".csv";
        return filePrefix + getNow().getTime() + fileExtension;
    }

    /**
     * Gets the date representing the current time in UTC
     *
     * @return now in UTC
     */
    private Date getNow() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTimeInMillis(System.currentTimeMillis());
        return c.getTime();
    }

    /**
     * Checks if external storage is available for read and write
     */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks if external storage is available to at least read
     */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * Notifies the listener that data has been loaded
     */
    private void notifyInitialized() {
        if (listener != null) {
            listener.onInitialized();
        }
    }
}
