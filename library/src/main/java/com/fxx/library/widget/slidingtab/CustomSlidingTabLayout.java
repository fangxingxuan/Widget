package com.fxx.library.widget.slidingtab;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by binlly on 2017/5/6.
 * 底部横线自适应text文本宽度，而不是撑满整个tabView
 */

public class CustomSlidingTabLayout extends SlidingTabLayout {
    private SlidingTabStrip mTabStrip;

    public CustomSlidingTabLayout(Context context) {
        this(context, null);
    }

    public CustomSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected SlidingTabStrip getSlidingTabStrip() {
        mTabStrip = new SlidingTabStrip(getContext());
        return mTabStrip;
    }

    /**
     * 对于单个View的layoutResId不能设置nestViewId
     *
     * @param layoutResId Layout id to be inflated
     * @param nestViewId
     */
    @Override
    public void setCustomTabView(int layoutResId, int nestViewId) {
        super.setCustomTabView(layoutResId, nestViewId);
        mTabStrip.setTabNestViewId(nestViewId);
    }
}
