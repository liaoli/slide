package com.slide.view.discovery;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.slide.liaoli.R;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class HashGroupView extends FrameLayout {
    public HashGroupView(@NonNull Context context) {
        this(context,null);
    }

    public HashGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HashGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HashGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(Context context) {

        inflate(context, R.layout.hash_group_item_layout,this);

    }
}
