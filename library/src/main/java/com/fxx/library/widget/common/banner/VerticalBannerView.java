package com.fxx.library.widget.common.banner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.fxx.library.widget.R;

import java.util.Random;

@SuppressWarnings("unused")
public class VerticalBannerView extends LinearLayout implements BaseBannerAdapter.OnDataChangedListener {

    private float mBannerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
            .getDisplayMetrics());
    private int mPeriod;
    private int mRandomStep;
    private int mAnimDuration;
    private boolean mRandomEnable;

    private BaseBannerAdapter mAdapter;

    private View mFirstView;
    private View mSecondView;

    private int mPosition;

    private boolean isStarted;
    private Paint mDebugPaint;

    private Random random;

    public VerticalBannerView(Context context) {
        this(context, null);
    }

    public VerticalBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    /**
     * bannerHeight banner的高度
     * animDuration 每次切换动画时间
     * gap banner切换时间
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerticalBannerView);
        mPeriod = array.getInteger(R.styleable.VerticalBannerView_vbvPeriod, 3000);
        mRandomStep = array.getInteger(R.styleable.VerticalBannerView_vbvRandomStep, 1000);
        mRandomEnable = array.getBoolean(R.styleable.VerticalBannerView_vbvRandomEnable, false);
        mAnimDuration = array.getInteger(R.styleable.VerticalBannerView_vbvAnimDuration, 800);

        int halfPeriod = mPeriod / 2;
        if (mAnimDuration > halfPeriod) {
            mAnimDuration = halfPeriod;
        }

        if (mRandomStep > halfPeriod) {
            mRandomStep = halfPeriod;
        }

        random = new Random();

        array.recycle();
    }

    /**
     * 设置banner的数据
     */
    public void setAdapter(BaseBannerAdapter adapter) {
        if (adapter == null) {
            throw new RuntimeException("adapter must not be null");
        }
        if (mAdapter != null) {
            throw new RuntimeException("you have already set an Adapter");
        }
        mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
        setupAdapter();
    }

    public void start() {
        if (mAdapter == null) {
            throw new RuntimeException("you must call setAdapter() before start");
        }

        if (!isStarted && mAdapter.getCount() > 1) {
            isStarted = true;
            postPeriod(mRunnable);
        }
    }

    public void stop() {
        removeCallbacks(mRunnable);
        isStarted = false;
    }


    private void setupAdapter() {
        if (mAdapter.getCount() == 0)
            return;

        removeAllViews();

        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);
        } else {
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));
            addView(mFirstView);
            addView(mSecondView);

            mPosition = 1;
            isStarted = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = (int) mBannerHeight;
        } else {
            mBannerHeight = getMeasuredHeight();
        }
        if (isInEditMode()) {
            setBackgroundColor(Color.GRAY);
            return;
        }
        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = (int) mBannerHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = (int) mBannerHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            mDebugPaint.setColor(Color.WHITE);
            mDebugPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources()
                    .getDisplayMetrics()));
            mDebugPaint.setStyle(Paint.Style.STROKE);
            canvas.drawText("banner is here", 20, getHeight() * 2 / 3, mDebugPaint);
        }
    }

    @Override
    public void onChanged() {
        setupAdapter();
    }


    private void performSwitch() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() -
                mBannerHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY()
                - mBannerHeight);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removedView = getChildAt(0);
                mPosition++;
                mAdapter.setItem(removedView, mAdapter.getItem(mPosition % mAdapter.getCount()));
                removeView(removedView);
                addView(removedView, 1);
            }

        });
        set.setDuration(mAnimDuration);
        set.start();
    }

    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {
        @Override
        public void run() {
            performSwitch();
            postPeriod(this);
        }
    }

    //周期运行runnable
    private void postPeriod(Runnable runnable) {
        if (mRandomEnable) {
            int step = random.nextInt(mRandomStep);
            boolean sign = random.nextBoolean();
            int period = sign ? (mPeriod + step) : (mPeriod - step);
            postDelayed(runnable, period);
        } else {
            postDelayed(runnable, mPeriod);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("VerticalBannerView", "onDetachedFromWindow stop");
        stop();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}















