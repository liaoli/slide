package com.slide.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;
import com.slide.utils.ScreenUtils;
import com.slide.liaoli.R;

/**
 * @author liaoli
 */
public class BinarySlidingView extends CustomHScrollView {


    private static final String TAG = "BinarySlidingMenu";
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    private int mHalfMenuWidth;

    private boolean isOperateRight;
    private boolean isOperateLeft;

    private boolean once;

    private ViewGroup mLeftContent;
    private FrameLayout mCenterContent;
    private ViewGroup mRightContent;
    private ViewGroup mWrapper;

    private boolean isLeftMenuOpen;
    private boolean isRightMenuOpen;
    private View toolbar;
    private GestureDetector mGestureDetector;

    public void setToolbar(View toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * 回调的接口
     *
     * @author zhy
     */
    public interface OnMenuOpenListener {
        /**
         * @param isOpen true打开菜单，false关闭菜单
         * @param flag   0 左侧， 1右侧
         */
        void onMenuOpen(boolean isOpen, int flag);
    }

    public OnMenuOpenListener mOnMenuOpenListener;

    public void setOnMenuOpenListener(OnMenuOpenListener mOnMenuOpenListener) {
        this.mOnMenuOpenListener = mOnMenuOpenListener;
    }

    public BinarySlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * dp 菜单距离屏幕的右边距
     */
    private int mMenuRightPadding = 0;


    public BinarySlidingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScreenWidth = ScreenUtils.getScreenWidth(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BinarySlidingView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.BinarySlidingView_rightPadding:
                    // 默认50
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 0f,
                                    getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();

        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    public BinarySlidingView(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度
         */
        if (!once) {

            mWrapper = (ViewGroup) getChildAt(0);
            mLeftContent = (ViewGroup) mWrapper.getChildAt(0);
            mCenterContent = (FrameLayout) mWrapper.getChildAt(2);
            mRightContent = (ViewGroup) mWrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 4;
            mLeftContent.getLayoutParams().width = mMenuWidth;
            mCenterContent.getLayoutParams().width = mScreenWidth;
            mRightContent.getLayoutParams().width = mMenuWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int scrollX = getScrollX();
        float oldX= 0,oldY= 0,newX=0,newY=0;

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                oldX = ev.getX();
                oldY = ev.getY();
                break;
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:

                newX = ev.getX();
                newY = ev.getY();

                float k = (newY - oldY) / (newX - oldX);
                int dx = (int) (newX - oldX);
                int dy = (int) (newY - oldY);
                Log.e(TAG, "onTouchEvent k = " + k + ",dx = " +dx + ",dy  = " + dy + ",oldX = " + oldX +",oldY = " + oldY + ",newX = " + newX + ",newY=" + newY);

                //如果是操作左侧菜单
                if (isOperateLeft) {
                    if (isLeftMenuOpen && scrollX == 0 && dx > 100) {
                        Log.e(TAG, "onTouchEvent  go to chat");
                    }
                    Log.e(TAG, "onTouchEvent scrollX = " + scrollX + ",mScreenWidth = " + mScreenWidth + ",mHalfMenuWidth = " + mHalfMenuWidth + ",(mScreenWidth - mHalfMenuWidth)= " + (mScreenWidth - mHalfMenuWidth) + "isOperateLeft = " + isOperateLeft + "isLeftMenuOpen = " + isLeftMenuOpen + "k = " + k + ",dx = " +dx);
                    if (isLeftMenuOpen) {
                        //如果影藏的区域大于菜单一半，则影藏菜单
                        if (scrollX > mHalfMenuWidth) {
                            this.smoothScrollTo(mMenuWidth, 0);
                            //如果当前左侧菜单是开启状态，且mOnMenuOpenListener不为空，则回调关闭菜单
                            if (isLeftMenuOpen && mOnMenuOpenListener != null) {
                                //第一个参数true：打开菜单，false：关闭菜单;第二个参数 0 代表左侧；1代表右侧
                                mOnMenuOpenListener.onMenuOpen(false, 0);
                            }
                            isLeftMenuOpen = false;


                        } else//打开左侧菜单
                        {
                            mLeftContent.bringToFront();
                            this.smoothScrollTo(0, 0);
                            //如果当前左侧菜单是关闭状态，且mOnMenuOpenListener不为空，则回调打开菜单
                            if (!isLeftMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(true, 0);
                            }
                            isLeftMenuOpen = true;

                        }


                    } else {
                        //如果影藏的区域大于菜单一半，则影藏菜单

                        if (scrollX > mScreenWidth - mHalfMenuWidth) {
                            this.smoothScrollTo(mMenuWidth, 0);
                            //如果当前左侧菜单是开启状态，且mOnMenuOpenListener不为空，则回调关闭菜单
                            if (isLeftMenuOpen && mOnMenuOpenListener != null) {
                                //第一个参数true：打开菜单，false：关闭菜单;第二个参数 0 代表左侧；1代表右侧
                                mOnMenuOpenListener.onMenuOpen(false, 0);
                            }
                            isLeftMenuOpen = false;


                        } else//打开左侧菜单
                        {
                            mLeftContent.bringToFront();
                            this.smoothScrollTo(0, 0);
                            //如果当前左侧菜单是关闭状态，且mOnMenuOpenListener不为空，则回调打开菜单
                            if (!isLeftMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(true, 0);
                            }
                            isLeftMenuOpen = true;

                        }
                    }
                }

                //操作右侧
                if (isOperateRight) {

                    if (isRightMenuOpen && scrollX == mScreenWidth * 2 && Math.abs(k) < 0.5 && dx < -100) {
                        Log.e(TAG, "onTouchEvent  go to live");
                    }
                    Log.e(TAG, "onTouchEvent scrollX = " + scrollX + ",mScreenWidth = " + mScreenWidth + ",mHalfMenuWidth = " + mHalfMenuWidth + ",(mScreenWidth + mHalfMenuWidth)= " + (mScreenWidth + mHalfMenuWidth) + "isOperateRight = " + isOperateRight + "isRightMenuOpen = " + isRightMenuOpen);
                    if (isRightMenuOpen) {
                        //打开右侧侧滑菜单
                        if (scrollX > 2 * mMenuWidth - mHalfMenuWidth) {
                            this.smoothScrollTo(mMenuWidth + mMenuWidth, 0);
                            mRightContent.bringToFront();

                            if (!isRightMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(true, 1);
                            }
                            isRightMenuOpen = true;

                        } else//关闭右侧侧滑菜单
                        {
                            this.smoothScrollTo(mMenuWidth, 0);

                            if (isRightMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(false, 1);
                            }
                            isRightMenuOpen = false;
                        }
                    } else {
                        //打开右侧侧滑菜单
                        if (scrollX > mHalfMenuWidth + mMenuWidth) {
                            this.smoothScrollTo(mMenuWidth + mMenuWidth, 0);
                            mRightContent.bringToFront();

                            if (!isRightMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(true, 1);
                            }
                            isRightMenuOpen = true;

                        } else//关闭右侧侧滑菜单
                        {
                            this.smoothScrollTo(mMenuWidth, 0);

                            if (isRightMenuOpen && mOnMenuOpenListener != null) {
                                mOnMenuOpenListener.onMenuOpen(false, 1);
                            }
                            isRightMenuOpen = false;
                        }
                    }
                }


                return true;
        }
        return super.onTouchEvent(ev);
    }


    public void closeRightPager() {
        this.smoothScrollTo(mMenuWidth, 0);
        isRightMenuOpen = false;
    }

    public void openRightPager() {
        this.smoothScrollTo(mMenuWidth + mMenuWidth, 0);
        mRightContent.bringToFront();
        isRightMenuOpen = true;
    }

    public void closeLeftPager() {
        this.smoothScrollTo(mMenuWidth, 0);
        isLeftMenuOpen = false;
    }

    public void openLeftPager() {
        this.smoothScrollTo(0, 0);
        mLeftContent.bringToFront();
        isLeftMenuOpen = true;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth;
        int alpha = 0, r = 0, g = 0, b = 0;
        if (l > mMenuWidth) {
            isOperateRight = true;
            isOperateLeft = false;
            alpha = (int) (255 * (scale - 1));
            r = 255;
        } else {
            isOperateRight = false;
            isOperateLeft = true;
            alpha = (int) (255 * (1 - scale));
            b = 255;
        }
        Log.e(TAG, "onScrollChanged  alpha = " + alpha + ",scale - 1 = " + (scale - 1) + ",l = " + l + ",oldl = " + oldl);
        ColorDrawable colorDrawable = new ColorDrawable(Color.argb(alpha, r, g, b));

        mCenterContent.setForeground(colorDrawable);

        toolbar.setBackground(colorDrawable);

        //ViewHelper.setTranslationX(mCenterContent, mMenuWidth * (scale - 1));
        mCenterContent.setTranslationX(mMenuWidth * (scale - 1));

    }


    /**
     * 如果竖向滑动距离<横向距离，执行横向滑动，否则竖向。如果是ScrollView，则'<'换成'>'
     */
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e(TAG, "onScroll distanceX = " + distanceX + ",ViewConfiguration.get(getContext()).getScaledWindowTouchSlop()" + ViewConfiguration.get(getContext()).getScaledWindowTouchSlop());
            if (Math.abs(distanceY) < Math.abs(distanceX) && Math.abs(distanceX) > ViewConfiguration.get(getContext()).getScaledWindowTouchSlop()) {

                if (distanceX > ViewConfiguration.get(getContext()).getScaledWindowTouchSlop()) {


                    if (isLeftMenuOpen) {
                        Log.e(TAG, "onScroll distanceX = " + distanceX + "go to chat");
                    }

                }

                if (distanceX < -ViewConfiguration.get(getContext()).getScaledWindowTouchSlop()) {

                    if (isRightMenuOpen) {
                        Log.e(TAG, "onScroll distanceX = " + distanceX + "go to live");
                    }
                }


                return true;
            }
            return false;
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


      final int action = ev.getAction();
        float oldX= 0,oldY= 0,newX=0,newY=0;

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {

            }
            break;
            case MotionEvent.ACTION_DOWN: {

                oldX = ev.getX();
                oldY = ev.getY();
            }

            break;

            case MotionEvent.ACTION_UP: {

                newX = ev.getX();
                newY = ev.getY();


             float k = (newY - oldY) / (newX - oldX);

                Log.e(TAG,"k = " + k);
                if(Math.abs(k) >1.0/2){




                    return false;
                }

            }

            break;
        }

        return super.onInterceptTouchEvent(ev);
    }
}
