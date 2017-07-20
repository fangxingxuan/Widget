package com.fxx.library.widget.demo.slidingtab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;
import com.fxx.library.widget.slidingtab.ExtendSlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yy on 2017/7/20.
 */

public class SlidingTabActivity extends BaseActivity {

    @BindView(R.id.sliding_tab1) ExtendSlidingTabLayout sliding_tab1;
    @BindView(R.id.pager1) ViewPager pager1;
    @BindView(R.id.sliding_tab2) ExtendSlidingTabLayout sliding_tab2;
    @BindView(R.id.pager2) ViewPager pager2;
    @BindView(R.id.sliding_tab3) ExtendSlidingTabLayout sliding_tab3;
    @BindView(R.id.pager3) ViewPager pager3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sliding_tab);
        ButterKnife.bind(this);

        attachToolbar("SlidingTab");

        float density = getResources().getDisplayMetrics().density;
        PagerAdapter pagerAdapter1 = new PagerAdapter(getSupportFragmentManager());
        PagerAdapter pagerAdapter2 = new PagerAdapter(getSupportFragmentManager());
        PagerAdapter pagerAdapter3 = new PagerAdapter(getSupportFragmentManager());

        pager1.setAdapter(pagerAdapter1);
        pager1.setOffscreenPageLimit(3);
        pager2.setAdapter(pagerAdapter2);
        pager2.setOffscreenPageLimit(3);
        pager3.setAdapter(pagerAdapter3);
        pager3.setOffscreenPageLimit(3);

        sliding_tab1.setDistributeEvenly(true);
        sliding_tab1.setCustomTabView(R.layout.sliding_tab_view, R.id.text);
        sliding_tab1.setFixNestWidth(false);
        sliding_tab1.setSelectedIndicatorColors(Color.rgb(0xe0, 0x00, 0x1e));
        sliding_tab1.setViewPager(pager1);

        sliding_tab2.setDistributeEvenly(true);
        sliding_tab2.setCustomTabView(R.layout.sliding_tab_textview, 0);
        sliding_tab2.setFixNestWidth(true);
        sliding_tab2.setFixNestWidthPadding((int) (4 * density));
        sliding_tab2.setSelectedIndicatorColors(Color.rgb(0xe0, 0x00, 0x1e));
        sliding_tab2.setViewPager(pager2);

        sliding_tab3.setDistributeEvenly(true);
        sliding_tab3.setBottomLineThickness((int) (4 * density));
        sliding_tab3.setCustomTabView(R.layout.sliding_tab_view, R.id.text);
        sliding_tab3.setSelectedIndicatorColors(Color.rgb(0xe0, 0x00, 0x1e), Color.rgb(0x1e, 0xe0, 0x00), Color.rgb
                (0x00, 0x1e, 0xe0));
        sliding_tab3.setViewPager(pager3);
    }
}
