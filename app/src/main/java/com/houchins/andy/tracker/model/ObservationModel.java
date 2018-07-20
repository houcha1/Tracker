package com.houchins.andy.tracker.model;

import android.os.Handler;

import com.houchins.andy.tracker.data.AppDatabase;
import com.houchins.andy.tracker.data.ObservationRecord;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.List;

/**
 * Model to hold observation data
 */

public class ObservationModel implements IObservationModel {
    private boolean isInitialized = false;
    private List<ObservationRecord> observationRecords;

    @Override
    public List<ObservationRecord> getObservationRecords() {
        return observationRecords;
    }

    @Override
    public void initialize(IObservationModelListener listener) {
        //createData(listener);
        loadData(listener);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * TEMPORARY: creates some default data asynchronously for testing the app
     */
    private void createData(final IObservationModelListener listener) {
        FlowManager.getDatabase(AppDatabase.class)
                .reset();
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                createDataSync(listener);
            }
        });
    }

    /**
     * TEMPORARY: creates some default data synchronously for testing the app
     *
     * @param listener the listener for load data events
     */
    private void createDataSync(final IObservationModelListener listener) {
        ObservationRecord o;
        for (int i = 0; i < 1000; i++) {
            o = new ObservationRecord();
            o.setFlags((int) (Math.random() * (Observation.FLAG_ALL_USED + 1)));
            o.setDaysSinceEpoch(DateHelper.getDays(2000, 1, i));
            o.setTemperature(97.0f + (float) (Math.random() * 2.0));
            o.save();
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onDataCreated(listener);
            }
        });
    }

    /**
     * TEMPORARY: triggers loading of data after the creation of the test data
     *
     * @param listener the listener for load data events
     */
    private void onDataCreated(IObservationModelListener listener) {
        loadData(listener);
    }

    /**
     * Loads data asynchronously from the database; when finished, {@link IObservationModelListener}
     * onInitialized is called.
     *
     * @param listener the listener for load data events
     */
    private void loadData(final IObservationModelListener listener) {

        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                loadDataSync(listener);
            }
        });
    }

    /**
     * Loads data synchronously from the database; Calls {@link IObservationModelListener} onInitialized on
     * the main thread.
     *
     * @param listener the listener for load data events
     */
    private void loadDataSync(final IObservationModelListener listener) {
        observationRecords = SQLite.select()
                .from(ObservationRecord.class)
                .queryList();
        isInitialized = true;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataLoaded(listener);
            }
        });
    }

    /**
     * Notifies the listener that data has been loaded
     *
     * @param listener the listener
     */
    private void notifyDataLoaded(IObservationModelListener listener) {
        if (listener != null) {
            listener.onInitialized();
        }
    }
}
