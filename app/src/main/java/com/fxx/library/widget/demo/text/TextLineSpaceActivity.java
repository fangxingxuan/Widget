package com.fxx.library.widget.demo.text;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fxx.library.widget.common.LetterSpacingTextView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wsl on 17/6/16.
 */

public class TextLineSpaceActivity extends BaseActivity{

    @BindView(R.id.letter)
    LetterSpacingTextView textView;
    @BindView(R.id.letter1)
    LetterSpacingTextView textView1;
    @BindView(R.id.label)
    TextView label;

    @BindView(R.id.seekBar0)
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_line_space);
        ButterKnife.bind(this);

        attachToolbar("字间距");

        textView.setSpacing(0f);
        textView.setText("因为不能异地高考，我离开广州成了留守儿童!");

        textView1.setSpacing(0.6f);
        textView1.setText("因为不能异地高考，我离开广州成了留守儿童!");



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                label.setText(String.valueOf(progress));
//                textView.setSpacing(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
