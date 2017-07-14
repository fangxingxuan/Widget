package com.fxx.library.widget.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created by binlly on 2017/4/7.
 */
public abstract class BaseCustomView extends RelativeLayout {

    public BaseCustomView(Context context) {
        super(context);
        initView(context);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, getStyleable());
        initAttributes(a);
        initView(context);
        a.recycle();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化布局
     */
    private void initView(Context context) {
        View view = View.inflate(getContext(), getLayout(), this);
        initData(context, view);
    }

    protected abstract int[] getStyleable();

    protected abstract void initAttributes(TypedArray a);

    protected abstract int getLayout();

    protected abstract void initData(Context context, View view);
}
