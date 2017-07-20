package com.fxx.library.widget.demo.slidingtab;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yy on 2017/7/20.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private static final String[] titles = new String[]{"T0", "Title_1", "T2", "Title_3"};
    private PagerFragment[] fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new PagerFragment[]{new PagerFragment(), new PagerFragment(), new PagerFragment(), new
                PagerFragment()};
    }

    @Override
    public PagerFragment getItem(int position) {
        PagerFragment fragment = fragments[position];
        fragment.setText("我是" + position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
