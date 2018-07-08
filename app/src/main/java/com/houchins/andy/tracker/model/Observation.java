package com.houchins.andy.tracker.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a set of tracking observations.
 */

public class Observation {

    public static final int FLAG_CERVIX_FIRMNESS_MASK = 0x00000003;
    public static final int FLAG_CERVIX_FIRM = 0x00000001;
    public static final int FLAG_CERVIX_FIRM_SOFT = 0x00000003;
    public static final int FLAG_CERVIX_SOFT = 0x00000002;

    public static final int FLAG_CERVIX_HEIGHT_MASK = 0x0000000C;
    public static final int FLAG_CERVIX_HIGH = 0x00000004;
    public static final int FLAG_CERVIX_HIGH_LOW = 0x0000000C;
    public static final int FLAG_CERVIX_LOW = 0x00000008;

    public static final int FLAG_CERVIX_OPENNESS_MASK = 0x00000030;
    public static final int FLAG_CERVIX_OPEN = 0x00000010;
    public static final int FLAG_CERVIX_OPEN_CLOSED = 0x00000030;
    public static final int FLAG_CERVIX_CLOSED = 0x00000020;

    public static final int FLAG_MUCUS_MASK = 0x000000C0;
    public static final int FLAG_MUCUS_DRY = 0x00000040;
    public static final int FLAG_MUCUS_M = 0x000000C0;
    public static final int FLAG_MUCUS_EWM = 0x00000080;

    public static final int FLAG_FLOW_MASK = 0x00000700;
    public static final int FLAG_FLOW_NONE = 0x00000000;
    public static final int FLAG_FLOW_EXTRA_LIGHT = 0x00000100;
    public static final int FLAG_FLOW_LIGHT = 0x00000200;
    public static final int FLAG_FLOW_MEDIUM_LIGHT = 0x00000300;
    public static final int FLAG_FLOW_MEDIUM = 0x00000400;
    public static final int FLAG_FLOW_MEDIUM_HEAVY = 0x00000500;
    public static final int FLAG_FLOW_HEAVY = 0x00000600;
    public static final int FLAG_FLOW_EXTRA_HEAVY = 0x00000700;

    public static final int FLAG_ALL_USED = 0x000007FF;

    private Date date;
    private Double temperature;
    private int flags;

    public Observation() {
        reset();
    }

    public Observation(Date date, Double temperature, int flags) {
        reset();
        setDate(date);
        setTemperature(temperature);
        setFlags(flags);
    }

    public void reset() {
        date = null;
        temperature = null;
        flags = 0;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getFlags() {
        return flags;
    }

    public void setCervixHeight(CervixHeight cervixHeight) {
        setFlags((getFlags() & ~FLAG_CERVIX_HEIGHT_MASK) | getFlags(cervixHeight));
    }

    public CervixHeight getCervixHeight() {
        CervixHeight cervixHeight;
        switch (getFlags() & FLAG_CERVIX_HEIGHT_MASK) {
            case FLAG_CERVIX_HIGH:
                cervixHeight = CervixHeight.HIGH;
                break;
            case FLAG_CERVIX_HIGH_LOW:
                cervixHeight = CervixHeight.HIGH_LOW;
                break;
            case FLAG_CERVIX_LOW:
                cervixHeight = CervixHeight.LOW;
                break;
            default:
                cervixHeight = CervixHeight.NONE;
                break;
        }
        return cervixHeight;
    }

    public void setCervixFirmness(CervixFirmness cervixFirmness) {
        setFlags((getFlags() & ~FLAG_CERVIX_FIRMNESS_MASK) | getFlags(cervixFirmness));
    }

    public CervixFirmness getCervixFirmness() {
        CervixFirmness cervixFirmness;
        switch (getFlags() & FLAG_CERVIX_FIRMNESS_MASK) {
            case FLAG_CERVIX_FIRM:
                cervixFirmness = CervixFirmness.FIRM;
                break;
            case FLAG_CERVIX_FIRM_SOFT:
                cervixFirmness = CervixFirmness.FIRM_SOFT;
                break;
            case FLAG_CERVIX_SOFT:
                cervixFirmness = CervixFirmness.SOFT;
                break;
            default:
                cervixFirmness = CervixFirmness.NONE;
                break;
        }
        return cervixFirmness;
    }

    public void setCervixOpenness(CervixOpenness cervixOpenness) {
        setFlags((getFlags() & ~FLAG_CERVIX_OPENNESS_MASK) | getFlags(cervixOpenness));
    }

    public CervixOpenness getCervixOpenness() {
        CervixOpenness cervixOpenness;
        switch (getFlags() & FLAG_CERVIX_OPENNESS_MASK) {
            case FLAG_CERVIX_OPEN:
                cervixOpenness = CervixOpenness.OPEN;
                break;
            case FLAG_CERVIX_OPEN_CLOSED:
                cervixOpenness = CervixOpenness.OPEN_CLOSED;
                break;
            case FLAG_CERVIX_CLOSED:
                cervixOpenness = CervixOpenness.CLOSED;
                break;
            default:
                cervixOpenness = CervixOpenness.NONE;
                break;
        }
        return cervixOpenness;
    }

    public void setMucus(Mucus mucus) {
        setFlags((getFlags() & ~FLAG_MUCUS_MASK) | getFlags(mucus));
    }

    public Mucus getMucus() {
        Mucus mucus;
        switch (getFlags() & FLAG_MUCUS_MASK) {
            case FLAG_MUCUS_DRY:
                mucus = Mucus.DRY;
                break;
            case FLAG_MUCUS_M:
                mucus = Mucus.M;
                break;
            case FLAG_MUCUS_EWM:
                mucus = Mucus.EWM;
                break;
            default:
                mucus = Mucus.NONE;
                break;
        }
        return mucus;
    }

    public void setFlow(Flow flow) {
        setFlags((getFlags() & ~FLAG_FLOW_MASK) | getFlags(flow));
    }

    public Flow getFlow() {
        Flow flow;
        switch (getFlags() & FLAG_FLOW_MASK) {
            case FLAG_FLOW_EXTRA_LIGHT:
                flow = Flow.EXTRA_LIGHT;
                break;
            case FLAG_FLOW_LIGHT:
                flow = Flow.LIGHT;
                break;
            case FLAG_FLOW_MEDIUM_LIGHT:
                flow = Flow.MEDIUM_LIGHT;
                break;
            case FLAG_FLOW_MEDIUM:
                flow = Flow.MEDIUM;
                break;
            case FLAG_FLOW_MEDIUM_HEAVY:
                flow = Flow.MEDIUM_HEAVY;
                break;
            case FLAG_FLOW_HEAVY:
                flow = Flow.HEAVY;
                break;
            case FLAG_FLOW_EXTRA_HEAVY:
                flow = Flow.EXTRA_HEAVY;
                break;
            case FLAG_FLOW_NONE:
            default:
                flow = Flow.NONE;
                break;
        }
        return flow;
    }

    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
        return "(" + f.format(date) + ", " + temperature.toString() +
                ", " + getCervixFirmness() + ", " + getCervixHeight() + ", " + getCervixOpenness() +
                ", " + getMucus() + ", " + getFlow() + ")";
    }

    private int getFlags(CervixHeight cervixHeight) {
        int flags;
        switch (cervixHeight) {
            case HIGH:
                flags = FLAG_CERVIX_HIGH;
                break;
            case HIGH_LOW:
                flags = FLAG_CERVIX_HIGH_LOW;
                break;
            case LOW:
                flags = FLAG_CERVIX_LOW;
                break;
            case NONE:
            default:
                flags = 0;
                break;
        }
        return flags;
    }

    private int getFlags(CervixFirmness cervixFirmness) {
        int flags;
        switch (cervixFirmness) {
            case FIRM:
                flags = FLAG_CERVIX_FIRM;
                break;
            case FIRM_SOFT:
                flags = FLAG_CERVIX_FIRM_SOFT;
                break;
            case SOFT:
                flags = FLAG_CERVIX_SOFT;
                break;
            case NONE:
            default:
                flags = 0;
                break;
        }
        return flags;
    }

    private int getFlags(CervixOpenness cervixOpenness) {
        int flags;
        switch (cervixOpenness) {
            case OPEN:
                flags = FLAG_CERVIX_OPEN;
                break;
            case OPEN_CLOSED:
                flags = FLAG_CERVIX_OPEN_CLOSED;
                break;
            case CLOSED:
                flags = FLAG_CERVIX_CLOSED;
                break;
            case NONE:
            default:
                flags = 0;
                break;
        }
        return flags;
    }

    private int getFlags(Mucus mucus) {
        int flags;
        switch (mucus) {
            case DRY:
                flags = FLAG_MUCUS_DRY;
                break;
            case M:
                flags = FLAG_MUCUS_M;
                break;
            case EWM:
                flags = FLAG_MUCUS_EWM;
                break;
            case NONE:
            default:
                flags = 0;
                break;
        }
        return flags;
    }

    private int getFlags(Flow flow) {
        int flags;
        switch (flow) {
            case HEAVY:
                flags = FLAG_FLOW_HEAVY;
                break;
            case MEDIUM:
                flags = FLAG_FLOW_MEDIUM;
                break;
            case LIGHT:
                flags = FLAG_FLOW_LIGHT;
                break;
            case NONE:
            default:
                flags = 0;
                break;
        }
        return flags;
    }
}
