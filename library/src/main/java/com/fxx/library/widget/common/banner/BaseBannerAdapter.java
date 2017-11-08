package com.fxx.library.widget.common.banner;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class BaseBannerAdapter<T> {
    private List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public BaseBannerAdapter() {
    }

    /**
     * 设置banner填充的数据
     */
    public void setData(List<T> datas) {
        if (datas == null)
            mDatas = new ArrayList<>();
        else
            mDatas = datas;
        notifyDataChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 设置banner的样式
     */
    public abstract View getView(VerticalBannerView parent);

    /**
     * 设置banner的数据
     */
    public abstract void setItem(View view, T data);


    interface OnDataChangedListener {
        void onChanged();
    }
}