package com.houchins.andy.tracker.view;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.CervixFirmness;
import com.houchins.andy.tracker.model.CervixHeight;
import com.houchins.andy.tracker.model.CervixOpenness;
import com.houchins.andy.tracker.model.Mucus;

import java.util.HashMap;
import java.util.Map;

public class ObservationViewFormatter {

    private static final Map<CervixFirmness, FormatSet> cervixFirmnessMap = new HashMap<>();
    private static final Map<CervixHeight, FormatSet> cervixHeightMap = new HashMap<>();
    private static final Map<CervixOpenness, FormatSet> cervixOpennessMap = new HashMap<>();
    private static final Map<Mucus, FormatSet> mucusMap = new HashMap<>();

    static {
        cervixFirmnessMap.put(CervixFirmness.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        cervixFirmnessMap.put(CervixFirmness.FIRM, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_firmness_firm));
        cervixFirmnessMap.put(CervixFirmness.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_firmness_medium));
        cervixFirmnessMap.put(CervixFirmness.SOFT, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.cervix_firmness_soft));

        cervixHeightMap.put(CervixHeight.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        cervixHeightMap.put(CervixHeight.LOW, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_height_low));
        cervixHeightMap.put(CervixHeight.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_height_medium));
        cervixHeightMap.put(CervixHeight.HIGH, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.cervix_height_high));

        cervixOpennessMap.put(CervixOpenness.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        cervixOpennessMap.put(CervixOpenness.CLOSED, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_openness_closed));
        cervixOpennessMap.put(CervixOpenness.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_openness_medium));
        cervixOpennessMap.put(CervixOpenness.OPEN, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.cervix_openness_open));

        mucusMap.put(Mucus.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        mucusMap.put(Mucus.DRY, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.mucus_dry));
        mucusMap.put(Mucus.M, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.mucus_m));
        mucusMap.put(Mucus.EWM, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.mucus_ewm));
    }

    public static int getTextId(CervixFirmness cervixFirmness) {
        return cervixFirmnessMap.get(cervixFirmness).getTextId();
    }

    public static int getTextId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getTextId();
    }

    public static int getTextId(CervixOpenness cervixOpenness) {
        return cervixOpennessMap.get(cervixOpenness).getTextId();
    }

    public static int getTextId(Mucus mucus) {
        return mucusMap.get(mucus).getTextId();
    }

    public static int getColorId(CervixFirmness cervixFirmness) {
        return cervixFirmnessMap.get(cervixFirmness).getColorId();
    }

    public static int getColorId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getColorId();
    }

    public static int getColorId(CervixOpenness cervixOpenness) {
        return cervixOpennessMap.get(cervixOpenness).getColorId();
    }

    public static int getColorId(Mucus mucus) {
        return mucusMap.get(mucus).getColorId();
    }

    public static int getSelectedColorId(CervixFirmness cervixFirmness) {
        return cervixFirmnessMap.get(cervixFirmness).getSelectedColorId();
    }

    public static int getSelectedColorId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getSelectedColorId();
    }

    public static int getSelectedColorId(CervixOpenness cervixOpenness) {
        return cervixOpennessMap.get(cervixOpenness).getSelectedColorId();
    }

    public static int getSelectedColorId(Mucus mucus) {
        return mucusMap.get(mucus).getSelectedColorId();
    }

    private static class FormatSet {
        private int colorId;
        private int selectedColorId;
        private int textId;

        FormatSet(int colorId, int selectedColorId, int textId) {
            this.colorId = colorId;
            this.selectedColorId = selectedColorId;
            this.textId = textId;
        }

        int getColorId() {
            return colorId;
        }

        int getSelectedColorId() {
            return selectedColorId;
        }

        int getTextId() {
            return textId;
        }
    }
}
