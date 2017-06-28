package com.fxx.library.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxx.library.widget.R;
import com.fxx.library.widget.base.BaseCustomView;

/**
 * Created by binlly on 2017/6/27.
 * 关注、取消关注等样式按钮
 */

public class FollowableView extends BaseCustomView {

    private int mFollowedType = 0;
    private FollowClickListener mFollowClickListener;

    private Drawable followedDrawable;
    private Drawable unfollowedDrawable;
    private Drawable eachfollowedDrawable;
    private Drawable leftDrawable;
    private int followedTextColor;
    private int unfollowedTextColor;
    private int eachfollowedTextColor;
    private String followedText;
    private String unfollowedText;
    private String eachfollowedText;

    private static final String FOLLOWED_TEXT = "已关注";
    private static final String UNFOLLOWED_TEXT = "关注";
    private static final String EACHFOLLOWED_TEXT = "互相关注";

    private static final int FOLLOWED_TEXT_COLOR = Color.rgb(0x46, 0x46, 0x46);
    private static final int UNFOLLOWED_TEXT_COLOR = Color.rgb(0xe2, 0x00, 0x1e);
    private static final int EACHFOLLOWED_TEXT_COLOR = Color.rgb(0x46, 0x46, 0x46);

    //未关注
    public static final int FOLLOWED_TYPE_UNFOLLOWED = 1;
    //已关注
    public static final int FOLLOWED_TYPE_FOLLOWED = 2;
    //相互关注
    public static final int FOLLOWED_TYPE_EACHOTHER = 4;

    private ImageView image_left;
    private TextView text;

    public interface FollowClickListener {
        void onFollowClick(int followedType);
    }

    public FollowableView(Context context) {
        super(context);
    }

    public FollowableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] getStyleable() {
        return R.styleable.FollowableView;
    }

    @Override
    protected void initAttributes(TypedArray a) {
        followedDrawable = a.getDrawable(R.styleable.FollowableView_fvFollowedBackground);
        unfollowedDrawable = a.getDrawable(R.styleable.FollowableView_fvUnFollowedBackground);
        eachfollowedDrawable = a.getDrawable(R.styleable.FollowableView_fvEachFollowedBackground);
        leftDrawable = a.getDrawable(R.styleable.FollowableView_fvLeftDrawable);
        followedTextColor = a.getColor(R.styleable.FollowableView_fvFollowedTextColor, FOLLOWED_TEXT_COLOR);
        unfollowedTextColor = a.getColor(R.styleable.FollowableView_fvUnFollowedTextColor, UNFOLLOWED_TEXT_COLOR);
        eachfollowedTextColor = a.getColor(R.styleable.FollowableView_fvEachFollowedTextColor, EACHFOLLOWED_TEXT_COLOR);
        followedText = a.getString(R.styleable.FollowableView_fvFollowedText);
        unfollowedText = a.getString(R.styleable.FollowableView_fvUnFollowedText);
        eachfollowedText = a.getString(R.styleable.FollowableView_fvEachFollowedText);

        if (TextUtils.isEmpty(followedText)) {
            followedText = FOLLOWED_TEXT;
        }
        if (TextUtils.isEmpty(unfollowedText)) {
            unfollowedText = UNFOLLOWED_TEXT;
        }
        if (TextUtils.isEmpty(eachfollowedText)) {
            eachfollowedText = EACHFOLLOWED_TEXT;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.widget_followable_view;
    }

    @Override
    protected void initData(Context context, View view) {
        image_left = (ImageView) view.findViewById(R.id.image_left);
        text = (TextView) view.findViewById(R.id.text);

        if (leftDrawable == null) {
            image_left.setImageResource(R.drawable.ic_add_red);
        } else {
            image_left.setImageDrawable(leftDrawable);
        }

        setFollowed(-1);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFollowClickListener != null) {
                    mFollowClickListener.onFollowClick(mFollowedType);
                }
            }
        });
    }

    public String getText() {
        return TextUtils.isEmpty(text.getText()) ? "" : text.getText().toString();
    }

    public int getFollowedType() {
        return mFollowedType;
    }

    public void setFollowedDrawable(Drawable followedDrawable) {
        this.followedDrawable = followedDrawable;
        setFollowed(mFollowedType);
    }

    public void setUnfollowedDrawable(Drawable unfollowedDrawable) {
        this.unfollowedDrawable = unfollowedDrawable;
        setFollowed(mFollowedType);
    }

    public void setEachfollowedDrawable(Drawable eachfollowedDrawable) {
        this.eachfollowedDrawable = eachfollowedDrawable;
        setFollowed(mFollowedType);
    }

    public void setLeftDrawable(Drawable leftDrawable) {
        this.leftDrawable = leftDrawable;
        setFollowed(mFollowedType);
    }

    public void setFollowedTextColor(int followedTextColor) {
        this.followedTextColor = followedTextColor;
        setFollowed(mFollowedType);
    }

    public void setUnfollowedTextColor(int unfollowedTextColor) {
        this.unfollowedTextColor = unfollowedTextColor;
        setFollowed(mFollowedType);
    }

    public void setEachfollowedTextColor(int eachfollowedTextColor) {
        this.eachfollowedTextColor = eachfollowedTextColor;
        setFollowed(mFollowedType);
    }

    public void setFollowedText(String followedText) {
        this.followedText = followedText;
        setFollowed(mFollowedType);
    }

    public void setUnfollowedText(String unfollowedText) {
        this.unfollowedText = unfollowedText;
        setFollowed(mFollowedType);
    }

    public void setEachfollowedText(String eachfollowedText) {
        this.eachfollowedText = eachfollowedText;
        setFollowed(mFollowedType);
    }

    public void setFollowed(int followedType) {
        mFollowedType = followedType;
        if (followedType == FOLLOWED_TYPE_FOLLOWED) {//已关注
            image_left.setVisibility(GONE);
            if (followedDrawable == null) {
                setBackgroundResource(R.drawable.shape_gray_solid_corners);
            } else {
                setBackgroundDrawable(followedDrawable);
            }
            text.setTextColor(followedTextColor);
            text.setText(followedText);
        } else if (followedType == FOLLOWED_TYPE_EACHOTHER) {//互相关注
            image_left.setVisibility(GONE);
            if (eachfollowedDrawable == null) {
                setBackgroundResource(R.drawable.shape_gray_solid_corners);
            } else {
                setBackgroundDrawable(eachfollowedDrawable);
            }
            text.setTextColor(eachfollowedTextColor);
            text.setText(eachfollowedText);
        } else {//未关注
            image_left.setVisibility(VISIBLE);
            if (unfollowedDrawable == null) {
                setBackgroundResource(R.drawable.shape_red_stroke_corners);
            } else {
                setBackgroundDrawable(unfollowedDrawable);
            }
            text.setTextColor(unfollowedTextColor);
            text.setText(unfollowedText);
        }
    }

    public void setOnFollowClickListener(FollowClickListener listener) {
        mFollowClickListener = listener;
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
