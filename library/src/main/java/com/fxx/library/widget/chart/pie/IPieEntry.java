package com.fxx.library.widget.chart.pie;

/**
 * 数据结构
 * Created by wsl on 17/5/27.
 */

public interface IPieEntry {

    String getLabel();
    int getColor();
    float getValue();
    void setValue(float value);
}