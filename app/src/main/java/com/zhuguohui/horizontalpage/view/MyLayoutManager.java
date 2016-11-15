package com.zhuguohui.horizontalpage.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhuguohui on 2016/11/9.
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    int totalHeight = 0;
    int offsetY = 0;


    @Override
    public boolean canScrollVertically() {
        return true;
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int newY = offsetY + dy;
        int result = dy;
        if (newY > totalHeight) {
            result = totalHeight - offsetY;
        } else if (newY < -getPaddingTop()) {
            result = -getPaddingTop() - offsetY;
        }
        offsetY += result;
        offsetChildrenVertical(-result);
        recycleAndFillItems(recycler, state);

        return result;

        //    return super.scrollVerticallyBy(dy, recycler, state);
    }


    private SparseArray<Rect> allItemFrames = new SparseArray<>();

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //    Log.i("zzz", "onLayoutChildren");
        if (getItemCount() == 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }

        detachAndScrapAttachedViews(recycler);
        int offsetY = 0;
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
            //   layoutDecorated(view, 0, offsetY, width, offsetY + height);
            Rect rect = allItemFrames.get(i);
            if (rect == null) {
                rect = new Rect();
            }
            rect.set(0, offsetY, width, offsetY + height);
            allItemFrames.put(i, rect);

            offsetY += height;
        }
        totalHeight = Math.max(offsetY, getHeight());
        totalHeight = totalHeight - getHeight() + getPaddingBottom() + getPaddingTop();
        recycleAndFillItems(recycler, state);
        // Log.i("zzz", "item count=" + getChildCount());
    }


    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }

        Rect displayRect = new Rect(getPaddingLeft(), getPaddingTop() + offsetY, getWidth() - getPaddingLeft() - getPaddingRight(), offsetY + getHeight() - getPaddingTop() - getPaddingBottom());
        Rect childRect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childRect.left = getDecoratedLeft(child);
            childRect.top = getDecoratedTop(child);
            childRect.right = getDecoratedRight(child);
            childRect.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(displayRect, childRect)) {
                removeAndRecycleView(child, recycler);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayRect, allItemFrames.get(i))) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                Rect rect = allItemFrames.get(i);
                layoutDecorated(view, rect.left, rect.top - offsetY, rect.right, rect.bottom - offsetY);
            }
        }

    }




}
