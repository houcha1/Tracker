package com.houchins.andy.tracker.activity;

import android.os.Bundle;

import com.houchins.andy.tracker.presenter.TrackerPresenter;

public class TrackerActivity extends BaseActivity {

    private static final String TAG = "TRACKER";

    private TrackerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TrackerPresenter(getStoreProvider().getObservationStore());
        setContentView(presenter.getView(getLayoutInflater(), this));
    }
}
