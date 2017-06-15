package com.fxx.library.widget.demo.shape;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

/**
 * Created by wsl on 17/6/15.
 */

public class ShapeActivity extends BaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shape);


        attachToolbar("Shape example");
    }
}
