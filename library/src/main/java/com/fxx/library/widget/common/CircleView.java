package com.fxx.library.widget.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 饼状图右边的描述圆点View
 * Created by wsl on 17/5/26.
 */

public class CircleView extends View {

    private Paint mCirclePaint;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setDither(true);
        mCirclePaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth() / 2;
        float cy = getHeight() / 2;
        int radius = (int) cx;
        canvas.drawCircle(cx, cy, radius, mCirclePaint);
    }

    public void setCircleColor(int color) {
        this.mCirclePaint.setColor(color);
//        invalidate();
    }

}