package com.fxx.library.widget.chart.pie;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

/**
 * 饼状图下面的自定义SeekBar
 * Created by wsl on 17/5/27.
 */

public class PieSeekBar extends AppCompatSeekBar {


    public PieSeekBar(Context context) {
        this(context, null);
    }

    public PieSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 21) {
            setSplitTrack(false);
        }
    }
}
