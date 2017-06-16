package com.slide.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.slide.liaoli.R;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class navigationView extends LinearLayout {

    private ImageView group,chat,discovery,flow,live;

    public navigationView(@NonNull Context context) {
        this(context,null);
    }

    public navigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public navigationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public navigationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {

        inflate(context, R.layout.navigation_layout,this);

        setOrientation(LinearLayout.HORIZONTAL);
        group = (ImageView) findViewById(R.id.bt_1);
        chat = (ImageView) findViewById(R.id.bt_2);
        discovery = (ImageView) findViewById(R.id.bt_3);
        flow = (ImageView) findViewById(R.id.bt_4);
        live = (ImageView) findViewById(R.id.bt_5);

        group.setImageResource(R.drawable.icon_group);
        chat.setImageResource(R.drawable.icon_camere);
        discovery.setImageResource(R.drawable.icon_discover);
        flow.setImageResource(R.drawable.icon_flow);
        live.setImageResource(R.drawable.icon_live);
    }



}
