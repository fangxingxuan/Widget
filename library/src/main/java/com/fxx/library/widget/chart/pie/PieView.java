package com.fxx.library.widget.chart.pie;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fxx.library.widget.R;
import com.fxx.library.widget.utils.FXWidgetUtils;

import java.util.List;

/**
 * 饼状图
 * Created by wsl on 17/5/25.
 */

public class PieView extends View {

    public interface Listener {
        //大于100%
        void onGreater();
        //小于100%
        void onLess();
    }

    private Listener mListener;

    //大圆区域，辅助画扇形
    private RectF mBigCircleBox = new RectF();
    private Path mPathBuffer = new Path();
    private Paint mRenderPaint;

    //超过100%
    private Paint mCenterTextPaint;

    //数据
    private List<IPieEntry> mPieList;

    private Rect mTestRect = new Rect();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    private void init(Context context, AttributeSet attrs) {
        int centerTextSize = (int) FXWidgetUtils.sp2px(15f, getContext());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PieView);
        centerTextSize = a.getDimensionPixelOffset(R.styleable.PieView_fx_center_text_size, centerTextSize);
        a.recycle();

        mRenderPaint = new Paint();
        mRenderPaint.setDither(true);
        mRenderPaint.setAntiAlias(true);
        mRenderPaint.setStyle(Paint.Style.FILL);

        mCenterTextPaint = new Paint();
        mCenterTextPaint.setDither(true);
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setColor(Color.WHITE);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);
        mCenterTextPaint.setTextSize(centerTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int size = Math.min(w, h);
        if (size == 0) {
            return;
        }
        mBigCircleBox.set(0, 0, size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPie(canvas);
    }

    private void drawPie(Canvas canvas) {
        if (isEmpty()) {
            return;
        }

        float totalValue = getTotalValue();

        if (totalValue != 100) {
            //不等于100，画默认图, 文案分2种
            mPathBuffer.reset();
            mRenderPaint.setColor(0xff999999);
            mPathBuffer.addCircle(mBigCircleBox.centerX(), mBigCircleBox.centerY(),
                    mBigCircleBox.width() / 2, Path.Direction.CW);
            canvas.drawPath(mPathBuffer, mRenderPaint);
            drawCenterText(canvas, totalValue);
            return;
        }

        int size = mPieList.size();
        //12点开始，默认是0，从3点开始
        float startAngle = 270f;
        //上一次的度数
        float lastSweepAngle = 0;

        for (int i = 0; i < size; i++) {
            IPieEntry pieEntry = mPieList.get(i);
            if(pieEntry.getValue() == 0) {
                continue;
            }
            mPathBuffer.reset();
            mRenderPaint.setColor(pieEntry.getColor());
            if(pieEntry.getValue() == 100) {
                //等于360度直接画个圆
                mPathBuffer.addCircle(mBigCircleBox.centerX(), mBigCircleBox.centerY(),
                        mBigCircleBox.width() / 2, Path.Direction.CW);
                canvas.drawPath(mPathBuffer, mRenderPaint);
                break;
            }

            startAngle = (startAngle + lastSweepAngle) % 360;
            float sweepAngle = PieUtils.getSweepAngle(pieEntry.getValue());

            //handle path, 画扇形
            mPathBuffer.moveTo(mBigCircleBox.centerX(), mBigCircleBox.centerY());
            mPathBuffer.arcTo(mBigCircleBox, startAngle, sweepAngle);

            mPathBuffer.close();

            canvas.drawPath(mPathBuffer, mRenderPaint);

            lastSweepAngle = sweepAngle;
        }

        //需求更改，不填充了,先注释掉
        /*
        if(totalValue < 100) {
            //填充一个剩余的部分
            float paddingAngle = (100 - totalValue ) / 100 * 360;
            startAngle = (startAngle + lastSweepAngle) % 360;
            mPathBuffer.reset();
            mRenderPaint.setColor(getPaddingColor());
            mPathBuffer.moveTo(mBigCircleBox.centerX(), mBigCircleBox.centerY());
            mPathBuffer.arcTo(mBigCircleBox, startAngle, paddingAngle);
            mPathBuffer.close();
            canvas.drawPath(mPathBuffer, mRenderPaint);
        }
        */
    }

    private void drawCenterText(Canvas canvas , float totalValue) {
        final String text = getCenterText(totalValue);
        String firstLine = text.substring(0, text.length() - 4);
        String secondLine = text.substring(text.length() - 4);

//        float textHeight = PieUtils.calTextHeight(mCenterTextPaint);
        // 相比于getTextBounds值小了10px,展示上没有padding,所以这个地方采用getTextBounds
        mCenterTextPaint.getTextBounds(firstLine, 0, firstLine.length() - 1 , mTestRect);
        float textHeight = mTestRect.height();

        float cx = mBigCircleBox.centerX();
        float cy = mBigCircleBox.centerY();

        //draw first line text
        canvas.drawText(firstLine, cx, cy, mCenterTextPaint);
        canvas.drawText(secondLine, cx, cy + textHeight, mCenterTextPaint);
    }



    private boolean isEmpty() {
        return mPieList == null || mPieList.isEmpty();
    }

    /**
     * 数据改变后需要检查回调
     */
    public void setPieData(List<IPieEntry> pieList) {
        this.mPieList = pieList;
        invalidate();
        checkPieState();
    }

    /**
     * 数据改变后需要检查回调
     */
    public void changeProgress(int value, int index) {
        if(isEmpty()) {
            return;
        }
        try {
            IPieEntry pieEntry = mPieList.get(index);
            pieEntry.setValue(value);
            invalidate();
            checkPieState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCenterText(float totalValue) {
        if(totalValue > 100) {
            return "权重之和大于100%";
        } else if(totalValue < 100){
            return "权重之和小于100%";
        } else {
            return "";
        }
    }

    private int getPaddingColor() {
        return 0xffededed;
    }

    private float getTotalValue() {
        int sum = 0;
        for (IPieEntry bean : mPieList) {
            sum += bean.getValue();
        }
        return sum;
    }

    /**
     * 是否超过100%
     */
    private void checkPieState() {
        float total =  getTotalValue();
        if(total > 100f) {
            if(mListener != null) {
                mListener.onGreater();
            }
        } else if(total < 100f) {
            if(mListener != null) {
                mListener.onLess();
            }
        }
    }

    public void setCenterTextSize(int size) {
        mCenterTextPaint.setTextSize(size);
    }
}