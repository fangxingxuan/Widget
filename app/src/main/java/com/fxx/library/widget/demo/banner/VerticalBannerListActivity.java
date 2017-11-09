package com.fxx.library.widget.demo.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yy on 2017/10/11.
 */

public class VerticalBannerListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = VerticalBannerListActivity.class.getSimpleName();

    @BindView(R.id.swipe) SwipeRefreshLayout swipe;
    @BindView(R.id.recycler) RecyclerView recycler;

    private BannerAdapter adapter;
    private Random random;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_banner_list);
        ButterKnife.bind(this);

        attachToolbar("VerticalBannerList");

        swipe.setOnRefreshListener(this);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BannerAdapter(this);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this, recycler);
        recycler.setAdapter(adapter);

        random = new Random();

        onRefresh();
    }

    @Override
    public void onRefresh() {
        adapter.setNewData(getDatas(10));
        swipe.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        adapter.addData(getDatas(10));
        if (adapter.getData().size() > 40) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

    private List<VerticalBannerModel> getDatas(int size) {
        List<VerticalBannerModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            VerticalBannerModel model = new VerticalBannerModel();
            model.name = "第 " + i + " 个banner";
            model.banners = getBanners(random.nextInt(4));
            list.add(model);
        }
        return list;
    }

    private int getRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private List<BannerModel> getBanners(int size) {
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
