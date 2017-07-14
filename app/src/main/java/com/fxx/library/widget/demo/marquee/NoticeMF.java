package com.fxx.library.widget.demo.marquee;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.fxx.library.widget.demo.R;
import com.fxx.library.widget.marquee.MarqueeFactory;

public class NoticeMF extends MarqueeFactory<TextView, String> {
    private LayoutInflater inflater;

    public NoticeMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(String data) {
        TextView mView = (TextView) inflater.inflate(R.layout.layout_marquee_notice_item, null);
        mView.setText(data);
        return mView;
    }
}