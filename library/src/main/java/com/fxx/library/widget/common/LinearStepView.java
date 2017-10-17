package com.fxx.library.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fxx.library.widget.R;
import com.fxx.library.widget.base.BaseCustomView;
import com.fxx.library.widget.utils.FXWidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2017/10/11.
 */

public class LinearStepView<T> extends BaseCustomView {

    // <attr name="lsvGapFixed" format="boolean"/>  <!-- step之间的间隔是否固定，固定则可滚动，否则match_parent -->
    // <attr name="lsvGapSize" format="dimension"/> <!-- step之间的间隔 -->
    // <attr name="lsvSteppedDrawable" format="reference"/> <!-- 已完成step样式 -->
    // <attr name="lsvSteppingDrawable" format="reference"/> <!-- 进行中step样式 -->
    // <attr name="lsvStepDefaultDrawable" format="reference"/> <!-- 默认step样式 -->
    // <attr name="lsvStepMax" format="integer"/> <!-- 所有step数目 -->
    // <attr name="lsvStepCurrentPos" format="integer"/> <!-- 当前选中step位置 -->
    // <attr name="lsvSteppedLineColor" format="color"/> <!-- 已完成step线的颜色 -->
    // <attr name="lsvStepLineColor" format="color"/> <!-- 默认step线的颜色 -->
    // <attr name="lsvSteppedTextColor" format="color"/> <!-- 已完成step描述文字颜色 -->
    // <attr name="lsvSteppingTextColor" format="color"/> <!-- 进行中step描述文字颜色 -->
    // <attr name="lsvStepDefaultTextColor" format="color"/> <!-- 默认step描述文字颜色 -->

    private final float GAP_SIZE = FXWidgetUtils.dp2px(80, getContext());
    private final int GAP_MARGIN = (int) FXWidgetUtils.dp2px(10, getContext());
    private final int TOP_MARGIN = (int) FXWidgetUtils.dp2px(12, getContext());
    private final int STEP_VIEW_SIZE = (int) FXWidgetUtils.dp2px(17, getContext());
    private static final int STEP_MAX = 5;
    private static final int CURRENT_POS = 0;
    private static final int RED_COLOR = Color.rgb(0xe0, 0x00, 0x1e);
    private static final int GRAY_COLOR_LIGHT = Color.rgb(0xea, 0xea, 0xea);
    private static final int GRAY_COLOR = Color.rgb(0x99, 0x99, 0x99);
    private static final int GRAY_COLOR_DARK = Color.rgb(0x46, 0x46, 0x46);

    private boolean gapFixed = false;
    private float gapSize = GAP_SIZE;
    private int gapMargin = GAP_MARGIN;
    private int stepLineHeight = (int) FXWidgetUtils.dp2px(2, getContext());
    private Drawable steppedDrawable;
    private Drawable steppingDrawable;
    private Drawable stepDrawable;
    private int stepMax = STEP_MAX;
    private int currentPos = CURRENT_POS;
    private int lastPos = -1;
    private View lastAdditionalView = null;
    private int steppedLineColor = RED_COLOR;
    private int stepLineColor = GRAY_COLOR_LIGHT;

    private StepAdapter<T> stepAdapter;
    private ProgressBar progress;
    private HorizontalScrollView h_scroll;
    private LinearLayout layout_content;

    private StepDataObserver dataObserver;

    public LinearStepView(Context context) {
        super(context);
    }

    public LinearStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] getStyleable() {
        return R.styleable.LinearStepView;
    }

    @Override
    protected void initAttributes(TypedArray a) {
        gapFixed = a.getBoolean(R.styleable.LinearStepView_lsvGapFixed, false);
        gapSize = a.getDimension(R.styleable.LinearStepView_lsvGapSize, GAP_SIZE);
        stepMax = a.getInt(R.styleable.LinearStepView_lsvStepMax, STEP_MAX);
        currentPos = a.getInt(R.styleable.LinearStepView_lsvStepCurrentPos, CURRENT_POS);
        steppedLineColor = a.getColor(R.styleable.LinearStepView_lsvSteppedLineColor, RED_COLOR);
        stepLineColor = a.getColor(R.styleable.LinearStepView_lsvStepLineColor, GRAY_COLOR_LIGHT);
        steppedDrawable = a.getDrawable(R.styleable.LinearStepView_lsvSteppedDrawable);
        steppingDrawable = a.getDrawable(R.styleable.LinearStepView_lsvSteppingDrawable);
        stepDrawable = a.getDrawable(R.styleable.LinearStepView_lsvStepDefaultDrawable);

        if (steppedDrawable == null) {
            steppedDrawable = getResources().getDrawable(R.drawable.shape_oval_green);
        }
        if (steppingDrawable == null) {
            steppingDrawable = getResources().getDrawable(R.drawable.shape_oval_red);
        }
        if (stepDrawable == null) {
            stepDrawable = getResources().getDrawable(R.drawable.shape_oval_gray);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.widget_linear_step_view;
    }

    @Override
    protected void initData(Context context, View view) {
        progress = (ProgressBar) view.findViewById(R.id.progress);
        h_scroll = (HorizontalScrollView) view.findViewById(R.id.h_scroll);
        layout_content = (LinearLayout) view.findViewById(R.id.layout_content);

        // TODO: 2017/10/11 第一版暂时没有实现
        h_scroll.setFillViewport(!gapFixed);

        updateProgressDrawable();
        setStepLineHeight((int) FXWidgetUtils.dp2px(8, getContext()));

        dataObserver = new StepDataObserver();
    }

    //计算当前步骤在全局中的比例，比如当前(0,1,2,3,4)=2，一共5，则刚好进行到中间
    private float getRatio() {
        if (currentPos <= 0 || stepMax == 1)
            return 0;
        return (float) currentPos / (stepMax - 1);
    }

    public void setSteppedLineColor(int color) {
        steppedLineColor = color;
        updateProgressDrawable();
    }

    public void setStepLineColor(int color) {
        stepLineColor = color;
        updateProgressDrawable();
    }

    //要先设置Max再设置Current，否则可能得到不正确的Current
    private void setStepMax(int max) {
        if (max < 1)
            max = 1;
        stepMax = max;
        updateProgressDrawable();
    }

    //从0开始
    public void setStepCurrent(int currentPos) {
        if (currentPos < 0)
            currentPos = 0;
        // if (currentPos >= stepMax)
        //     currentPos = stepMax;
        this.currentPos = currentPos;
        if (stepAdapter != null) {
            stepAdapter.notifyChanged();
        }
    }

    private void updateProgressDrawable() {
        progress.setBackgroundDrawable(new ColorDrawable(stepLineColor));
        ClipDrawable clipDrawable = new ClipDrawable(new ColorDrawable(steppedLineColor), Gravity.LEFT, ClipDrawable
                .HORIZONTAL);
        //一定要先设置Drawable再设置Level
        progress.setProgressDrawable(clipDrawable);
        clipDrawable.setLevel((int) (getRatio() * 10000));
        int progress = (stepMax - 1) > 0 ? (stepMax - 1) : 0;
        this.progress.setMax(progress);
        this.progress.setProgress(currentPos);
    }

    public void setStepLineHeight(int height) {
        stepLineHeight = height;
        ViewGroup.LayoutParams layoutParams = progress.getLayoutParams();
        layoutParams.height = height;
        progress.setLayoutParams(layoutParams);
    }

    public void setSteppedDrawable(Drawable steppedDrawable) {
        this.steppedDrawable = steppedDrawable;
    }

    public void setSteppingDrawable(Drawable steppingDrawable) {
        this.steppingDrawable = steppingDrawable;
    }

    public void setStepDrawable(Drawable stepDrawable) {
        this.stepDrawable = stepDrawable;
    }

    public void setAdapter(@NonNull StepAdapter adapter) {
        if (stepAdapter != null) {
            stepAdapter.unregisterObserver(dataObserver);
        }
        stepAdapter = adapter;
        adapter.registerObserver(dataObserver);
        adapter.notifyChanged();
    }

    private class StepDataObserver extends DataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            layout_content.removeAllViews();
            stepMax = stepAdapter.steps.size();
            for (int i = 0; i < stepMax; i++) {
                T data = stepAdapter.steps.get(i);
                //绑定当前step，返回一个附加View（可以为null），添加到当前step中去
                View additionalView = stepAdapter.onBindStep(data, i);
                addViewToStep(i, additionalView);
                if (currentPos == i) {//通知当前选中的step
                    stepAdapter.onStepSelected(additionalView, lastAdditionalView, i, lastPos);
                    lastPos = currentPos;
                    lastAdditionalView = additionalView;
                }
            }

            post(new Runnable() {
                @Override
                public void run() {
                    int w = getWidth() - getPaddingLeft() - getPaddingRight();
                    int stepWidth = w / stepMax;
                    int H = stepWidth / 2;
                    int V = STEP_VIEW_SIZE / 2 - stepLineHeight / 2;
                    H = H < 0 ? 0 : H;
                    V = V < 0 ? 0 : V;
                    MarginLayoutParams layoutParams = (MarginLayoutParams) progress.getLayoutParams();
                    layoutParams.leftMargin = H;
                    layoutParams.rightMargin = H;
                    layoutParams.topMargin = V;
                    layoutParams.bottomMargin = V;
                    progress.setLayoutParams(layoutParams);
                    updateProgressDrawable();
                }
            });
        }
    }

    private void addViewToStep(final int pos, final View additionalView) {
        final LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams;
        layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.weight = 1;
        layout_content.addView(layout, layoutParams);
        addCenterStepView(pos, layout, additionalView);
        // if (stepMax == 1) {
        //     addCenterStepView(layout, additionalView);
        //     layoutParams = new LinearLayout.LayoutParams(-1, -2);
        //     layoutParams.gravity = Gravity.CENTER;
        //     layout_content.addView(layout, layoutParams);
        // } else if (stepMax == 2) {
        //     addCenterStepView(layout, additionalView);
        //     layoutParams = new LinearLayout.LayoutParams(0, -2);
        //     layoutParams.weight = 1;
        //     layoutParams.gravity = Gravity.CENTER;
        //     layout_content.addView(layout, layoutParams);
        // } else {
        //     int space = width - 2 * STEP_VIEW_SIZE;//除去两头中间剩余的长度
        //     int parts = stepMax - 2 + 1;//中间需要等分的份数
        //     int len = space / parts;
        //
        //     if (pos == 1) {
        //
        //     } else if (pos == stepMax - 1) {
        //
        //     } else {
        //
        //     }
        // }
    }

    private void addCenterStepView(final int pos, LinearLayout layout, final View additionalView) {
        final View step = new View(getContext());
        if (pos > currentPos) {
            step.setBackgroundDrawable(stepDrawable);
        } else if (pos < currentPos) {
            step.setBackgroundDrawable(steppedDrawable);
        } else {
            step.setBackgroundDrawable(steppingDrawable);
        }
        //点击某个步骤切换
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == lastPos)
                    return;

                if (pos > currentPos) {
                    return;
                }

                stepAdapter.onStepSelected(additionalView, lastAdditionalView, pos, lastPos);
                lastPos = pos;
                lastAdditionalView = additionalView;
            }
        });

        layout.setGravity(Gravity.CENTER);
        layout.addView(step);
        LinearLayout.LayoutParams stepParams = (LinearLayout.LayoutParams) step.getLayoutParams();
        stepParams.width = STEP_VIEW_SIZE;
        stepParams.height = STEP_VIEW_SIZE;
        step.setLayoutParams(stepParams);
        if (additionalView != null) {
            layout.addView(additionalView);
            LinearLayout.LayoutParams additionalParams = (LinearLayout.LayoutParams) additionalView.getLayoutParams();
            additionalParams.topMargin = TOP_MARGIN;
            additionalView.setLayoutParams(additionalParams);
        }
    }

    static abstract class DataObserver extends Observable {
        public void onChanged() {
            // do nothing
        }
    }

    public static class DataObservable extends Observable<DataObserver> {
        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    public abstract static class StepAdapter<T> {
        private List<T> steps = new ArrayList<>();
        private final DataObservable observable = new DataObservable();

        public void setSteps(List<T> steps) {
            if (steps != null) {
                this.steps = steps;
            } else {
                this.steps = new ArrayList<>();
            }
            notifyChanged();
        }

        public List<T> getSteps() {
            return steps;
        }

        public T getStep(int pos) {
            if (steps == null || steps.isEmpty()) {
                return null;
            }
            if (pos < 0 || pos >= steps.size()) {
                return null;
                // throw new IndexOutOfBoundsException("get step index " + pos + ", current size " + steps.size() +
                // "!");
            }
            return steps.get(pos);
        }

        public void registerObserver(DataObserver observer) {
            observable.registerObserver(observer);
        }

        public void unregisterObserver(DataObserver observer) {
            observable.unregisterObserver(observer);
        }

        public void notifyChanged() {
            observable.notifyChanged();
        }

        //绑定stepView，需要外部创建新的View
        abstract public View onBindStep(T t, int position);

        //选中某个step
        abstract public void onStepSelected(View view, View lastView, int currentPos, int lastPos);
    }

    public boolean isGapFixed() {
        return gapFixed;
    }

    public float getGapSize() {
        return gapSize;
    }

    public int getGapMargin() {
        return gapMargin;
    }

    public int getStepLineHeight() {
        return stepLineHeight;
    }

    public int getStepMax() {
        return stepMax;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getLastPos() {
        return lastPos;
    }

    public View getLastAdditionalView() {
        return lastAdditionalView;
    }

    public StepAdapter<T> getStepAdapter() {
        return stepAdapter;
    }

    public static class Observable<T> {
        protected final ArrayList<T> mObservers = new ArrayList<T>();

        public void registerObserver(T observer) {
            if (observer == null) {
                throw new IllegalArgumentException("The observer is null.");
            }
            synchronized (mObservers) {
                if (mObservers.contains(observer)) {
                    throw new IllegalStateException("Observer " + observer + " is already registered.");
                }
                mObservers.add(observer);
            }
        }

        public void unregisterObserver(T observer) {
            if (observer == null) {
                throw new IllegalArgumentException("The observer is null.");
            }
            synchronized (mObservers) {
                int index = mObservers.indexOf(observer);
                if (index == -1) {
                    throw new IllegalStateException("Observer " + observer + " was not registered.");
                }
                mObservers.remove(index);
            }
        }

        public void unregisterAll() {
            synchronized (mObservers) {
                mObservers.clear();
            }
        }
    }
}
