package com.example.fitforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {
    private Paint progressPaint; // צבע המייצג את ההתקדמות
    private Paint backgroundPaint; // צבע הרקע של הבר
    private RectF rectF; // מלבן המגדיר את גבולות הבר העגול
    private float progress = 0; // משתנה המייצג את ההתקדמות הנוכחית (באחוזים)
    private float strokeWidth = 20; // רוחב הקו של הבר

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(); // אתחול משתני הצבע והמלבן
    }

    private void init() {
        rectF = new RectF(); // אתחול מלבן הגבולות

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffd3d3d3); // צבע הרקע (אפור בהיר)
        backgroundPaint.setStyle(Paint.Style.STROKE); // הגדרת סגנון הקו (רק קו חיצוני)
        backgroundPaint.setStrokeWidth(strokeWidth); // הגדרת רוחב הקו
        backgroundPaint.setAntiAlias(true); // הפעלת אנטי-אליאסינג לקווים חלקים

        progressPaint = new Paint();
        progressPaint.setColor(0xff00bcd4); // צבע ההתקדמות (תכלת)
        progressPaint.setStyle(Paint.Style.STROKE); // הגדרת סגנון הקו (רק קו חיצוני)
        progressPaint.setStrokeWidth(strokeWidth); // הגדרת רוחב הקו
        progressPaint.setAntiAlias(true); // הפעלת אנטי-אליאסינג לקווים חלקים
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // חישוב רוחב וגובה הקנבס
        float width = getWidth();
        float height = getHeight();
        // חישוב הרדיוס של הבר העגול
        float radius = Math.min(width, height) / 2 - strokeWidth / 2;

        // הגדרת גבולות המלבן
        rectF.set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);

        // ציור הבר העגול עם צבע הרקע
        canvas.drawOval(rectF, backgroundPaint);
        // ציור ההתקדמות על הבר העגול
        canvas.drawArc(rectF, -90, progress * 360 / 100, false, progressPaint);
    }

    // פונקציה להגדרת ההתקדמות מחדש ועדכון התצוגה
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate(); // בקשה לשרטט מחדש את התצוגה
    }
}
