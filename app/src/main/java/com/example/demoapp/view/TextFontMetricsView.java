package com.example.demoapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TextFontMetricsView extends View {
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mRectPaint;
    public TextFontMetricsView(Context context) {
        super(context);
        init(context);
    }

    public TextFontMetricsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextFontMetricsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(120);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setStrokeWidth(2);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.BLACK);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = mPaint.measureText("Season");
        float x = getWidth() >> 1;
        float y = 0;
        Paint.FontMetrics fontMetrics= mPaint.getFontMetrics();
        float ascent = fontMetrics.ascent ;
        float descent = fontMetrics.descent ;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        float baseLineY = (getHeight() >> 1) + (descent - ascent) / 2 - descent;
        canvas.drawText("Seasyn", x, baseLineY, mPaint);

        Log.e("yxx",
                "ascent:" + ascent + "\n" +
                        "descent:" + descent + "\n" +
                        "top:" + top + "\n" +
                        "bottom:" + bottom + "\n"
        );
        canvas.drawLine(x,ascent,width,ascent,mLinePaint);
        canvas.drawLine(x,descent,width,descent,mLinePaint);
        canvas.drawLine(x,top,width,top,mLinePaint);
        canvas.drawLine(x,bottom,width,bottom,mLinePaint);
        canvas.drawLine(x,y,width,y,mLinePaint);


        canvas.drawRect(0,0,getWidth(),getHeight(),mRectPaint);
        canvas.drawLine(0, getHeight() >> 1,getWidth(), getHeight() >> 1,mLinePaint);
    }
}
