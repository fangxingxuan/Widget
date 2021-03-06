package com.fxx.library.widget.demo.weight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fxx.library.widget.chart.pie.IPieEntry;
import com.fxx.library.widget.chart.pie.PieDefaultEntry;
import com.fxx.library.widget.chart.pie.PieLegendLayout;
import com.fxx.library.widget.chart.pie.PieView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试页面
 * Created by wsl on 17/5/25.
 */

public class WeightActivity extends BaseActivity{

    @BindView(R.id.pie1)
    PieView pieView1;

    private static final int FIRST_DEFAULT_VALUE = 50;
    private static final int SECOND_DEFAULT_VALUE = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        attachToolbar("Weight example");


        List<IPieEntry> list1 = new ArrayList<>();
        list1.add(new PieDefaultEntry(FIRST_DEFAULT_VALUE, "安全", 0xff8bdaf9));
        list1.add(new PieDefaultEntry(SECOND_DEFAULT_VALUE, "高效", 0xff00b7f1));

//        pieView1.setCenterTextSize((int) FXWidgetUtils.sp2px(20, this));
        pieView1.setPieData(list1);

        PieLegendLayout legendLayout = (PieLegendLayout) findViewById(R.id.layout_legend);
        legendLayout.setPieData(list1);

        final TextView ratioView = (TextView) findViewById(R.id.view_ratio);
        ratioView.setText(String.format(Locale.getDefault(), "%d%%" , 10));

        SeekBar seekBar0 = (SeekBar) findViewById(R.id.seekBar0);

        seekBar0.setProgress(FIRST_DEFAULT_VALUE);
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

        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setProgress(SECOND_DEFAULT_VALUE);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                ratioView.setText(String.format(Locale.getDefault(), "%d%%" , progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pieView1.changeProgress(seekBar.getProgress(), 1);
            }
        });
    }
}
