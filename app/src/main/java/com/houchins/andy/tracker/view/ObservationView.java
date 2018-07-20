package com.houchins.andy.tracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.houchins.andy.tracker.R;

public class ObservationView extends FrameLayout {
    private static final double DEFAULT_TEMPERATURE = Double.NaN;
    private boolean showTemperature;
    private boolean showCervix;
    private boolean showMucus;
    private double startTemperature;
    private double middleTemperature;
    private double endTemperature;
    private double maximumTemperature;
    private double minimumTemperature;
    private LineGraphSegmentView temperatureGraph;
    private TextView cervixFirmness;
    private TextView cervixHeight;
    private TextView cervixOpenness;
    private TextView mucus;

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
        temperatureGraph = (LineGraphSegmentView) findViewById(R.id.temperature_graph);
        cervixFirmness = (TextView) findViewById(R.id.cervix_firmness);
        cervixHeight = (TextView) findViewById(R.id.cervix_height);
        cervixOpenness = (TextView) findViewById(R.id.cervix_openness);
        mucus = (TextView) findViewById(R.id.mucus);
        setTemperature(DEFAULT_TEMPERATURE);
        setTemperatureRange(DEFAULT_TEMPERATURE, DEFAULT_TEMPERATURE);
    }

    public void setTemperature(double temperature) {
        setTemperature(temperature, temperature, temperature);
    }

    public void setTemperature(double startTemperature, double middleTemperature, double endTemperature) {
        this.startTemperature = startTemperature;
        this.middleTemperature = middleTemperature;
        this.endTemperature = endTemperature;
        updateTemperatureGraph();
    }

    public void setTemperatureRange(double minimumTemperature, double maximumTemperature) {
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        updateTemperatureGraph();
    }

    private void updateTemperatureGraph() {
        float segmentStart = LineGraphSegmentView.SEGMENT_UNUSED;
        float segmentMiddle = LineGraphSegmentView.SEGMENT_UNUSED;
        float segmentEnd = LineGraphSegmentView.SEGMENT_UNUSED;

        if (!Double.isNaN(minimumTemperature) && !Double.isNaN(maximumTemperature)) {
            if (!Double.isNaN(startTemperature)) {
                segmentStart = getSegmentValue(minimumTemperature, maximumTemperature, startTemperature);
            }
            if (!Double.isNaN(middleTemperature)) {
                segmentMiddle = getSegmentValue(minimumTemperature, maximumTemperature, middleTemperature);
            }
            if (!Double.isNaN(endTemperature)) {
                segmentEnd = getSegmentValue(minimumTemperature, maximumTemperature, endTemperature);
            }
        }

        temperatureGraph.setSegmentStart(segmentStart);
        temperatureGraph.setSegmentMiddle(segmentMiddle);
        temperatureGraph.setSegmentEnd(segmentEnd);
    }

    private float getSegmentValue(double min, double max, double value) {
        float segmentValue = LineGraphSegmentView.SEGMENT_UNUSED;
        if (!(min > max) && !(value < min) && !(value > max)) {
            segmentValue = (float) ((value - min) / (max - min));
        }
        return segmentValue;
    }
}
