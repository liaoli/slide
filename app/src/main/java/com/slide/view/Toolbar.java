package com.slide.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.slide.liaoli.R;
import com.slide.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class Toolbar extends FrameLayout{


    ImageView mLefticon,mSearch,mMore;

    public Toolbar(@NonNull Context context) {
        this(context,null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        inflate(context, R.layout.toolbat_layout,this);
        mLefticon = (ImageView) findViewById(R.id.left_icon);
        mSearch = (ImageView) findViewById(R.id.search);
        mMore = (ImageView) findViewById(R.id.more);

        int statusBarHeight = ScreenUtils.getStatusBarHeight(context);

        setPadding(0,statusBarHeight,0,0);

        mMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(mMore, "rotation", 0f, 45f);

                anim.setDuration(500);

                anim.start();
            }
        });
    }



}
