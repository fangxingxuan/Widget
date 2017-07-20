/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fxx.library.widget.slidingtab;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

class SlidingTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 2;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;

    private boolean fixNestWidth = true;
    private int mTabNestViewId;

    private int fixNestWidthPadding = 0;

    private SlidingTabLayout.TabColorizer mCustomTabColorizer;
    private final SimpleTabColorizer mDefaultTabColorizer;

    SlidingTabStrip(Context context) {
        this(context, null);
    }

    public void setTabNestViewId(int resId) {
        mTabNestViewId = resId;
    }

    SlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorForeground, outValue, true);
        final int themeForegroundColor = outValue.data;

        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new SimpleTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();
    }

    public void setFixNestWidth(boolean fixNestWidth) {
        this.fixNestWidth = fixNestWidth;
    }

    public void setFixNestWidthPadding(int padding) {
        if (padding > 0)
            this.fixNestWidthPadding = padding;
    }

    public void setBottomLineThickness(int thicknessInPx) {
        if (thicknessInPx > 0)
            mSelectedIndicatorThickness = thicknessInPx;
    }

    void setCustomTabColorizer(SlidingTabLayout.TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null ? mCustomTabColorizer :
                mDefaultTabColorizer;

        // 画底部的线
        if (childCount > 0) {
            View selectedTab = getChildAt(mSelectedPosition);
            int[] leftRight = getNestLeftRightWithPadding(selectedTab);
            int color = tabColorizer.getIndicatorColor(mSelectedPosition);

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset);
                }

                View nextTab = getChildAt(mSelectedPosition + 1);
                int[] nextLeftRight = getNestLeftRightWithPadding(nextTab);
                leftRight[0] = (int) (mSelectionOffset * nextLeftRight[0] + (1.0f - mSelectionOffset) * leftRight[0]);
                leftRight[1] = (int) (mSelectionOffset * nextLeftRight[1] + (1.0f - mSelectionOffset) * leftRight[1]);
            }

            mSelectedIndicatorPaint.setColor(color);
            if (mSelectedIndicatorThickness > getHeight()) {
                mSelectedIndicatorThickness = getHeight();
            }
            canvas.drawRect(leftRight[0], height - mSelectedIndicatorThickness, leftRight[1], height,
                    mSelectedIndicatorPaint);
        }

        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    /**
     * 若fixNestWidth == true: 计算指定嵌套View的Left、Right
     * .  若nest为空，且parent是TextView时，提取文字宽度作为嵌套宽度
     * <p>
     * 若fixNestWidth == false: 计算parent的Left、Right
     *
     * @param parent
     * @return
     */
    private int[] getNestLeftRightWithPadding(View parent) {
        int left = parent.getLeft();
        int right = parent.getRight();

        if (fixNestWidth) {
            View nest = parent.findViewById(mTabNestViewId);

            if (nest != null) {
                right = left + nest.getRight();
                left = left + nest.getLeft();
            } else {
                if (parent instanceof TextView) {
                    TextView textView = (TextView) parent;
                    try {//获取文字宽度，可以关闭，设置
                        right = (int) (left + textView.getLayout().getLineRight(0));
                        left = (int) (left + textView.getLayout().getLineLeft(0));
                    } catch (Exception e) {
                    }
                }
            }

            left = left - fixNestWidthPadding;
            if (parent.getLeft() > left) {
                left = parent.getLeft();
            }

            right = right + fixNestWidthPadding;
            if (parent.getRight() < right) {
                right = parent.getRight();
            }
        }

        return new int[]{left, right};
    }

    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer {
        private int[] mIndicatorColors;

        @Override
        public final int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        void setIndicatorColors(int... colors) {
            mIndicatorColors = colors;
        }
    }
}
