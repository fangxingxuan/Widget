package com.fxx.library.widget.chart.pie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxx.library.widget.common.CircleView;
import com.wsl.library.widget.R;

import java.util.List;

/**
 * 饼状图旁边的描述layout
 * Created by wsl on 17/5/26.
 */

public class PieLegendLayout extends LinearLayout{

    private LayoutInflater mInflater;


    public PieLegendLayout(Context context) {
        this(context, null);
    }

    public PieLegendLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieLegendLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        mInflater = LayoutInflater.from(context);
    }

    public void setPieData(List<IPieEntry> pieList) {
        this.removeAllViews();

        if(pieList == null || pieList.isEmpty()) {
            return;
        }

        for(IPieEntry entry : pieList) {
            View item = mInflater.inflate(R.layout.item_pie_legend, this, false);
            CircleView legendView = (CircleView) item.findViewById(R.id.view_legend);
            legendView.setCircleColor(entry.getColor());
            TextView textView = (TextView) item.findViewById(R.id.view_text);
            textView.setText(entry.getLabel());

            addView(item);
        }
    }
}