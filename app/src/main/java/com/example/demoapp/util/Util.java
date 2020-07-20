package com.example.demoapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.TypedValue;

public class Util {

    private static Paint mPaint;

    @Deprecated
    public static final int dipToPixel(Context context, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1);
    }

    @Deprecated
    public static final int dipToPixel(Context context, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1);
    }

    public static final int dipToPixel(Resources r, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics()) + 1);
    }

    public static final float dipToPixel3(Context context, float dip) {
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()));
    }


    /**
     * dip转换成px
     *
     * @param context
     * @param dip
     * @return
     */
    public static final int dipToPixel2(Context context, int dip) {
        float scale = 0;
        if (context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dip * scale + 0.5f);
    }

    public static final int spToPixel(Context context, int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()) + 1);
    }

    public static Bitmap makeBitmap(int w, int h, int color) {
        if (w <= 0 || h <= 0) {
            return null;
        }
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
        p.setColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(0, 0, w, h, p);
        } else {
            canvas.drawOval(new RectF(0, 0, w, h), p);
        }
        return result;
    }

    public static Paint getPaint(){
        if(mPaint == null){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }else{
            mPaint.reset();
        }
        return mPaint;
    }
}
