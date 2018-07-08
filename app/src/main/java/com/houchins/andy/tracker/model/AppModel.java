package com.houchins.andy.tracker.model;

import android.content.Context;
import android.os.Handler;

import com.houchins.andy.tracker.data.AppDatabase;
import com.houchins.andy.tracker.data.ObservationRecord;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.List;

/**
 * Created by Lisa on 6/16/2018.
 */

public class AppModel {
    private boolean isInitialized = false;
    private IAppModelListener listener;
    private List<ObservationRecord> observationRecords;

    public void setListener(IAppModelListener listener) {
        this.listener = listener;
    }

    public List<ObservationRecord> getObservationRecords() {
        return observationRecords;
    }

    public void init(Context context) {
        FlowManager.init(context);
        // TODO: Normally data will be loaded here, but for now we ar estartgin by creating
        // temporary data for testing the app; remove and uncomment the loadData call
        //createData();
        loadData();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * TEMPORARY: creates some default data asynchronously for testing the app
     */
    private void createData() {
        FlowManager.getDatabase(AppDatabase.class)
                .reset();
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                createDataSync();
            }
        });
    }

    /**
     * TEMPORARY: creates some default data synchronously for testing the app
     */
    private void createDataSync() {
        ObservationRecord o;
        for (int i = 0; i < 1000; i++) {
            o = new ObservationRecord();
            o.setFlags((int) (Math.random() * (Observation.FLAG_ALL_USED + 1)));
            o.setDaysSinceEpoch(DateHelper.getDays(2000, 1, i));
            o.setTemperature(97.0f + (float) (Math.random() * 2.0));
            o.save();
        }
//        o = new ObservationRecord();
//        o.setFlags(Observation.FLAG_CERVIX_FIRM | Observation.FLAG_CERVIX_LOW | Observation.FLAG_CERVIX_CLOSED | Observation.FLAG_MUCUS_DRY);
//        o.setDaysSinceEpoch(DateHelper.getDays(1981, 7, 31));
//        o.setTemperature(98.6f);
//        o.save();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onDataCreated();
            }
        });
    }

    /**
     * TEMPORARY: triggers loading of data after the creation of the test data
     */
    private void onDataCreated() {
        loadData();
    }

    /**
     * Loads data asynchronously from the database; when finished, {@link IAppModelListener}
     * onDataLoaded is called.
     */
    private void loadData() {

        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                loadDataSync();
            }
        });
    }

    /**
     * Loads data synchronously from the database; Calls {@link IAppModelListener} onDataLoaded on
     * the main thread.
     */
    private void loadDataSync() {
        observationRecords = SQLite.select()
                .from(ObservationRecord.class)
                .queryList();
        isInitialized = true;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onDataLoaded();
                }
            }
        });
    }
}
