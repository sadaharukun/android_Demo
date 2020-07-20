package com.example.demoapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;

import com.example.demoapp.util.Util;

public class NormalGestureView extends View {
    private Paint mPaint;
    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;
    private Path mPath;
    public NormalGestureView(Context context) {
        super(context);
        init(context);
    }

    public NormalGestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalGestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mPaint = Util.getPaint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            mDownX = event.getX();
            mDownY = event.getY();
            mMoveX = mDownX;
            mMoveY = mDownY;
            mPath.moveTo(mDownX, mDownY);
            invalidate();
        } else if (action == MotionEvent.ACTION_MOVE) {
            float endX = (mMoveX + event.getX()) / 2;
            float endY = (mMoveY + event.getY()) / 2;
            mPath.quadTo(mMoveX, mMoveY, endX, endY);
//            mPath.lineTo(event.getX(),event.getY());
            mMoveX = endX;
            mMoveY = endY;
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.drawPath(mPath,mPaint);

    }
}
