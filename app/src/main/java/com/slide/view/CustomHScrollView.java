package com.slide.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class CustomHScrollView extends HorizontalScrollView {

    private GestureDetector mGestureDetector;

    public ViewPager page;

    public CustomHScrollView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public CustomHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public CustomHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public void setPage(ViewPager page) {
        this.page = page;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (page != null) {
            page.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (page != null) {
            page.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (page != null) {
            page.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 如果竖向滑动距离<横向距离，执行横向滑动，否则竖向。如果是ScrollView，则'<'换成'>'
     */
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) < Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}
