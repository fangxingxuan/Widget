package com.fxx.library.widget.utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;

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
}
