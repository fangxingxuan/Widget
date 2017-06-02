package com.fxx.library.widget.chart.pie;

/**
 * Created by wsl on 17/5/25.
 */

public final class PieUtils {

    public static double getArcLength(float borderWidth, float radius) {
        double degree =  getArcDegree(borderWidth, radius);
        return Math.PI * degree * radius / 180;
    }

    public static double getArcDegree(float borderWidth, float radius) {
        float tmp = borderWidth * radius / 2;

        return Math.asin(tmp);
    }

    /**
     * @param value IPieEntry value
     * @return degree about 360
     */
    public static float getSweepAngle(float value) {
        return 360 * value / 100;
    }

}