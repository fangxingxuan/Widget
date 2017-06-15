package com.fxx.library.widget.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity adater
 * Created by wsl on 17/6/5.
 */

class MainAdapter extends RecyclerView.Adapter {

    interface Listener {
        void onClickItem(int position);
    }

    private List<MainItem> mItems;
    private Context mContext;

    private Listener mListener;

    MainAdapter(Context context, List<MainItem> items) {
        mContext = context;

        mItems = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            mItems.addAll(items);
        }
    }

    void setListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new VHItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainItem item = getItem(position);

        VHItem vhItem = (VHItem) holder;


//        vhItem.divider.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        vhItem.text.setText(item.text);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private MainItem getItem(int position) {
        return mItems.get(position);
    }


    class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.divider)
        View divider;

        @BindView(R.id.text)
        TextView text;

        VHItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (mListener != null) {
                mListener.onClickItem(position);
            }
        }
    }
}
