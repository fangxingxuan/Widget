package com.fxx.library.widget.demo.slidingtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fxx.library.widget.demo.R;

/**
 * Created by yy on 2017/7/20.
 */

public class PagerFragment extends Fragment {
    private String mText;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_tab, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text);
        textView.setText(mText);
    }

    public void setText(String text) {
        mText = text;
    }
}
