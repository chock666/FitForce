package com.example.fitforce;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {
    private Paint progressPaint;
    private Paint backgroundPaint;
    private RectF rectF;
    private float progress = 0;
    private float strokeWidth = 20;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        rectF = new RectF();

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffd3d3d3); // Background color
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(0xff00bcd4); // Progress color
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2 - strokeWidth / 2;

        rectF.set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);

        canvas.drawOval(rectF, backgroundPaint);
        canvas.drawArc(rectF, -90, progress * 360 / 100, false, progressPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}