package com.houchins.andy.tracker.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.houchins.andy.tracker.model.Observation;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder> {
    private static final String TAG = "ObservationAdapter";

    private List<Observation> dataSet;
    private static final double MAXIMUM_TEMPERATURE = 99.0;
    private static final double MINIMUM_TEMPERATURE = 95.0;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ObservationView view;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            view = (ObservationView) v;
        }

        public ObservationView getView() {
            return view;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet the data to populate views to be used by RecyclerView.
     */
    public ObservationAdapter(List<Observation> dataSet) {
        this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = new ObservationView(viewGroup.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        double t1;
        double t2;
        double t3;

        t2 = dataSet.get(position).getTemperature();
        t1 = (position - 1 >= 0) ? midpoint(dataSet.get(position - 1).getTemperature(), t2) : Double.NaN;
        t3 = (position + 1 < dataSet.size()) ? midpoint(t2, dataSet.get(position + 1).getTemperature()) : Double.NaN;

        viewHolder.getView().setTemperatureRange(MINIMUM_TEMPERATURE, MAXIMUM_TEMPERATURE);
        viewHolder.getView().setTemperature(t1, t2, t3);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private double midpoint(double d1, double d2) {
        return (d1 + d2) / 2.0;
    }

}

