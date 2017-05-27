package com.fxx.library.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;


import com.fxx.library.widget.chart.pie.IPieEntry;
import com.fxx.library.widget.chart.pie.PieLegendLayout;
import com.fxx.library.widget.chart.pie.PieDefaultEntry;
import com.fxx.library.widget.chart.pie.PieView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 测试页面
 * Created by wsl on 17/5/25.
 */

public class FXCategoryWeightActivity extends AppCompatActivity{

    private PieView pieView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fx_activity_category_weight);


        List<IPieEntry> list1 = new ArrayList<>();
        list1.add(new PieDefaultEntry(50, "安全", 0xff8bdaf9));
        list1.add(new PieDefaultEntry(20, "高效", 0xff00b7f1));
        pieView1 = (PieView) findViewById(R.id.pie1);
        pieView1.setPieData(list1);

        PieLegendLayout legendLayout = (PieLegendLayout) findViewById(R.id.layout_legend);
        legendLayout.setPieData(list1);

        final TextView ratioView = (TextView) findViewById(R.id.view_ratio);
        ratioView.setText(String.format(Locale.getDefault(), "%d%%" , 10));

        SeekBar seekBar0 = (SeekBar) findViewById(R.id.seekBar0);

        seekBar0.setProgress(50);
        seekBar0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratioView.setText(String.format(Locale.getDefault(), "%d%%" , progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pieView1.changeProgress(seekBar.getProgress(), 0);
            }
        });
    }
}
