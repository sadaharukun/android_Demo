package com.example.demoapp.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


import androidx.annotation.Nullable;

import com.example.demoapp.R;
import com.example.demoapp.util.STR;
import com.example.demoapp.util.Util;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.RESTART;


/**
 * 正在直播动画
 */
public class LivingStreamerView extends View {

    private  int TEXT_MARGIN;

    /**
     * 顶部的偏移量。UI设计上，内容区域不是居中，整体向下便宜了一点
     */
    private int TOP_OFFSET;
    /**
     * 每根线的初始和结束位置
     */
    private static final float RATIO_ANIM_INIT_ONE = 0.5f;
    private static final float RATIO_ANIM_END_ONE = 1f;
    private static final float RATIO_ANIM_INIT_TWO = 1f;
    private static final float RATIO_ANIM_END_TWO = 0.5f;
    private static final float RATIO_ANIM_INIT_THREE = 0.6f;
    private static final float RATIO_ANIM_END_THREE = 1f;
    private static final float RATIO_ANIM_INIT_FOUR = 1f;
    private static final float RATIO_ANIM_END_FOUR = 0.5f;
    private static final float RATIO_ANIM_INIT_FIVE = 0.5f;
    private static final float RATIO_ANIM_END_FIVE = 1f;
    /**
     * 线条的数量
     */
    private final static int LINE_COUNT = 5;

    /**
     * 总的动画时长，分成5个阶段
     */
    private final static int ANIM_DURATION = 200 * 5;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * text paint
     */
    private Paint mTextPaint;

    /**
     * 线条的颜色
     */
    private int mLineColor;


    /**
     * 线条的最大高度
     */
    private int mLineMaxHeight;

    /**
     * 线条的宽度
     */
    private int mSignLineWidth;

    /**
     * 线条之间的间距
     */
    private int mLineSpace;

    /**
     * 5根线，每根线4个坐标点
     */
    private float[] mPlayLinesDraw = new float[LINE_COUNT * 4];

    /**
     * 动画animator
     */
    private ValueAnimator mValueAnimator;

    /**
     * 是否正在执行动画
     */
    private boolean mIsInAnim;

    private String mLivingStr;

    private int mTextWidth;

    private Bitmap mBgBitmap;

    private Paint mBitmapPaint;

    private Rect mBgRect;

    public LivingStreamerView(Context context) {
        this(context, null);
    }

    public LivingStreamerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivingStreamerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        TEXT_MARGIN = Util.dipToPixel(context,1);
        TOP_OFFSET = Util.dipToPixel(context,1);
        setPadding(Util.dipToPixel(context, 3), Util.dipToPixel2(context, 6), Util.dipToPixel(context, 3), Util.dipToPixel2(context, 2));
        mLineColor = getContext().getResources().getColor(R.color.color_living_streamer_default_color);
        mLineMaxHeight = context.getResources().getDimensionPixelSize(R.dimen.living_streamer_max_line_height);
        mSignLineWidth = context.getResources().getDimensionPixelSize(R.dimen.living_streamer_line_width);
        mLineSpace = context.getResources().getDimensionPixelSize(R.dimen.living_streamer_line_space);
        mLivingStr = context.getResources().getString(R.string.living_streamer_str);
        mBgBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.living_streamer_background);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mSignLineWidth);
        mPaint.setColor(mLineColor);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setTextSize(Util.spToPixel(context,10));

        mBgRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int defaultWidth = getDefaultWidth();
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = defaultWidth;
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
            if (width < defaultWidth) {
                width = defaultWidth;
            }
        }

        int height;
        int defaultHeight = getDefaultHeight();
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = defaultHeight;
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
            if (height < defaultHeight) {
                height = defaultHeight;
            }
        }

        setMeasuredDimension(width, height);
        initPlayingLines();
        mBgRect.set(0, 0, width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background
        canvas.drawBitmap(mBgBitmap, null, mBgRect, mBitmapPaint);
        //draw lines
        canvas.drawLines(mPlayLinesDraw, mPaint);
        //draw text
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = (getHeight() >> 1) + distance + TOP_OFFSET;
        canvas.drawText(mLivingStr, getWidth() - getPaddingRight() - mTextWidth, baseline, mTextPaint);
    }

    public void stopPlay() {
        endAnim();
        initPlayingLines();
        invalidate();
    }

    public void startPlay() {
        startAnim();
    }

    public void setLineColor(int color) {
        if (color == mLineColor) {
            return;
        }
        mLineColor = color;
        if (mPaint != null) {
            mPaint.setColor(color);
        }
        invalidateInMainThread();
    }

    /**
     * 设置描述
     *
     * @param text
     */
    public void setLivingStr(String text) {
        if (STR.isEmptyNull(text) || text.equals(mLivingStr)) {
            return;
        }
        mLivingStr = text;
        requestLayout();
    }

    /**
     * 默认宽度
     */
    private int getDefaultWidth() {
        if (!STR.isEmptyNull(mLivingStr)) {
            mTextWidth = (int) mTextPaint.measureText(mLivingStr);
        }
        return getPaddingLeft() + getPaddingRight() + LINE_COUNT * mSignLineWidth + (LINE_COUNT - 1) * mLineSpace + mTextWidth + TEXT_MARGIN;
    }

    /**
     * 默认高度
     */
    private int getDefaultHeight() {
        Rect rect = new Rect();
        if (!STR.isEmptyNull(mLivingStr)) {
            mTextPaint.getTextBounds(mLivingStr, 0, mLivingStr.length(), rect);
        }
        return getPaddingTop() + getPaddingBottom() + Math.max(mLineMaxHeight, rect.height());
    }

    /**
     * 初始化每根线的位置。
     */
    private void initPlayingLines() {
        // 要保证view始终居中对齐，当设置的宽超过了默认值时，需要进行修正
        int gravityFixLeft;
        int defaultWidth = getDefaultWidth();
        if (getMeasuredWidth() > defaultWidth) {
            gravityFixLeft = (getMeasuredWidth() - defaultWidth) / 2;
        } else {
            gravityFixLeft = 0;
        }
        for (int i = 0; i < LINE_COUNT; i++) {
            int PointStarIndex = i * 4;
            //4个点的坐标
            mPlayLinesDraw[PointStarIndex] = gravityFixLeft + getPaddingLeft() + i * mSignLineWidth + i * mLineSpace + (mSignLineWidth >> 1);
            mPlayLinesDraw[PointStarIndex + 1] = (getMeasuredHeight() - getInitRatio(i) * mLineMaxHeight) / 2 + TOP_OFFSET;
            mPlayLinesDraw[PointStarIndex + 2] = mPlayLinesDraw[PointStarIndex];
            mPlayLinesDraw[PointStarIndex + 3] = mPlayLinesDraw[PointStarIndex + 1] + getInitRatio(i) * mLineMaxHeight;
        }
    }


    /**
     * 每根线初始高度比例
     *
     * @param index 每根线的index
     */
    private float getInitRatio(int index) {
        float ratio;
        switch (index) {
            case 0:
                ratio = RATIO_ANIM_INIT_ONE;
                break;
            case 1:
                ratio = RATIO_ANIM_INIT_TWO;
                break;
            case 2:
                ratio = RATIO_ANIM_INIT_THREE;
                break;
            case 3:
                ratio = RATIO_ANIM_INIT_FOUR;
                break;
            case 4:
                ratio = RATIO_ANIM_INIT_FIVE;
                break;
            default:
                ratio = 1f;
        }
        return ratio;
    }


    /**
     * 结束动画
     */
    private void endAnim() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
    }


    /**
     * 开始动画
     */
    public void startAnim() {
        if (mIsInAnim) {
            return;
        }
        if (mValueAnimator == null) {
            mValueAnimator = new ValueAnimator();
        } else {
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.removeAllListeners();
            mValueAnimator.cancel();
        }
        mValueAnimator.setFloatValues(0, 1f);
        mValueAnimator.setDuration(ANIM_DURATION);
        mValueAnimator.setRepeatCount(INFINITE);
        mValueAnimator.setRepeatMode(RESTART);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                playingLines(animatedFraction);
                invalidateInMainThread();
            }
        });

        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsInAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsInAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mIsInAnim = false;

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mValueAnimator.start();
    }

    /**
     * 因为动画在执行过程中有一个停顿的过程，所以处理的会复杂一些
     * 动画分成5个阶段，前4个阶段是动画过程，最后一个阶段是停顿的过程
     * singleFraction ： 每一阶段动画执行的时间片段
     * realFraction   ： 每一阶段动画从0f -> 1f
     *
     * @param fraction fraction 0f-1f
     */
    private void playingLines(float fraction) {
        float realFraction;
        //不要超过0.25
        float singleFraction = 0.2f;
        //第一阶段和第三阶段的动画
        if (fraction < singleFraction || (fraction >= singleFraction * 2 && fraction < singleFraction * 3)) {
            if (fraction < singleFraction) {
                realFraction = fraction / singleFraction;
            } else {
                realFraction = (fraction - singleFraction * 2) / singleFraction;
            }
            PlaySingleLine(0, RATIO_ANIM_INIT_ONE, RATIO_ANIM_END_ONE, realFraction);
            PlaySingleLine(1, RATIO_ANIM_INIT_TWO, RATIO_ANIM_END_TWO, realFraction);
            PlaySingleLine(2, RATIO_ANIM_INIT_THREE, RATIO_ANIM_END_THREE, realFraction);
            PlaySingleLine(3, RATIO_ANIM_INIT_FOUR, RATIO_ANIM_END_FOUR, realFraction);
            PlaySingleLine(4, RATIO_ANIM_INIT_FIVE, RATIO_ANIM_END_FIVE, realFraction);
        }
        //第二阶段和第四阶段的动画
        else if (fraction < singleFraction * 2 || (fraction >= singleFraction * 3 && fraction < singleFraction * 4)) {
            if (fraction < singleFraction * 2) {
                realFraction = (fraction - singleFraction) / singleFraction;
            } else {
                realFraction = (fraction - singleFraction * 3) / singleFraction;
            }
            PlaySingleLine(0, RATIO_ANIM_END_ONE, RATIO_ANIM_INIT_ONE, realFraction);
            PlaySingleLine(1, RATIO_ANIM_END_TWO, RATIO_ANIM_INIT_TWO, realFraction);
            PlaySingleLine(2, RATIO_ANIM_END_THREE, RATIO_ANIM_INIT_THREE, realFraction);
            PlaySingleLine(3, RATIO_ANIM_END_FOUR, RATIO_ANIM_INIT_FOUR, realFraction);
            PlaySingleLine(4, RATIO_ANIM_END_FIVE, RATIO_ANIM_INIT_FIVE, realFraction);
        } else if (fraction < 1) {//阶段5
            // 啥都不干，停顿
        }
    }

    /**
     * 只需要修改每根线的top和bottom即可
     *
     * @param lineIndex 第几跟线
     * @param start     开始位置
     * @param end       结束位置
     * @param fraction  比率
     */
    private void PlaySingleLine(int lineIndex, float start, float end, float fraction) {
        int lineHeight = (int) ((start + fraction * (end - start)) * mLineMaxHeight);
        //top
        mPlayLinesDraw[lineIndex * 4 + 1] = ((getHeight() - lineHeight) >> 1) + TOP_OFFSET ;
        //bottom
        mPlayLinesDraw[lineIndex * 4 + 3] =   mPlayLinesDraw[lineIndex * 4 + 1] + lineHeight;
    }


    /**
     * UI线程刷新
     */
    private void invalidateInMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
