package com.fxx.library.widget.demo.banner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fxx.library.widget.common.banner.BaseBannerAdapter;
import com.fxx.library.widget.common.banner.VerticalBannerView;
import com.fxx.library.widget.demo.R;

/**
 * Created by yy on 2017/11/9.
 */

public class BannerAdapter extends BaseQuickAdapter<VerticalBannerModel, BannerAdapter.BannerHolder> {

    private Context context;

    public BannerAdapter(Context context) {
        super(R.layout.item_vertical_banner_list);
        this.context = context;
    }

    @Override
    protected void convert(BannerHolder holder, final VerticalBannerModel item) {
        holder.text.setText(item.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show();
            }
        });

        holder.banner.stop();
        if (item.banners == null || item.banners.isEmpty()) {
            holder.banner.setVisibility(View.GONE);
        } else {
            holder.banner.setVisibility(View.VISIBLE);
            holder.adapter.setData(item.banners);
            holder.banner.start();
        }
    }

    public class BannerHolder extends BaseViewHolder {
        public TextView text;
        public VerticalBannerView banner;
        public BaseBannerAdapter<BannerModel> adapter;

        public BannerHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
            banner = (VerticalBannerView) view.findViewById(R.id.banner);
            adapter = new BaseBannerAdapter<BannerModel>() {
                @Override
                public View getView(VerticalBannerView parent) {
                    return LayoutInflater.from(context).inflate(R.layout.item_vertical_banner, null);
                }

                @Override
                public void setItem(View view, final BannerModel data) {
                    TextView text_nick = (TextView) view.findViewById(R.id.text_nick);
                    TextView text_content = (TextView) view.findViewById(R.id.text_content);

                    text_nick.setText(data.nick);
                    text_content.setText(data.content);
                    text_content.setTextColor(data.color);

                    Log.d("VerticalBannerView", "setItem " + data.nick);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, data.nick, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            banner.setAdapter(adapter);
        }
    }
}

