package com.houchins.andy.tracker.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.Observation;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder> implements OnItemSelectedListener {
    private static final String LOG_TAG = "ObservationAdapter";

    private List<Observation> dataSet;
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
     * @param dataSet         the data to populate views to be used by RecyclerView.
     * @param showTemperature show temperature graph
     * @param showCervix      show cervix observations
     * @param showMucus       show mucus observations
     */
    public ObservationAdapter(List<Observation> dataSet, boolean showTemperature, boolean showCervix, boolean showMucus) {
        this.dataSet = dataSet;
        this.showTemperature = showTemperature;
        this.showCervix = showCervix;
        this.showMucus = showMucus;
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
        Observation observation = dataSet.get(position);
        double t1 = (position - 1 >= 0) ?
                roundTemperature(dataSet.get(position - 1).getTemperature()) :
                Double.NaN;
        double t2 = roundTemperature(observation.getTemperature());
        double t3 = (position + 1 < dataSet.size()) ?
                roundTemperature(dataSet.get(position + 1).getTemperature()) :
                Double.NaN;

        ObservationView view = viewHolder.getView();
        view.setObservationVisibility(showTemperature, showCervix, showMucus);
        view.setTemperature(t1, t2, t3);
        if (position == selectedPosition) {
            view.setGraphColor(R.color.colorGridLine, R.color.colorAccent,
                    R.color.colorAccentLevel2, R.color.colorAccentLevel1);
            view.setCervixFirmnessColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixFirmness()),
                    R.color.colorAccentDark);
            view.setCervixHeightColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixHeight()),
                    R.color.colorAccentDark);
            view.setCervixOpennessColor(ObservationViewFormatter.getSelectedColorId(observation.getCervixOpenness()),
                    R.color.colorAccentDark);
            view.setMucusColor(ObservationViewFormatter.getSelectedColorId(observation.getMucus()),
                    R.color.colorAccentDark);
        } else {
            view.setGraphColor(R.color.colorGridLine, R.color.colorPrimaryDark,
                    R.color.colorShadow, R.color.colorBackground);
            view.setCervixFirmnessColor(ObservationViewFormatter.getColorId(observation.getCervixFirmness()),
                    R.color.colorPrimaryDark);
            view.setCervixHeightColor(ObservationViewFormatter.getColorId(observation.getCervixHeight()),
                    R.color.colorPrimaryDark);
            view.setCervixOpennessColor(ObservationViewFormatter.getColorId(observation.getCervixOpenness()),
                    R.color.colorPrimaryDark);
            view.setMucusColor(ObservationViewFormatter.getColorId(observation.getMucus()),
                    R.color.colorPrimaryDark);
        }
        view.setCervixFirmnessText(ObservationViewFormatter.getTextId(observation.getCervixFirmness()));
        view.setCervixHeightText(ObservationViewFormatter.getTextId(observation.getCervixHeight()));
        view.setCervixOpennessText(ObservationViewFormatter.getTextId(observation.getCervixOpenness()));
        view.setMucusText(ObservationViewFormatter.getTextId(observation.getMucus()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
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
        if (!Double.isNaN(temperature)) {
            temperature = ((int) (temperature * 10 + 0.5)) / 10.0f;
        }
        return temperature;
    }
}

