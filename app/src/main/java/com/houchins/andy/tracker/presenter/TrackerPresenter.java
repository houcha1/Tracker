package com.houchins.andy.tracker.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.Observation;
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

    public TrackerPresenter(IObservationStore observationStore) {
        this.observationStore = observationStore;
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_tracker, null);
            recyclerView = view.findViewById(R.id.tracker_recycler_view);
            initialize(context);
        }
        return view;
    }

    @Override
    public void onItemSelected(int position, Object item) {
        beginEdit((Observation) item);
    }

    private void initialize(Context context) {
        adapter = new ObservationAdapter(observationStore.getObservations(), true, true, true);
        adapter.setOnItemSelectedListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void beginEdit(Observation observation) {
        if (!editInProgress) {
            Log.d(LOG_TAG, "Start Edit Observation: " + observation);
            editInProgress = true;
            // TODO: start edit and listen for completion
        }
    }
}
