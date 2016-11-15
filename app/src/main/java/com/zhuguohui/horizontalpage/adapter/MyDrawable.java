package com.zhuguohui.horizontalpage.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by zhuguohui on 2016/11/9.
 */

public class MyDrawable extends Drawable {
    Paint mPaint;


    public MyDrawable() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(getBounds(),mPaint);
        Log.i("zzz","draw bounds="+getBounds());
    }

    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
        Log.i("zzz","bounds="+bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }



    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }
}
