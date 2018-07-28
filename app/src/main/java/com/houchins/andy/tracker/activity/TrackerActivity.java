package com.houchins.andy.tracker.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.presenter.TrackerPresenter;

public class TrackerActivity extends BaseActivity {

    private static final String TAG = "TrackerActivity";

    private TrackerPresenter presenter;
    private MenuItem editObservationItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TrackerPresenter(getStoreProvider().getObservationStore());
        setContentView(presenter.getView(getLayoutInflater(), this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tracker_menu, menu);
        editObservationItem = menu.findItem(R.id.edit_observation);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_observation:
                presenter.editSelectedItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
