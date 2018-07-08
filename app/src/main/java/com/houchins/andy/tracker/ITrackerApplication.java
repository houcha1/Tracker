package com.houchins.andy.tracker;

import com.houchins.andy.tracker.model.AppModel;
import com.houchins.andy.tracker.model.IAppModelListener;

/**
 * Created by Lisa on 6/16/2018.
 */

public interface ITrackerApplication {
    void initializeModel(IAppModelListener listener);
    AppModel getModel();
}
