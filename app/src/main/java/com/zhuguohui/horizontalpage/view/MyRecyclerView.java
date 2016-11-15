package com.zhuguohui.horizontalpage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * Created by zhuguohui on 2016/11/9.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        this(context, null);
    }

    Scroller mScroller;

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnFlingListener(new MyOnFlingListener());
        mScroller = new Scroller(context);
    }

    class MyOnFlingListener extends OnFlingListener {

        @Override
        public boolean onFling(int velocityX, int velocityY) {
            int dy = 0;
            Log.i("zzz","vY="+velocityY);
            if (velocityY <0) {
                dy = 0 - startY;
            } else {
                dy = getHeight() - startY;
            }

            mScroller.startScroll(0, startY, 0, dy, 500);
            invalidate();
            return true;
        }
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if(mScroller.computeScrollOffset()){
            int x=mScroller.getCurrX();
            int y=mScroller.getCurrY();
            int dy=y-startY;
            Log.i("zzz","dy="+dy);
            scrollBy(0,dy);
            startY=y;
            //    scrollTo(x,y);
        }
    }

    int startX, startY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            startX = (int) e.getX();
            startY = (int) e.getY();
        }
        return super.onTouchEvent(e);
    }
}
