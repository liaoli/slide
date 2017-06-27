package com.slide.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.slide.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class CustomHScrollView extends HorizontalScrollView {

    private static final String TAG = "CustomHScrollView";

    public ViewPager page;

    public CustomHScrollView(Context context) {
        super(context);
        setFadingEdgeLength(0);
    }

    public CustomHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFadingEdgeLength(0);
    }

    public CustomHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public boolean onTouchEvent(MotionEvent ev) {
        if (page != null) {
            page.requestDisallowInterceptTouchEvent(true);
        }

        return  super.onTouchEvent(ev);
    }

}
