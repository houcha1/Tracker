package com.houchins.andy.tracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.houchins.andy.tracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ObservationView extends FrameLayout {
    private static final double DEFAULT_TEMPERATURE = Double.NaN;
    private boolean showTemperature;
    private boolean showCervix;
    private boolean showMucus;
    private double previousTemperature;
    private double currentTemperature;
    private double nextTemperature;

    private TemperatureGraphSegmentView temperatureGraph;
    private List<View> temperatureContainers = new ArrayList<>();
    private List<View> cervixContainers = new ArrayList<>();
    private List<View> mucusContainers = new ArrayList<>();
    private List<View> gridViews = new ArrayList<>();
    private TextView temperatureView;
    private TextView cervixFirmnessView;
    private TextView cervixHeightView;
    private TextView cervixOpennessView;
    private TextView mucusView;

    public ObservationView(@NonNull Context context) {
        this(context, null);
    }

    public ObservationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ObservationView,
                0, 0);
        try {
            showTemperature = a.getBoolean(R.styleable.ObservationView_showTemperature, true);
            showCervix = a.getBoolean(R.styleable.ObservationView_showCervix, true);
            showMucus = a.getBoolean(R.styleable.ObservationView_showMucus, true);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.observation_data, this);
        gridViews.add(findViewById(R.id.observation_root));
        temperatureGraph = findViewById(R.id.temperature_graph);
        temperatureContainers.add(findViewById(R.id.temperature_graph_container));
        temperatureContainers.add(findViewById(R.id.temperature_container));
        cervixContainers.add(findViewById(R.id.cervix_firmness_container));
        cervixContainers.add(findViewById(R.id.cervix_height_container));
        cervixContainers.add(findViewById(R.id.cervix_openness_container));
        mucusContainers.add(findViewById(R.id.mucus_container));
        temperatureView = findViewById(R.id.temperature);
        cervixFirmnessView = findViewById(R.id.cervix_firmness);
        cervixHeightView = findViewById(R.id.cervix_height);
        cervixOpennessView = findViewById(R.id.cervix_openness);
        mucusView = findViewById(R.id.mucus);

        previousTemperature = DEFAULT_TEMPERATURE;
        currentTemperature = DEFAULT_TEMPERATURE;
        nextTemperature = DEFAULT_TEMPERATURE;
        updateTemperatureGraph();
    }

    public void setObservationVisibility(boolean showTemperature, boolean showCervix, boolean showMucus) {
        this.showTemperature = showTemperature;
        this.showCervix = showCervix;
        this.showMucus = showMucus;
        temperatureGraph.setVisibility(this.showTemperature ? VISIBLE : GONE);
        for (View v : temperatureContainers) {
            v.setVisibility(this.showTemperature ? VISIBLE : GONE);
        }
        for (View v : cervixContainers) {
            v.setVisibility(this.showCervix ? VISIBLE : GONE);
        }
        for (View v : mucusContainers) {
            v.setVisibility(this.showMucus ? VISIBLE : GONE);
        }
    }

    public void setTemperature(double previousTemperature, double currentTemperature, double nextTemperature) {
        this.previousTemperature = previousTemperature;
        this.currentTemperature = currentTemperature;
        this.nextTemperature = nextTemperature;
        temperatureView.setText(String.format(Locale.getDefault(),
                getResources().getString(R.string.temperature_format), currentTemperature));
        updateTemperatureGraph();
    }

    public void setCervixFirmnessText(int resourceId) {
        cervixFirmnessView.setText(resourceId);
    }

    public void setCervixHeightText(int resourceId) {
        cervixHeightView.setText(resourceId);
    }

    public void setCervixOpennessText(int resourceId) {
        cervixOpennessView.setText(resourceId);
    }

    public void setMucusText(int resourceId) {
        mucusView.setText(resourceId);
    }

    public void setCervixFirmnessColor(int backgroundResourceId, int foregroundResourceId) {
        cervixFirmnessView.setBackgroundColor(getResources().getColor(backgroundResourceId));
        cervixFirmnessView.setTextColor(getResources().getColor(foregroundResourceId));
    }

    public void setCervixHeightColor(int backgroundResourceId, int foregroundResourceId) {
        cervixHeightView.setBackgroundColor(getResources().getColor(backgroundResourceId));
        cervixHeightView.setTextColor(getResources().getColor(foregroundResourceId));
    }

    public void setCervixOpennessColor(int backgroundResourceId, int foregroundResourceId) {
        cervixOpennessView.setBackgroundColor(getResources().getColor(backgroundResourceId));
        cervixOpennessView.setTextColor(getResources().getColor(foregroundResourceId));
    }

    public void setMucusColor(int backgroundResourceId, int foregroundResourceId) {
        mucusView.setBackgroundColor(getResources().getColor(backgroundResourceId));
        mucusView.setTextColor(getResources().getColor(foregroundResourceId));
    }

    public void setGraphColor(int gridResourceId, int lineResourceId, int shadowResourceId, int backgroundResourceId) {
        int color = getResources().getColor(gridResourceId);
        int color2 = getResources().getColor(lineResourceId);
        int color3 = getResources().getColor(shadowResourceId);
        int color4 = getResources().getColor(backgroundResourceId);

        // set grid color
        for (View v : gridViews) {
            v.setBackgroundColor(color);
        }
        temperatureGraph.setColor(color, color2, color3);

        // set background (fill) color
        for (View v : temperatureContainers) {
            v.setBackgroundColor(color4);
        }
        for (View v : cervixContainers) {
            v.setBackgroundColor(color4);
        }
        for (View v : mucusContainers) {
            v.setBackgroundColor(color4);
        }
        setBackgroundColor(color4);
    }

    private void updateTemperatureGraph() {
        temperatureGraph.setTemperature(previousTemperature, currentTemperature, nextTemperature);
    }
}
