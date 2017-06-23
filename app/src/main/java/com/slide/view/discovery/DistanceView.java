package com.slide.view.discovery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slide.liaoli.R;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DistanceView  extends LinearLayout{
    private TextView distamnce;

    public DistanceView(Context context) {
        this(context,null);
    }

    public DistanceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DistanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DistanceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        inflate(context, R.layout.distance_layout,this);
        distamnce = (TextView) findViewById(R.id.distance_text);
    }

    public void setDistamnce(String distamnce){
        this.distamnce.setText(distamnce);
    }
}
