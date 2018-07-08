package com.houchins.andy.tracker;

import android.app.Application;

import com.houchins.andy.tracker.model.AppModel;
import com.houchins.andy.tracker.model.IAppModelListener;

/**
 * The tracker application class
 */

public class TrackerApplication extends Application implements ITrackerApplication {

    private AppModel appModel;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void initializeModel(IAppModelListener listener) {
        if (appModel == null) {
            appModel = new AppModel();
        }
        appModel.setListener(listener);
        appModel.init(this);
        // TODO: uncomment once init is no longer calling this method
        //appModel.loadData();
    }

    @Override
    public AppModel getModel() {
        return appModel;
    }
}
