package com.houchins.andy.tracker.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.presenter.WelcomePresenter;

/**
 * Initial activity
 */

public class WelcomeActivity extends BaseActivity {

    private WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WelcomePresenter(getStoreProvider().getObservationStore());
        setContentView(presenter.getView(getLayoutInflater(), this));
        presenter.initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.export_data:
                presenter.exportData(getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_start_button:
                presenter.startTracker(this);
                break;
            default:
                break;
        }
    }
}
