package com.fxx.library.widget.demo.linearstep;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fxx.library.widget.common.LinearStepView;
import com.fxx.library.widget.demo.BaseActivity;
import com.fxx.library.widget.demo.R;
import com.fxx.library.widget.utils.FXWidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2017/10/11.
 */

public class LinearStepActivity extends BaseActivity {
    private static final String TAG = LinearStepActivity.class.getSimpleName();

    LinearStepView<String> stepView1;
    LinearStepView<String> stepView2;
    LinearStepView<String> stepView3;
    LinearStepView<String> stepView4;

    LinearStepView.StepAdapter<String> adapter1;
    LinearStepView.StepAdapter<String> adapter2;
    LinearStepView.StepAdapter<String> adapter3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_step);

        attachToolbar("LinearStepView");

        stepView1 = (LinearStepView) findViewById(R.id.step_view1);
        stepView2 = (LinearStepView) findViewById(R.id.step_view2);
        stepView3 = (LinearStepView) findViewById(R.id.step_view3);
        stepView4 = (LinearStepView) findViewById(R.id.step_view4);

        adapter1 = newAdapter();
        adapter2 = newAdapter();
        adapter3 = newAdapter();

        Button reset = (Button) findViewById(R.id.reset);
        Button refresh = (Button) findViewById(R.id.refresh);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        reset();
    }

    private void reset() {
        stepView1.setStepCurrent(0);
        stepView1.setStepLineHeight((int) FXWidgetUtils.dp2px(2, this));
        stepView1.setStepLineColor(Color.BLUE);
        stepView1.setSelectedPos(0);
        stepView1.setAdapter(adapter1);
        adapter1.setSteps(getSteps(5));

        stepView2.setSelectedPos(1);
        stepView2.setStepCurrent(2);
        stepView2.setStepLineHeight((int) FXWidgetUtils.dp2px(2, this));
        stepView2.setStepLineColor(Color.LTGRAY);
        stepView2.setSteppedLineColor(Color.RED);
        stepView2.setStepDrawable(new ColorDrawable(Color.GRAY));
        stepView2.setSteppedDrawable(new ColorDrawable(Color.CYAN));
        stepView2.setSteppingDrawable(new ColorDrawable(Color.MAGENTA));
        stepView2.setAdapter(adapter2);
        adapter2.setSteps(getSteps(3));

        stepView3.setStepCurrent(3);
        stepView3.setSelectedPos(2);
        stepView3.setStepLineHeight((int) FXWidgetUtils.dp2px(4, this));
        stepView3.setStepLineColor(Color.YELLOW);
        stepView3.setSteppedLineColor(Color.GREEN);
        stepView3.setAdapter(adapter3);
        adapter3.setSteps(getSteps(5));

        stepView4.setStepCurrent(2);
        stepView4.setAdditionalViewTopMargin(10);
        stepView4.setSelectedPos(2);
        stepView4.setStepLineHeight((int) FXWidgetUtils.dp2px(2, this));
        stepView4.setStepLineColor(Color.LTGRAY);
        stepView4.setAdapter(adapter1);
        adapter1.setSteps(getSteps(5));
    }

    private void refresh() {
        adapter1.setSteps(getSteps(5));
        adapter2.setSteps(getSteps(3));
        adapter3.setSteps(getSteps(5));
        adapter1.setSteps(getSteps(5));
    }

    private LinearStepView.StepAdapter<String> newAdapter() {
        return new LinearStepView.StepAdapter<String>() {
            @Override
            public View onBindStep(String s, int position) {
                Log.d(TAG, "onBindStep " + s + " " + position);
                return getView(s);
            }

            @Override
            public void onStepSelected(View view, View lastView, int currentPos, int lastPos) {
                Toast.makeText(LinearStepActivity.this, "选中了(" + currentPos + ")=" + getStep(currentPos) + " 上一步(" +
                        lastPos + ")=" + getStep(lastPos), Toast.LENGTH_SHORT).show();
                if (lastView != null)
                    ((TextView) lastView).setTextColor(Color.rgb(0x46, 0x46, 0x46));
                if (view != null)
                    ((TextView) view).setTextColor(Color.RED);
            }
        };
    }

    private View getView(String s) {
        TextView view = new TextView(LinearStepActivity.this);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.rgb(0x46, 0x46, 0x46));
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        view.setText(s);
        view.setMaxLines(1);
        view.setEllipsize(TextUtils.TruncateAt.END);
        return view;
    }

    private List<String> getSteps(int size) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("第" + i + "步");
        }
        return list;
    }
}
