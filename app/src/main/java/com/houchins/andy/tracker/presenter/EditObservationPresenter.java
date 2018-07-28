package com.houchins.andy.tracker.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.CervixHeight;
import com.houchins.andy.tracker.model.CervixShape;
import com.houchins.andy.tracker.model.CervixTexture;
import com.houchins.andy.tracker.model.Mucus;
import com.houchins.andy.tracker.model.Observation;
import com.houchins.andy.tracker.store.IObservationStore;
import com.houchins.andy.tracker.view.ObservationViewFormatter;

import java.text.NumberFormat;
import java.text.ParseException;

import static com.houchins.andy.tracker.model.Observation.INVALID_TEMPERATURE;

public class EditObservationPresenter implements IPresenter {

    private static final String LOG_TAG = "EditObservationPresenter";
    private View view;

    private EditText temperatureView;
    private ToggleButton cervixTextureFirmView;
    private ToggleButton cervixTextureMediumView;
    private ToggleButton cervixTextureSoftView;
    private ToggleButton cervixHeightLowView;
    private ToggleButton cervixHeightMediumView;
    private ToggleButton cervixHeightHighView;
    private ToggleButton cervixShapeClosedView;
    private ToggleButton cervixShapeMediumView;
    private ToggleButton cervixShapeOpenView;
    private ToggleButton mucusDryView;
    private ToggleButton mucusMView;
    private ToggleButton mucusEwmView;
    private IObservationStore observationStore;
    private int daysSinceEpoch;
    private Observation observation;

    public EditObservationPresenter(IObservationStore observationStore, int daysSinceEpoch) {
        this.observationStore = observationStore;
        this.daysSinceEpoch = daysSinceEpoch;
        observation = observationStore.getObservation(daysSinceEpoch);
    }

    @Override
    public View getView(LayoutInflater inflater, Context context) {
        if (view == null) {
            view = inflater.inflate(R.layout.edit_observation_activity, null);
            initialize();
        }
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cervix_texture_firm:
                setCervixTexture(CervixTexture.FIRM);
                break;
            case R.id.cervix_texture_medium:
                setCervixTexture(CervixTexture.MEDIUM);
                break;
            case R.id.cervix_texture_soft:
                setCervixTexture(CervixTexture.SOFT);
                break;
            case R.id.cervix_height_low:
                setCervixHeight(CervixHeight.LOW);
                break;
            case R.id.cervix_height_medium:
                setCervixHeight(CervixHeight.MEDIUM);
                break;
            case R.id.cervix_height_high:
                setCervixHeight(CervixHeight.HIGH);
                break;
            case R.id.cervix_shape_closed:
                setCervixShape(CervixShape.CLOSED);
                break;
            case R.id.cervix_shape_medium:
                setCervixShape(CervixShape.MEDIUM);
                break;
            case R.id.cervix_shape_open:
                setCervixShape(CervixShape.OPEN);
                break;
            case R.id.mucus_dry:
                setMucus(Mucus.DRY);
                break;
            case R.id.mucus_m:
                setMucus(Mucus.M);
                break;
            case R.id.mucus_ewm:
                setMucus(Mucus.EWM);
                break;
        }
    }

    private void initialize() {
        temperatureView = view.findViewById(R.id.temperature_value);
        cervixTextureFirmView = view.findViewById(R.id.cervix_texture_firm);
        cervixTextureMediumView = view.findViewById(R.id.cervix_texture_medium);
        cervixTextureSoftView = view.findViewById(R.id.cervix_texture_soft);
        cervixHeightLowView = view.findViewById(R.id.cervix_height_low);
        cervixHeightMediumView = view.findViewById(R.id.cervix_height_medium);
        cervixHeightHighView = view.findViewById(R.id.cervix_height_high);
        cervixShapeClosedView = view.findViewById(R.id.cervix_shape_closed);
        cervixShapeMediumView = view.findViewById(R.id.cervix_shape_medium);
        cervixShapeOpenView = view.findViewById(R.id.cervix_shape_open);
        mucusDryView = view.findViewById(R.id.mucus_dry);
        mucusMView = view.findViewById(R.id.mucus_m);
        mucusEwmView = view.findViewById(R.id.mucus_ewm);
        updateTemperature();
        updateCervixTextureViews();
        updateCervixHeightViews();
        updateCervixShapeViews();
        updateMucusViews();

        temperatureView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveTemperatureFromText(editable.toString());
            }
        });
    }

    private void saveTemperatureFromText(String text) {
        NumberFormat format = NumberFormat.getNumberInstance();
        double temperature;
        try {
            temperature = format.parse(text).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            temperature = INVALID_TEMPERATURE;
        }
        observation.setTemperature(temperature);
        observationStore.saveObservation(observation);
    }

    private void updateTemperature() {
        temperatureView.setText(ObservationViewFormatter.getTemperatureText(view.getResources(),
                observation.getTemperature()));
    }

    private void setCervixTexture(CervixTexture texture) {
        observation.setCervixTexture(texture);
        observationStore.saveObservation(observation);
        updateCervixTextureViews();
    }

    private void setCervixHeight(CervixHeight height) {
        observation.setCervixHeight(height);
        observationStore.saveObservation(observation);
        updateCervixHeightViews();
    }

    private void setCervixShape(CervixShape shape) {
        observation.setCervixShape(shape);
        observationStore.saveObservation(observation);
        updateCervixShapeViews();
    }

    private void setMucus(Mucus mucus) {
        observation.setMucus(mucus);
        observationStore.saveObservation(observation);
        updateMucusViews();
    }

    private void updateCervixTextureViews() {
        cervixTextureFirmView.setChecked(observation.getCervixTexture() == CervixTexture.FIRM);
        cervixTextureMediumView.setChecked(observation.getCervixTexture() == CervixTexture.MEDIUM);
        cervixTextureSoftView.setChecked(observation.getCervixTexture() == CervixTexture.SOFT);
    }

    private void updateCervixHeightViews() {
        cervixHeightLowView.setChecked(observation.getCervixHeight() == CervixHeight.LOW);
        cervixHeightMediumView.setChecked(observation.getCervixHeight() == CervixHeight.MEDIUM);
        cervixHeightHighView.setChecked(observation.getCervixHeight() == CervixHeight.HIGH);
    }

    private void updateCervixShapeViews() {
        cervixShapeClosedView.setChecked(observation.getCervixShape() == CervixShape.CLOSED);
        cervixShapeMediumView.setChecked(observation.getCervixShape() == CervixShape.MEDIUM);
        cervixShapeOpenView.setChecked(observation.getCervixShape() == CervixShape.OPEN);
    }

    private void updateMucusViews() {
        mucusDryView.setChecked(observation.getMucus() == Mucus.DRY);
        mucusMView.setChecked(observation.getMucus() == Mucus.M);
        mucusEwmView.setChecked(observation.getMucus() == Mucus.EWM);
    }
}
