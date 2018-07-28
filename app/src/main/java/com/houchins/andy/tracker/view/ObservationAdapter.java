package com.houchins.andy.tracker.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.DateHelper;
import com.houchins.andy.tracker.model.Observation;

import static com.houchins.andy.tracker.model.Observation.INVALID_TEMPERATURE;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder> implements OnItemSelectedListener {
    private static final String LOG_TAG = "ObservationAdapter";

    private SparseArray<Observation> dataSet;
    private int dataSetCount;
    private OnItemSelectedListener onItemSelectedListener;
    private boolean showTemperature;
    private boolean showCervix;
    private boolean showMucus;
    private int selectedPosition = -1;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ObservationView view;
        private final OnItemSelectedListener listener;

        ViewHolder(View v, OnItemSelectedListener listener) {
            super(v);
            this.listener = listener;
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "Element " + getAdapterPosition() + " clicked.");
                    if (ViewHolder.this.listener != null) {
                        ViewHolder.this.listener.onItemSelected(getAdapterPosition(), null);
                    }
                }
            });
            view = (ObservationView) v;
        }

        public ObservationView getView() {
            return view;
        }
    }

    /**
     * Initialize the data set of the Adapter.
     *
     * @param dataSet          the data to populate views to be used by RecyclerView.
     * @param selectedPosition the initially selected position; less thatn zero for no selection
     * @param showTemperature  show temperature graph
     * @param showCervix       show cervix observations
     * @param showMucus        show mucus observations
     */
    public ObservationAdapter(SparseArray<Observation> dataSet, int selectedPosition,
                              boolean showTemperature, boolean showCervix, boolean showMucus) {
        this.dataSet = dataSet;
        this.showTemperature = showTemperature;
        this.showCervix = showCervix;
        this.showMucus = showMucus;
        this.selectedPosition = selectedPosition;
        calculateDataSetCount();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = new ObservationView(viewGroup.getContext());
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        // Get nearby observations
        Observation observation = getObservation(position);
        Observation previousObservation = getObservation(position - 1);
        Observation nextObservation = getObservation(position + 1);

        // calculate nearby temperatures
        double t1 = roundTemperature(previousObservation.getTemperature());
        double t2 = roundTemperature(observation.getTemperature());
        double t3 = roundTemperature(nextObservation.getTemperature());

        // Update views
        ObservationView view = viewHolder.getView();
        view.setObservationVisibility(showTemperature, showCervix, showMucus);
        view.setTemperature(t1, t2, t3);
        if (position == selectedPosition) {
            setViewSelected(observation, view);
        } else {
            setViewUnselected(observation, view);
        }
        view.setCervixTextureText(ObservationViewFormatter.getTextId(observation.getCervixTexture()));
        view.setCervixHeightText(ObservationViewFormatter.getTextId(observation.getCervixHeight()));
        view.setCervixShapeText(ObservationViewFormatter.getTextId(observation.getCervixShape()));
        view.setMucusText(ObservationViewFormatter.getTextId(observation.getMucus()));
    }

    private void setViewUnselected(Observation observation, ObservationView view) {
        view.setGraphColor(R.color.colorGridLine, R.color.colorPrimaryDark,
                R.color.colorShadow, R.color.colorBackground);
        view.setCervixTextureColor(ObservationViewFormatter.getColorId(observation.getCervixTexture()),
                R.color.colorPrimaryDark);
        view.setCervixHeightColor(ObservationViewFormatter.getColorId(observation.getCervixHeight()),
                R.color.colorPrimaryDark);
        view.setCervixShapeColor(ObservationViewFormatter.getColorId(observation.getCervixShape()),
                R.color.colorPrimaryDark);
        view.setMucusColor(ObservationViewFormatter.getColorId(observation.getMucus()),
                R.color.colorPrimaryDark);
    }

    private void setViewSelected(Observation observation, ObservationView view) {
        view.setGraphColor(R.color.colorGridLine, R.color.colorAccent,
                R.color.colorAccentLevel2, R.color.colorAccentLevel1);
        view.setCervixTextureColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixTexture()),
                R.color.colorAccentDark);
        view.setCervixHeightColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixHeight()),
                R.color.colorAccentDark);
        view.setCervixShapeColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixShape()),
                R.color.colorAccentDark);
        view.setMucusColor(ObservationViewFormatter.getSelectedColorId(observation.getMucus()),
                R.color.colorAccentDark);
    }

    @Override
    public int getItemCount() {
        return dataSetCount;
    }

    @Override
    public void onItemSelected(int position, Object item) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;
        if (previousSelectedPosition >= 0) {
            notifyItemChanged(previousSelectedPosition);
        }
        if (selectedPosition >= 0) {
            notifyItemChanged(selectedPosition);
        }
        notifyItemSelected(position, dataSet.get(position));
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        onItemSelectedListener = listener;
    }

    private void notifyItemSelected(int position, Observation observation) {
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(position, observation);
        }
    }

    private double roundTemperature(double temperature) {
        if (!Double.isNaN(temperature) && (temperature != INVALID_TEMPERATURE)) {
            temperature = ((int) (temperature * 10 + 0.5)) / 10.0f;
        } else {
            temperature = Double.NaN;
        }
        return temperature;
    }

    private void calculateDataSetCount() {
        dataSetCount = 1;
        if (dataSet.size() > 1) {
            dataSetCount += dataSet.get(dataSet.keyAt(dataSet.size() - 1)).getDaysSinceEpoch() -
                    dataSet.get(dataSet.keyAt(0)).getDaysSinceEpoch();
        }
    }

    public Observation getObservation(int position) {
        // get the days since epoch for this position
        int daysSinceEpoch = (dataSet.size() == 0) ? DateHelper.getDaysSinceEpoch() :
                dataSet.get(dataSet.keyAt(0)).getDaysSinceEpoch() + position;
        Observation observation = dataSet.get(daysSinceEpoch);
        if (observation == null) {
            observation = new Observation();
            observation.setDaysSinceEpoch(daysSinceEpoch);
        }
        return observation;
    }
}

