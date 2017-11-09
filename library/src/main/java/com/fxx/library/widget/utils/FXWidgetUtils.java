package com.fxx.library.widget.utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wsl on 17/5/27.
 */

public final class FXWidgetUtils {


    public static float calTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -fontMetrics.ascent - fontMetrics.descent;
    }

    public static float calTextWidth(Paint paint, String demoText) {
        return paint.measureText(demoText);
    }

    public static float sp2px(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static float dp2px(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取view滚动到屏幕底部的距离
     *
     * @param view
     * @return
     */
    public static int getScrollY(Context context, View view) {
        return getYOnScreen(context, view) + view.getHeight() - getScreenHeight(context);
    }

    /**
     * 获得控件在屏幕的Y位置
     *
     * @param view
     * @return
     */
    public static int getYOnScreen(Context context, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }
}
