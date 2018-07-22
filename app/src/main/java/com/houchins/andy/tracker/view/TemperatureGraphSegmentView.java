package com.houchins.andy.tracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.houchins.andy.tracker.R;

public class TemperatureGraphSegmentView extends View {
    public static final float SEGMENT_UNUSED = -1.0f;
    private static final double MAXIMUM_TEMPERATURE = 99.6;
    private static final double MINIMUM_TEMPERATURE = 96.6;
    private static final int LINE_COLOR_DEFAULT_ID = R.color.colorPrimaryDark;
    private static final int SHADOW_COLOR_DEFAULT_ID = R.color.colorShadow;
    private static final int LINE_WIDTH_DEFAULT_ID = R.dimen.default_line_width;
    private static final int CIRCLE_RADIUS_DEFAULT_ID = R.dimen.default_circle_radius;
    private static final int SHADOW_WIDTH_DEFAULT_ID = R.dimen.default_line_width;
    private static final int SHADOW_OFFSET_Y_DEFAULT_ID = R.dimen.default_shadow_offset_y;
    private static final int SHADOW_OFFSET_X_DEFAULT_ID = R.dimen.default_shadow_offset_x;
    private static final int GRID_COLOR_DEFAULT_ID = R.color.colorGridLine;
    private static final int GRID_WIDTH_DEFAULT_ID = R.dimen.grid_line_width;
    private static final float ROUNDING_FACTOR = 0.5f;

    // line
    private int lineColor;
    private float lineWidth;
    private Paint linePaint;

    // shadow
    private int shadowColor;
    private float shadowWidth;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Paint shadowPaint;

    // temperatures
    private double previousTemperature;
    private double currentTemperature;
    private double nextTemperature;

    // circle
    private float circleRadius;

    // Grid
    private int gridColor;
    private float gridWidth;
    private Paint gridPaint;
    private Paint gridThickPaint;

    public TemperatureGraphSegmentView(Context context) {
        this(context, null);
    }

    public TemperatureGraphSegmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureGraphSegmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TemperatureGraphSegmentView,
                0, 0);
        try {
            lineColor = a.getColor(R.styleable.TemperatureGraphSegmentView_lineColor,
                    getResources().getColor(LINE_COLOR_DEFAULT_ID));
            lineWidth = a.getDimension(R.styleable.TemperatureGraphSegmentView_lineWidth,
                    getResources().getDimension(LINE_WIDTH_DEFAULT_ID));
            circleRadius = a.getDimension(R.styleable.TemperatureGraphSegmentView_circleRadius,
                    getResources().getDimension(CIRCLE_RADIUS_DEFAULT_ID));
            shadowColor = a.getColor(R.styleable.TemperatureGraphSegmentView_shadowColor,
                    getResources().getColor(SHADOW_COLOR_DEFAULT_ID));
            shadowWidth = a.getDimension(R.styleable.TemperatureGraphSegmentView_shadowWidth,
                    getResources().getDimension(SHADOW_WIDTH_DEFAULT_ID));
            shadowOffsetX = a.getDimension(R.styleable.TemperatureGraphSegmentView_shadowOffsetX,
                    getResources().getDimension(SHADOW_OFFSET_X_DEFAULT_ID));
            shadowOffsetY = a.getDimension(R.styleable.TemperatureGraphSegmentView_shadowOffsetY,
                    getResources().getDimension(SHADOW_OFFSET_Y_DEFAULT_ID));
            gridColor = a.getColor(R.styleable.TemperatureGraphSegmentView_gridColor,
                    getResources().getColor(GRID_COLOR_DEFAULT_ID));
            gridWidth = a.getDimension(R.styleable.TemperatureGraphSegmentView_gridWidth,
                    getResources().getDimension(GRID_WIDTH_DEFAULT_ID));
        } finally {
            a.recycle();
        }
        init();
    }

    public void setTemperature(double previousTemperature, double currentTemperature, double nextTemperature) {
        this.previousTemperature = previousTemperature;
        this.currentTemperature = currentTemperature;
        this.nextTemperature = nextTemperature;
        invalidate();
    }

    public void setColor(int gridColor, int lineColor, int shadowColor) {
        this.gridColor = gridColor;
        this.lineColor = lineColor;
        this.shadowColor = shadowColor;
        updatePaints();
        invalidate();
    }

    private void init() {
        updatePaints();
    }

    private void updatePaints() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(shadowColor);
        shadowPaint.setStrokeWidth(shadowWidth);
        shadowPaint.setStrokeCap(Paint.Cap.ROUND);
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(gridWidth);
        gridPaint.setStrokeCap(Paint.Cap.ROUND);
        gridThickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridThickPaint.setColor(gridColor);
        gridThickPaint.setStrokeWidth(gridWidth * 2);
        gridThickPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean rtl = getLayoutDirection() == LAYOUT_DIRECTION_RTL;
        final int w = getMeasuredWidth();
        final int x2 = (int) ((w + ROUNDING_FACTOR) / 2.0f);
        final int x1 = rtl ? x2 + w : x2 - w;
        final int x3 = rtl ? x2 - w : x2 + w;
        final int y2 = getYValue(currentTemperature);
        final int y1 = getYValue(previousTemperature);
        final int y3 = getYValue(nextTemperature);
        final int offsetX = (int) (shadowOffsetX + ROUNDING_FACTOR);
        final int offsetY = (int) (shadowOffsetY + ROUNDING_FACTOR);

        boolean circle = !Double.isNaN(currentTemperature);
        boolean segment1to2 = !Double.isNaN(previousTemperature) && !Double.isNaN(currentTemperature);
        boolean segment2to3 = !Double.isNaN(currentTemperature) && !Double.isNaN(nextTemperature);

        // Draw grid
        drawGrid(canvas);

        // draw shadow
        if (segment1to2) {
            canvas.drawLine(x1 + offsetX, y1 + offsetY, x2 + offsetX, y2 + offsetY, shadowPaint);
        }
        if (segment2to3) {
            canvas.drawLine(x2 + offsetX, y2 + offsetY, x3 + offsetX, y3 + offsetY, shadowPaint);
        }
        if (circle) {
            canvas.drawCircle(x2 + offsetX, y2 + offsetY, circleRadius, shadowPaint);
        }

        // draw line
        if (segment1to2) {
            canvas.drawLine(x1, y1, x2, y2, linePaint);
        }
        if (segment2to3) {
            canvas.drawLine(x2, y2, x3, y3, linePaint);
        }
        if (circle) {
            canvas.drawCircle(x2, y2, circleRadius, linePaint);
        }
    }

    private int getYValue(double temperature) {
        int h = getMeasuredHeight();
        if (temperature < MINIMUM_TEMPERATURE) {
            temperature = MINIMUM_TEMPERATURE;
        } else if (temperature > MAXIMUM_TEMPERATURE) {
            temperature = MAXIMUM_TEMPERATURE;
        }
        return (int) (((MAXIMUM_TEMPERATURE - temperature) / (MAXIMUM_TEMPERATURE - MINIMUM_TEMPERATURE)) *
                h + ROUNDING_FACTOR);
    }

    private void drawGrid(Canvas canvas) {
        final int w = getMeasuredWidth();
        final int h = getMeasuredHeight();
        float y;
        canvas.drawLine(0, 0, 0, h, gridPaint);
        canvas.drawLine(w, 0, w, h, gridPaint);
        double temperature = MINIMUM_TEMPERATURE;
        for (int i = 0; i <= 15; i++) {
            temperature = MINIMUM_TEMPERATURE + 0.2 * i;
            y = getYValue(temperature);
            if (i % 5 == 2) {
                //draw a thick line
                canvas.drawLine(0, y, w, y, gridThickPaint);
            } else {
                //draw a regular line
                canvas.drawLine(0, y, w, y, gridPaint);
            }
        }
    }
}
