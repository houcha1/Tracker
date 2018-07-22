package com.houchins.andy.tracker.activity;

import android.os.Bundle;
import android.view.View;

import com.houchins.andy.tracker.presenter.EditObservationPresenter;

public class EditObservationActivity extends BaseActivity {

    private static final String TAG = "EditObservationActivity";
    public static final String INTENT_OBSERVATION = "Observation";

    private EditObservationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EditObservationPresenter(
                getStoreProvider().getObservationStore(),
                getIntent().getIntExtra(INTENT_OBSERVATION, -1));
        getIntent().getIntExtra(INTENT_OBSERVATION, -1);
        setContentView(presenter.getView(getLayoutInflater(), this));
    }

    public void onClick(View view) {
        presenter.onClick(view);
    }
}
