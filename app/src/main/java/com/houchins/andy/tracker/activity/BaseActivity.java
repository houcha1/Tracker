package com.houchins.andy.tracker.activity;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.houchins.andy.tracker.store.IStoreProvider;

public class BaseActivity extends AppCompatActivity {

    protected IStoreProvider getStoreProvider() {
        IStoreProvider storeProvider = null;
        Application application = getApplication();
        if (application instanceof IStoreProvider) {
            storeProvider = (IStoreProvider) application;
        }
        return storeProvider;
    }
}
