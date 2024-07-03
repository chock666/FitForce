package com.example.fitforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BarChartView extends View {

    private Paint paint; // אובייקט לציור
    private float[] data = {100, 200, 300}; // נתוני גרף עמודות התחלתיים



    // קונסטרקטור שמקבל הקשר ו AttributeSet (מאפיינים מה-XML)
    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    // פונקציה לאתחול הצבעים וסגנון הציור
    private void init() {
        paint = new Paint();
        paint.setColor(Color.GRAY); // קביעת צבע אפור לציור
        paint.setStyle(Paint.Style.FILL); // סגנון ציור למילוי
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // חישוב רוחב העמודות והמרווחים ביניהם
        int barWidth = getWidth() / (data.length * 2);
        int maxValue = getMaxValue(data); // מקסימום ערך בנתונים
        int bottom = getHeight() - 20; // תחתית הגרף

        // ציור העמודות
        for (int i = 0; i < data.length; i++) {
            float left = i * barWidth * 2 + barWidth / 2; // צד שמאל של העמודה
            float right = left + barWidth; // צד ימין של העמודה
            float top = bottom - (data[i] / maxValue) * bottom; // צד עליון של העמודה (גובה לפי הערך)
            canvas.drawRect(left, top, right, bottom, paint); // ציור העמודה
        }
    }

    // פונקציה למציאת הערך המקסימלי בנתונים
    private int getMaxValue(float[] data) {
        int maxValue = 0;
        for (float value : data) {
            if (value > maxValue) {
                maxValue = (int) value;
            }
        }
        return maxValue;
    }

    // פונקציה לעדכון הנתונים ושרטוט מחדש של הגרף
    public void setData(float[] data) {
        this.data = data;
        invalidate(); // קריאה לפונקציה onDraw לציור מחדש
    }
}
