package com.slide.liaoli;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.slide.view.BinarySlidingView;
import com.slide.view.BinarySlidingView.OnMenuOpenListener;
import com.slide.view.DepthPageTransformer;
import com.slide.view.FlingRecycleView;
import com.slide.view.adapter.ImageCardAdapter;
import com.slide.view.layoutmanager.GalleryLayoutManager;
import com.slide.view.layoutmanager.impl.CurveTransformer;

public class MainActivity extends FragmentActivity implements BlankFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private BinarySlidingView mMenu;
    private List<String> mDatas = new ArrayList<String>();
    private List<Fragment> fragments = new ArrayList<>();
    FlingRecycleView mPagerRecycleView;
    List<Integer> mResId;
    List<ImageCardAdapter.CardItem> mCardItems;
    private int oldPosition;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();

            //Translucent status bar

            window.setFlags(

                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,

                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //Translucent navigation bar
        }

        initView();

        initNavigation();
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {




                return super.onFling(e1, e2, velocityX, velocityY);

            }
        });

    }

    private void initNavigation() {


        mResId = new ArrayList<Integer>();
        mResId.add(R.drawable.icon_navigation_group);
        mResId.add(R.drawable.icon_navigation_camere);
        mResId.add(R.drawable.icon_navigation_discover);
        mResId.add(R.drawable.icon_navigation_flow);
        mResId.add(R.drawable.icon_navigation_live);
        mCardItems = new ArrayList<ImageCardAdapter.CardItem>(5);
        ImageCardAdapter.CardItem cardItem;
        for (int i = 0; i < 5; i++) {
            cardItem = new ImageCardAdapter.CardItem(mResId.get(i % mResId.size()), "item:" + i);
            mCardItems.add(cardItem);
        }

        mPagerRecycleView = (FlingRecycleView) findViewById(R.id.navigation_recycle_view);

        mPagerRecycleView.setFlingAble(false);
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mPagerRecycleView, 3);
//        layoutManager.attach(mPagerRecycleView);
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {

                Log.e(TAG, item + " is selected  " + position);
                if (oldPosition == position) {
                    return;
                }

                if (oldPosition < position) {

                    switch (oldPosition) {
                        case 0:

                            break;
                        case 1:
                            mMenu.closeLeftPager();
                            break;
                        case 2:
                            mMenu.openRightPager();
                            break;
                        case 3:
                            //TODO：跳转到开播预览界面
                            break;
                        case 4:
                            break;
                    }

                } else {

                    switch (oldPosition) {
                        case 0:
                            break;
                        case 1:
                            //TODO：跳转到群界面
                            break;
                        case 2:
                            mMenu.openLeftPager();
                            break;
                        case 3:
                            mMenu.closeRightPager();
                            break;
                        case 4:
                            break;
                    }

                }

                oldPosition = position;

            }
        });
        layoutManager.setItemTransformer(new CurveTransformer());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ImageCardAdapter imageAdapter = new ImageCardAdapter(mCardItems, (int) (displayMetrics.widthPixels * 0.3f), (int) (displayMetrics.heightPixels * 0.3f));
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "click" + mCardItems.get(position).mName, Toast.LENGTH_SHORT).show();
                mPagerRecycleView.smoothScrollToPosition(position);
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mPagerRecycleView.smoothScrollToPosition(2);
            }
        });
    }

    private void initView() {
        View toolbar = findViewById(R.id.toolbar);

        mMenu = (BinarySlidingView) findViewById(R.id.id_menu);

        mMenu.setToolbar(toolbar);

        mMenu.setOnMenuOpenListener(new OnMenuOpenListener()

        {
            @Override
            public void onMenuOpen(boolean isOpen, int flag) {
                if (isOpen) {
                    Toast.makeText(getApplicationContext(),
                            flag == 0 ? "LeftMenu Open" : "RightMenu Open",
                            Toast.LENGTH_SHORT).show();
                    if (flag == 0) {
                        mPagerRecycleView.smoothScrollToPosition(1);
                    } else {
                        mPagerRecycleView.smoothScrollToPosition(3);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            flag == 0 ? "LeftMenu Close" : "RightMenu Close",
                            Toast.LENGTH_SHORT).show();
                    if (flag == 0) {
                        mPagerRecycleView.smoothScrollToPosition(2);
                    } else {
                        mPagerRecycleView.smoothScrollToPosition(2);
                    }
                }

            }
        });


        fragments.add(BlankFragment.newInstance("#330f7f", "fragment_1"));

        fragments.add(BlankFragment.newInstance("#0f0f33", "fragment_2"));

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setCurrentItem(0);


        pager.setPageTransformer(false, new DepthPageTransformer());

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {



        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
