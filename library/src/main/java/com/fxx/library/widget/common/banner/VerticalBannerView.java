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

/**
 * Created by yy on 2017/11/9
 */
public class VerticalBannerView extends LinearLayout implements BaseBannerAdapter.OnDataChangedListener {

    private static final String TAG = VerticalBannerView.class.getSimpleName();

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
    private AnimatorSet set;

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
        set = new AnimatorSet();

        array.recycle();
    }

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
        Log.d(TAG, "start");
        if (mAdapter == null) {
            throw new RuntimeException("you must call setAdapter() before start");
        }

        if (!isStarted && mAdapter.getCount() > 1) {
            isStarted = true;
            postPeriod(mRunnable);
        }
    }

    public void stop() {
        Log.d(TAG, "stop");
        set.removeAllListeners();
        set.cancel();
        if (mFirstView != null)
            mFirstView.setTranslationY(0);
        if (mSecondView != null)
            mSecondView.setTranslationY(0);
        removeCallbacks(mRunnable);
        isStarted = false;
        // requestLayout();
    }


    private void setupAdapter() {
        if (mAdapter.getCount() == 0)
            return;

        removeAllViews();

        mFirstView = null;
        mSecondView = null;
        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);

            //需要重置一下child的高度，第二个View看不见所以onMeasure中会变成0
            mFirstView.getLayoutParams().height = (int) mBannerHeight;
        } else {
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));

            addView(mFirstView);
            addView(mSecondView);

            //需要重置一下child的高度，第二个View看不见所以onMeasure中会变成0
            mFirstView.getLayoutParams().height = (int) mBannerHeight;
            mSecondView.getLayoutParams().height = (int) mBannerHeight;

            mPosition = 1;
            isStarted = false;
        }

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = (int) mBannerHeight;
        } else {
            mBannerHeight = getMeasuredHeight();
        }

        Log.d(TAG, "mBannerHeight=" + mBannerHeight + "  getMeasuredHeight=" + getMeasuredHeight());

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
        if (mFirstView == null || mSecondView == null)
            return;

        set.removeAllListeners();
        set.cancel();
        mFirstView.setTranslationY(0);
        mSecondView.setTranslationY(0);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() -
                mBannerHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY()
                - mBannerHeight);

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
        Log.d(TAG, "onDetachedFromWindow stop");
        stop();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}