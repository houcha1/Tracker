package com.houchins.andy.tracker.view;

import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.model.CervixTexture;
import com.houchins.andy.tracker.model.CervixHeight;
import com.houchins.andy.tracker.model.CervixShape;
import com.houchins.andy.tracker.model.Mucus;

import java.util.HashMap;
import java.util.Map;

public class ObservationViewFormatter {

    private static final Map<CervixTexture, FormatSet> CervixTextureMap = new HashMap<>();
    private static final Map<CervixHeight, FormatSet> cervixHeightMap = new HashMap<>();
    private static final Map<CervixShape, FormatSet> CervixShapeMap = new HashMap<>();
    private static final Map<Mucus, FormatSet> mucusMap = new HashMap<>();

    static {
        CervixTextureMap.put(CervixTexture.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        CervixTextureMap.put(CervixTexture.FIRM, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_firmness_firm));
        CervixTextureMap.put(CervixTexture.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_firmness_medium));
        CervixTextureMap.put(CervixTexture.SOFT, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.cervix_firmness_soft));

        cervixHeightMap.put(CervixHeight.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        cervixHeightMap.put(CervixHeight.LOW, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_height_low));
        cervixHeightMap.put(CervixHeight.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_height_medium));
        cervixHeightMap.put(CervixHeight.HIGH, new FormatSet(
                R.color.colorPrimaryLevel7, R.color.colorAccentLevel7, R.string.cervix_height_high));

        CervixShapeMap.put(CervixShape.NONE, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.observation_none));
        CervixShapeMap.put(CervixShape.CLOSED, new FormatSet(
                R.color.colorTransparent, R.color.colorAccentLevel1, R.string.cervix_openness_closed));
        CervixShapeMap.put(CervixShape.MEDIUM, new FormatSet(
                R.color.colorPrimaryLevel4, R.color.colorAccentLevel4, R.string.cervix_openness_medium));
        CervixShapeMap.put(CervixShape.OPEN, new FormatSet(
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

    public static int getTextId(CervixTexture cervixTexture) {
        return CervixTextureMap.get(cervixTexture).getTextId();
    }

    public static int getTextId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getTextId();
    }

    public static int getTextId(CervixShape cervixShape) {
        return CervixShapeMap.get(cervixShape).getTextId();
    }

    public static int getTextId(Mucus mucus) {
        return mucusMap.get(mucus).getTextId();
    }

    public static int getColorId(CervixTexture cervixTexture) {
        return CervixTextureMap.get(cervixTexture).getColorId();
    }

    public static int getColorId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getColorId();
    }

    public static int getColorId(CervixShape cervixShape) {
        return CervixShapeMap.get(cervixShape).getColorId();
    }

    public static int getColorId(Mucus mucus) {
        return mucusMap.get(mucus).getColorId();
    }

    public static int getSelectedColorId(CervixTexture cervixTexture) {
        return CervixTextureMap.get(cervixTexture).getSelectedColorId();
    }

    public static int getSelectedColorId(CervixHeight cervixHeight) {
        return cervixHeightMap.get(cervixHeight).getSelectedColorId();
    }

    public static int getSelectedColorId(CervixShape cervixShape) {
        return CervixShapeMap.get(cervixShape).getSelectedColorId();
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
