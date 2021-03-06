package com.fxx.library.widget.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fxx.library.widget.demo.avatargroup.AvatarGroupActivity;
import com.fxx.library.widget.demo.banner.VerticalBannerActivity;
import com.fxx.library.widget.demo.follow.FollowableActivity;
import com.fxx.library.widget.demo.linearstep.LinearStepActivity;
import com.fxx.library.widget.demo.shape.ShapeActivity;
import com.fxx.library.widget.demo.slidingtab.SlidingTabActivity;
import com.fxx.library.widget.demo.text.TextLineSpaceActivity;
import com.fxx.library.widget.demo.weight.WeightActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainAdapter.Listener {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        attachToolbar("Widget Activity", false);

        List<MainItem> items = new ArrayList<>();
        items.add(new MainItem("Weight"));
        items.add(new MainItem("Shape"));
        items.add(new MainItem("Text Padding"));
        items.add(new MainItem("Followable View"));
        items.add(new MainItem("SlidingTab"));
        items.add(new MainItem("LinearStepView"));
        items.add(new MainItem("AvatarGroup"));
        items.add(new MainItem("VerticalBanner"));

        MainAdapter adapter = new MainAdapter(this, items);
        adapter.setListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                intentToActivity(WeightActivity.class);
                break;
            case 1:
                intentToActivity(ShapeActivity.class);
                break;
            case 2:
                intentToActivity(TextLineSpaceActivity.class);
                break;
            case 3:
                intentToActivity(FollowableActivity.class);
                break;
            case 4:
                intentToActivity(SlidingTabActivity.class);
                break;
            case 5:
                intentToActivity(LinearStepActivity.class);
                break;
            case 6:
                intentToActivity(AvatarGroupActivity.class);
                break;
            case 7:
                intentToActivity(VerticalBannerActivity.class);
                break;
            default:
                break;
        }
    }

    private void intentToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
