package com.houchins.andy.tracker.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.activity.EditObservationActivity;
import com.houchins.andy.tracker.store.IObservationStore;
import com.houchins.andy.tracker.view.ObservationAdapter;
import com.houchins.andy.tracker.view.OnItemSelectedListener;

public class TrackerPresenter implements IPresenter, OnItemSelectedListener {
    private static final String LOG_TAG = "TrackerPresenter";
    private IObservationStore observationStore;
    private View view;
    private RecyclerView recyclerView;
    private ObservationAdapter adapter;
    boolean editInProgress = false;
    private int selectedIndex = -1;

    public TrackerPresenter(IObservationStore observationStore) {
        this.observationStore = observationStore;
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.tracker_activity, null);
            initialize(context);
        }
        return view;
    }

    public void onResume() {
        observationStore.initialize();
        initializeAdapter();
        editInProgress = false;
    }

    @Override
    public void onItemSelected(int position, Object item) {
        selectedIndex = position;
    }

    public void editSelectedItem() {
        if (selectedIndex >= 0) {
            startEdit(selectedIndex);
        }
    }

    private void initialize(Context context) {
        recyclerView = view.findViewById(R.id.tracker_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initializeAdapter() {
        adapter = new ObservationAdapter(observationStore.getObservations(),
                selectedIndex,
                true, true, true);
        adapter.setOnItemSelectedListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void startEdit(int position) {
        if (!editInProgress) {
            Log.d(LOG_TAG, "Start Edit Observation: " + position);
            editInProgress = true;
            Activity activity = (Activity) view.getContext();
            Intent intent = new Intent(activity, EditObservationActivity.class);
            intent.putExtra(EditObservationActivity.INTENT_OBSERVATION,
                    adapter.getObservation(position).getDaysSinceEpoch());
            activity.startActivity(intent);
        }
    }
}
