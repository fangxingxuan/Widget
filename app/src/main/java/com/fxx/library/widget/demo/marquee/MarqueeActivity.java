package com.fxx.library.widget.demo.marquee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;
import com.fxx.library.widget.marquee.MarqueeFactory;
import com.fxx.library.widget.marquee.MarqueeView;

import java.util.Arrays;
import java.util.List;

/**
 * 跑马灯效果
 * Created by wsl on 17/7/14.
 */

public class MarqueeActivity extends BaseActivity{

    private final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。",
            "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marquee);




        attachToolbar("Marquee example");

        MarqueeView marqueeView4 = (MarqueeView) findViewById(R.id.marqueeView4);

        MarqueeFactory<TextView, String> marqueeFactory4 = new NoticeMF(this);
        marqueeFactory4.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(MarqueeActivity.this, holder.data, Toast.LENGTH_SHORT).show();
            }
        });
        marqueeFactory4.setData(datas);
//        marqueeView4.setAnimInAndOut(R.anim.marquee_top_in, R.anim.marquee_bottom_out);
        marqueeView4.setMarqueeFactory(marqueeFactory4);
        marqueeView4.startFlipping();
    }
}