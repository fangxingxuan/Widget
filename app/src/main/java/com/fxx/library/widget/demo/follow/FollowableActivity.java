package com.fxx.library.widget.demo.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.fxx.library.widget.common.FollowableView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binlly on 17/6/28.
 */

public class FollowableActivity extends BaseActivity {

    @BindView(R.id.follow1) FollowableView follow1;
    @BindView(R.id.follow2) FollowableView follow2;
    @BindView(R.id.follow3) FollowableView follow3;
    @BindView(R.id.follow4) FollowableView follow4;
    @BindView(R.id.follow5) FollowableView follow5;

    @OnClick(R.id.follow1)
    void onClick1() {
        Toast.makeText(this, follow1.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.follow2)
    void onClick2() {
        Toast.makeText(this, follow2.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.follow3)
    void onClick3() {
        Toast.makeText(this, follow3.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.follow4)
    void onClick4() {
        Toast.makeText(this, follow4.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.follow5)
    void onClick5() {
        Toast.makeText(this, follow5.getText(), Toast.LENGTH_SHORT).show();
        if (follow5.getFollowedType() == FollowableView.FOLLOWED_TYPE_FOLLOWED) {
            follow5.setFollowed(FollowableView.FOLLOWED_TYPE_UNFOLLOWED);
        } else {
            follow5.setFollowed(FollowableView.FOLLOWED_TYPE_FOLLOWED);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_followable_view);
        ButterKnife.bind(this);

        attachToolbar("Followable View");

        follow1.setFollowed(FollowableView.FOLLOWED_TYPE_EACHOTHER);
        follow2.setFollowed(FollowableView.FOLLOWED_TYPE_UNFOLLOWED);
        follow3.setFollowed(FollowableView.FOLLOWED_TYPE_FOLLOWED);
        follow4.setFollowed(FollowableView.FOLLOWED_TYPE_EACHOTHER);
        follow5.setFollowed(FollowableView.FOLLOWED_TYPE_UNFOLLOWED);
    }
}
