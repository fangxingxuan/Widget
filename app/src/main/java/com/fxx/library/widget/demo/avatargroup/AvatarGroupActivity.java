package com.fxx.library.widget.demo.avatargroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.fxx.library.widget.common.AvatarViewGroup;
import com.fxx.library.widget.common.CircleImageView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;
import com.fxx.library.widget.utils.FXWidgetUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yy on 2017/10/11.
 */

public class AvatarGroupActivity extends BaseActivity {
    private static final String TAG = AvatarGroupActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_group);

        attachToolbar("AvatarGroup");

        Integer[] colorArray = new Integer[]{Color.GREEN, Color.RED, Color.BLUE, Color.CYAN, Color.MAGENTA, Color
                .DKGRAY};
        List<Integer> colors = Arrays.asList(colorArray);

        AvatarViewGroup<Integer, CircleImageView> avatar_group_lor = (AvatarViewGroup) findViewById(R.id
                .avatar_group_lor);
        AvatarViewGroup<Integer, CircleImageView> avatar_group_lor_invert = (AvatarViewGroup) findViewById(R.id
                .avatar_group_lor_invert);
        AvatarViewGroup<Integer, CircleImageView> avatar_group_rol = (AvatarViewGroup) findViewById(R.id
                .avatar_group_rol);
        AvatarViewGroup<Integer, CircleImageView> avatar_group_rol_invert = (AvatarViewGroup) findViewById(R.id
                .avatar_group_rol_invert);

        //展示图片回调
        AvatarViewGroup.OnDisplayListener<Integer> onDisplayListener = new AvatarViewGroup.OnDisplayListener<Integer>
                () {
            @Override
            public void onDisplay(Context context, Integer color, ImageView imageView) {
                imageView.setImageDrawable(new ColorDrawable(color));
            }
        };

        //生成ImageView
        AvatarViewGroup.OnGenerateViewListener<CircleImageView> onGenerateViewListener = new AvatarViewGroup
                .OnGenerateViewListener<CircleImageView>() {
            @Override
            public CircleImageView getView(int at) {
                return genImageView();
            }
        };

        /////////////////////////////// 左边覆盖右边(默认) /////////////////////////////////
        avatar_group_lor.setOverOrientation(AvatarViewGroup.Orientation.LOR);
        avatar_group_lor.setOverlap((int) FXWidgetUtils.dp2px(6, this));
        avatar_group_lor.setChildSize((int) FXWidgetUtils.dp2px(52, this), (int) FXWidgetUtils.dp2px(52, this));
        avatar_group_lor.setOnDisplayListener(onDisplayListener);
        avatar_group_lor.setOnGenerateViewListener(onGenerateViewListener);
        avatar_group_lor.setShowMore(false);
        avatar_group_lor.setDatas(colors.subList(0, 5));

        //逆向
        avatar_group_lor_invert.setInvert(true);
        avatar_group_lor_invert.setOverOrientation(AvatarViewGroup.Orientation.LOR);
        avatar_group_lor_invert.setOverlap((int) FXWidgetUtils.dp2px(6, this));
        avatar_group_lor_invert.setChildSize((int) FXWidgetUtils.dp2px(52, this), (int) FXWidgetUtils.dp2px(52, this));
        avatar_group_lor_invert.setOnDisplayListener(onDisplayListener);
        avatar_group_lor_invert.setOnGenerateViewListener(onGenerateViewListener);
        avatar_group_lor_invert.setShowMore(true);
        avatar_group_lor_invert.setMoreDrawableResId(R.drawable.ic_rounded_image);
        avatar_group_lor_invert.setDatas(colors.subList(0, 5));


        /////////////////////////////// 右边覆盖左边 /////////////////////////////////
        avatar_group_rol.setOverOrientation(AvatarViewGroup.Orientation.ROL);
        avatar_group_rol.setOverlap((int) FXWidgetUtils.dp2px(6, this));
        avatar_group_rol.setChildSize((int) FXWidgetUtils.dp2px(32, this), (int) FXWidgetUtils.dp2px(32, this));
        avatar_group_rol.setOnDisplayListener(onDisplayListener);
        avatar_group_rol.setOnGenerateViewListener(onGenerateViewListener);
        avatar_group_rol.setShowMore(true);
        avatar_group_rol.setDatas(colors.subList(0, 5));

        //逆向
        avatar_group_rol_invert.setInvert(true);
        avatar_group_rol_invert.setOverOrientation(AvatarViewGroup.Orientation.ROL);
        avatar_group_rol_invert.setOverlap((int) FXWidgetUtils.dp2px(6, this));
        avatar_group_rol_invert.setChildSize((int) FXWidgetUtils.dp2px(32, this), (int) FXWidgetUtils.dp2px(32, this));
        avatar_group_rol_invert.setOnDisplayListener(onDisplayListener);
        avatar_group_rol_invert.setOnGenerateViewListener(onGenerateViewListener);
        avatar_group_rol_invert.setShowMore(false);
        avatar_group_rol_invert.setDatas(colors.subList(0, 5));
    }

    private CircleImageView genImageView() {
        CircleImageView civ = new CircleImageView(AvatarGroupActivity.this);
        civ.setBorderColor(Color.YELLOW);
        civ.setBorderWidth((int) FXWidgetUtils.dp2px(1, AvatarGroupActivity.this));
        civ.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return civ;
    }
}
