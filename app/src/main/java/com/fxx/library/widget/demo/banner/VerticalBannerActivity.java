package com.fxx.library.widget.demo.banner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fxx.library.widget.common.banner.BaseBannerAdapter;
import com.fxx.library.widget.common.banner.VerticalBannerView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yy on 2017/10/11.
 */

public class VerticalBannerActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = VerticalBannerActivity.class.getSimpleName();

    private Button bt_reset;
    private Button bt_all;
    private Button bt0;
    private Button bt1;
    private Button bt2;
    private VerticalBannerView banner0;
    private VerticalBannerView banner1;
    private VerticalBannerView banner2;

    private Random random;
    private BaseBannerAdapter<BannerModel> adapter0;
    private BaseBannerAdapter<BannerModel> adapter1;
    private BaseBannerAdapter<BannerModel> adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_banner);

        final Toolbar toolbar = attachToolbar("VerticalBannerView");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_list:
                        startActivity(new Intent(VerticalBannerActivity.this, VerticalBannerListActivity.class));
                        break;

                    case R.id.action_hehe:
                        Toast.makeText(VerticalBannerActivity.this, "hehe", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        random = new Random();

        bt_reset = (Button) findViewById(R.id.button_reset);
        bt_all = (Button) findViewById(R.id.button_all);
        bt0 = (Button) findViewById(R.id.button0);
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt_reset.setOnClickListener(this);
        bt_all.setOnClickListener(this);
        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        banner0 = (VerticalBannerView) findViewById(R.id.banner0);
        banner1 = (VerticalBannerView) findViewById(R.id.banner1);
        banner2 = (VerticalBannerView) findViewById(R.id.banner2);

        adapter0 = newAdapter();
        adapter1 = newAdapter();
        adapter2 = newAdapter();
        banner0.setAdapter(adapter0);
        banner1.setAdapter(adapter1);
        banner2.setAdapter(adapter2);

        adapter0.setData(getDatas(5));
        adapter1.setData(getDatas(4));
        adapter2.setData(getDatas(3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vertical_banner, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                if ("开始0".equals(bt0.getText())) {
                    banner0.start();
                    bt0.setText("停止0");
                } else {
                    banner0.stop();
                    bt0.setText("开始0");
                }
                break;

            case R.id.button1:
                if ("开始1".equals(bt1.getText())) {
                    banner1.start();
                    bt1.setText("停止1");
                } else {
                    banner1.stop();
                    bt1.setText("开始1");
                }
                break;

            case R.id.button2:
                if ("开始2".equals(bt2.getText())) {
                    banner2.start();
                    bt2.setText("停止2");
                } else {
                    banner2.stop();
                    bt2.setText("开始2");
                }
                break;

            case R.id.button_all:
                if ("全部开始".equals(bt_all.getText())) {
                    banner0.start();
                    banner1.start();
                    banner2.start();
                    bt_all.setText("全部停止");
                } else {
                    banner0.stop();
                    banner1.stop();
                    banner2.stop();
                    bt_all.setText("全部开始");
                }
                break;

            case R.id.button_reset:
                banner0.stop();
                banner1.stop();
                banner2.stop();
                adapter0.setData(getDatas(5));
                adapter1.setData(getDatas(4));
                adapter2.setData(getDatas(3));
                banner0.start();
                banner1.start();
                banner2.start();
                break;

            default:
                break;
        }
    }

    private BaseBannerAdapter<BannerModel> newAdapter() {
        return new BaseBannerAdapter<BannerModel>() {
            @Override
            public View getView(VerticalBannerView parent) {
                return LayoutInflater.from(VerticalBannerActivity.this).inflate(R.layout.item_vertical_banner, null);
            }

            @Override
            public void setItem(View view, BannerModel data) {
                TextView text_nick = (TextView) view.findViewById(R.id.text_nick);
                TextView text_content = (TextView) view.findViewById(R.id.text_content);

                text_nick.setText(data.nick);
                text_content.setText(data.content);
                text_content.setTextColor(data.color);

                Log.d("TAG", "setItem " + data.nick + " " + view);
            }
        };
    }

    private int getRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private List<BannerModel> getDatas(int size) {
        List<BannerModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            BannerModel model = new BannerModel();
            model.color = getRandomColor();
            model.nick = "我是名字 " + i;
            model.content = "我是内容我是内容我是内容我是内容我是内容 " + i;
            list.add(model);
        }
        return list;
    }
}
