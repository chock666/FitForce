package com.example.fitforce;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BarChartView extends View {

    private Paint paint;
    private float[] data = {100, 200, 300}; // Example data for bars

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate bar width and gap
        int barWidth = getWidth() / (data.length * 2);
        int maxValue = getMaxValue(data);
        int bottom = getHeight() - 20;

        // Draw bars
        for (int i = 0; i < data.length; i++) {
            float left = i * barWidth * 2 + barWidth / 2;
            float right = left + barWidth;
            float top = bottom - (data[i] / maxValue) * bottom;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private int getMaxValue(float[] data) {
        int maxValue = 0;
        for (float value : data) {
            if (value > maxValue) {
                maxValue = (int) value;
            }
        }
        return maxValue;
    }

    public void setData(float[] data) {
        this.data = data;
        invalidate();
    }
}

