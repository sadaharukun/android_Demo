package com.example.demoapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ZhiFuBaoAnimView extends View {
    private Paint mPaint;
    private Path mCirclePath;
    private Path mDestPath;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;
    private ValueAnimator mAnimator;
    private PathMeasure mPathMeasure;
    private float mCurValue;

    public ZhiFuBaoAnimView(Context context) {
        super(context);
        init(context);
    }

    public ZhiFuBaoAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZhiFuBaoAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mCirclePath = new Path();
        mDestPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = (getMeasuredWidth() >> 1);
        mCenterY = (getMeasuredHeight() >> 1);
        mRadius = Math.min(mCenterX, mCenterY) - mPaint.getStrokeWidth();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float start = 0;
        float stop = 0;
        mDestPath.reset();
        mCirclePath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CW);
        mCirclePath.moveTo(mCenterX - mRadius / 2, mCenterY);
        mCirclePath.lineTo(mCenterX, mCenterY + mRadius / 2);
        mCirclePath.lineTo(mCenterX + mRadius / 2, mCenterY - mRadius / 3);
        mPathMeasure.setPath(mCirclePath, false);
        if (mCurValue < 1) {
            start = 0;
            stop = mCurValue * mPathMeasure.getLength();
            mPathMeasure.getSegment(start, stop, mDestPath, true);
        } else if (mCurValue >= 1) {
            start = 0;
            stop = mPathMeasure.getLength();
            mPathMeasure.getSegment(start, stop, mDestPath, true);
            mPathMeasure.nextContour();
            start = 0;
            stop = mPathMeasure.getLength() * (mCurValue - 1);
            mPathMeasure.getSegment(start, stop, mDestPath, true);
        }
        canvas.drawPath(mDestPath, mPaint);
    }

    public void start() {
        if (mAnimator != null && mAnimator.isRunning()) {
            return;
        }
        if (mAnimator == null) {
            mAnimator = new ValueAnimator();
        } else {
            mAnimator.removeAllListeners();
            mAnimator.removeAllUpdateListeners();
        }
        mAnimator.setFloatValues(0, 2);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setDuration(5000);
        mAnimator.start();
    }

    public void stop() {
        if (mAnimator != null) {
            mAnimator.end();
        }
    }
}