package com.houchins.andy.tracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.houchins.andy.tracker.R;

public class LineGraphSegmentView extends View {
    public static final float SEGMENT_UNUSED = -1.0f;

    private static final int LINE_COLOR_DEFAULT_ID = R.color.colorPrimaryDark;
    private static final int SHADOW_COLOR_DEFAULT_ID = R.color.colorShadow;
    private static final int LINE_WIDTH_DEFAULT_ID = R.dimen.default_line_width;
    private static final int CIRCLE_RADIUS_DEFAULT_ID = R.dimen.default_circle_radius;
    private static final int SHADOW_WIDTH_DEFAULT_ID = R.dimen.default_line_width;
    private static final int SHADOW_OFFSET_Y_DEFAULT_ID = R.dimen.default_shadow_offset_y;
    private static final int SHADOW_OFFSET_X_DEFAULT_ID = R.dimen.default_shadow_offset_x;
    private static final float SEGMENT_MINIMUM = 0.0f;
    private static final float SEGMENT_MAXIMUM = 1.0f;
    private static final float SEGMENT_DEFAULT = 0.5f;
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

    // segments
    private float segmentStart;
    private float segmentMiddle;
    private float segmentEnd;

    // circle
    private float circleRadius;

    public LineGraphSegmentView(Context context) {
        this(context, null);
    }

    public LineGraphSegmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineGraphSegmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LineGraphSegmentView,
                0, 0);
        try {
            setSegmentStart(a.getFloat(R.styleable.LineGraphSegmentView_segmentStart, SEGMENT_DEFAULT));
            setSegmentMiddle(a.getFloat(R.styleable.LineGraphSegmentView_segmentMiddle, SEGMENT_DEFAULT));
            setSegmentEnd(a.getFloat(R.styleable.LineGraphSegmentView_segmentEnd, SEGMENT_DEFAULT));
            lineColor = a.getColor(R.styleable.LineGraphSegmentView_lineColor,
                    getResources().getColor(LINE_COLOR_DEFAULT_ID));
            lineWidth = a.getDimension(R.styleable.LineGraphSegmentView_lineWidth,
                    getResources().getDimension(LINE_WIDTH_DEFAULT_ID));
            circleRadius = a.getDimension(R.styleable.LineGraphSegmentView_circleRadius,
                    getResources().getDimension(CIRCLE_RADIUS_DEFAULT_ID));
            shadowColor = a.getColor(R.styleable.LineGraphSegmentView_shadowColor,
                    getResources().getColor(SHADOW_COLOR_DEFAULT_ID));
            shadowWidth = a.getDimension(R.styleable.LineGraphSegmentView_shadowWidth,
                    getResources().getDimension(SHADOW_WIDTH_DEFAULT_ID));
            shadowOffsetX = a.getDimension(R.styleable.LineGraphSegmentView_shadowOffsetX,
                    getResources().getDimension(SHADOW_OFFSET_X_DEFAULT_ID));
            shadowOffsetY = a.getDimension(R.styleable.LineGraphSegmentView_shadowOffsetY,
                    getResources().getDimension(SHADOW_OFFSET_Y_DEFAULT_ID));
        } finally {
            a.recycle();
        }
        init();
    }

    public void setSegmentStart(float value) {
        segmentStart = coerceSegmentValue(value);
    }

    public void setSegmentMiddle(float value) {
        segmentMiddle = coerceSegmentValue(value);
    }

    public void setSegmentEnd(float value) {
        segmentEnd = coerceSegmentValue(value);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean rtl = getLayoutDirection() == LAYOUT_DIRECTION_RTL;
        final int w = getMeasuredWidth();
        final int h = getMeasuredHeight();
        final int y1 = (int) (h * segmentStart + ROUNDING_FACTOR);
        final int y2 = (int) (h * segmentMiddle + ROUNDING_FACTOR);
        final int y3 = (int) (h * segmentEnd + ROUNDING_FACTOR);
        final int x1 = rtl ? w : 0;
        final int x2 = (int) ((w + ROUNDING_FACTOR) / 2.0f);
        final int x3 = rtl ? 0 : w;
        final int offsetX = (int) (shadowOffsetX + ROUNDING_FACTOR);
        final int offsetY = (int) (shadowOffsetY + ROUNDING_FACTOR);

        boolean circle = (segmentMiddle != SEGMENT_UNUSED);
        boolean segment1to2 = (segmentStart != SEGMENT_UNUSED) && (segmentMiddle != SEGMENT_UNUSED);
        boolean segment2to3 = (segmentMiddle != SEGMENT_UNUSED) && (segmentEnd != SEGMENT_UNUSED);
        boolean segment1to3 = (segmentStart != SEGMENT_UNUSED) &&
                (segmentMiddle == SEGMENT_UNUSED) && (segmentEnd != SEGMENT_UNUSED);

        // draw shadow
        if (segment1to2) {
            canvas.drawLine(x1 + offsetX, y1 + offsetY, x2 + offsetX, y2 + offsetY, shadowPaint);
        }
        if (segment2to3) {
            canvas.drawLine(x2 + offsetX, y2 + offsetY, x3 + offsetX, y3 + offsetY, shadowPaint);
        }
        if (segment1to3) {
            canvas.drawLine(x1 + offsetX, y1 + offsetY, x3 + offsetX, y3 + offsetY, shadowPaint);
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
        if (segment1to3) {
            canvas.drawLine(x1, y1, x3, y3, linePaint);
        }
        if (circle) {
            canvas.drawCircle(x2, y2, circleRadius, linePaint);
        }
    }

    private float coerceSegmentValue(float value) {
        if (value != SEGMENT_UNUSED) {
            if (value < SEGMENT_MINIMUM) {
                value = SEGMENT_MINIMUM;
            } else if (value > SEGMENT_MAXIMUM) {
                value = SEGMENT_MAXIMUM;
            }
        }
        return value;
    }

}
