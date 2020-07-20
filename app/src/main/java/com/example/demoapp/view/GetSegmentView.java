package com.example.demoapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.demoapp.util.Util;

import static android.animation.ValueAnimator.INFINITE;
import static android.graphics.PathMeasure.POSITION_MATRIX_FLAG;
import static android.graphics.PathMeasure.TANGENT_MATRIX_FLAG;

public class GetSegmentView extends View {

    private Paint paint;
    private Bitmap desBitmap;
    private Bitmap srcBitmap;

    private PathMeasure pathMeasure;

    private Path destPath;
    private Path circlePath;
    private float mCurValue;

    private float pos[];
    private float tan[];
    private Matrix mMatrix;
    public GetSegmentView(Context context) {
        super(context);
        init(context);
    }

    public GetSegmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GetSegmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        setLayerType(LAYER_TYPE_HARDWARE,null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);

        destPath = new Path();
        circlePath = new Path();

        pathMeasure = new PathMeasure();

        pos = new float[2];
        tan = new float[2];
        mMatrix = new Matrix();
        //git test
    }

    public void startPlay(){
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(3000);
        animator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        desBitmap = Util.makeBitmap(getMeasuredWidth(),getMeasuredHeight(),0xFFFFCC44);
        srcBitmap = Util.makeBitmap(getMeasuredWidth(),getMeasuredHeight(),0xFF66AAFF);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        circlePath.addCircle(width >> 1, height >> 1, (Math.min(width, height) >> 1) - 10, Path.Direction.CW);

        pathMeasure.setPath(circlePath, false);
        float length = pathMeasure.getLength();
        float start = 0;
        if (mCurValue >= 0.5f) {
            start = (mCurValue * 2 - 1) * length;
        }
        int stop = (int) (pathMeasure.getLength() * mCurValue);
        destPath.reset();
        pathMeasure.getSegment(start, stop, destPath, true);
        canvas.drawPath(destPath,paint);

        pathMeasure.getPosTan(stop,pos,tan);
        for (int i = 0; i < pos.length; i++) {
            Log.e("season", "pos" + pos[i] + " tan" + tan[i]);
        }
        pathMeasure.getMatrix(stop,mMatrix,POSITION_MATRIX_FLAG | TANGENT_MATRIX_FLAG);
//        canvas.drawColor(Color.GREEN);
//        int layerID;
//        layerID = canvas.saveLayer(0, 0, width , height , paint, Canvas.ALL_SAVE_FLAG);
//        Log.e("yxx","layerID:"+layerID);
////        canvas.save();
//        canvas.drawBitmap(desBitmap, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(srcBitmap, width / 2, height / 2, paint);
//        paint.setXfermode(null);
////        canvas.restore();
//        canvas.restoreToCount(layerID);
//        canvas.drawLine(0,100,100,200,paint);
//        layerID = canvas.saveLayer(0, 0, width , height , paint, Canvas.ALL_SAVE_FLAG);
//        Log.e("yxx","layerID:"+layerID);
//        canvas.restoreToCount(layerID);

    }

    private Paint generatePaint(int color,Paint.Style style,int width)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }
}
