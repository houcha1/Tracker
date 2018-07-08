package com.houchins.andy.tracker.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Represents the recorded observations for a single day
 */

@Table(database = AppDatabase.class)
public class ObservationRecord extends BaseModel {

    @PrimaryKey
    private int daysSinceEpoch;

    @Column
    private float temperature;

    @Column
    int flags;

    public void setDaysSinceEpoch(int daysSinceEpoch) {
        this.daysSinceEpoch = daysSinceEpoch;
    }

    public int getDaysSinceEpoch() {
        return daysSinceEpoch;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getFlags() {
        return flags;
    }
}
