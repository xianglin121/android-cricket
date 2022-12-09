package com.onecric.live.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.onecric.live.R;
import com.onecric.live.util.DpUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/6
 */
public class BottomDotLayout extends LinearLayout {

    public BottomDotLayout(Context context) {
        super(context);
    }

    public BottomDotLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomDotLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(List<String> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);

                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(list.get(i));
                textView.setTextSize(14);
                textView.setTextColor(getResources().getColor(R.color.c_333333));
                linearLayout.addView(textView);

                View dotView = new View(getContext());
                LinearLayout.LayoutParams dotLayoutParams = new LinearLayout.LayoutParams(DpUtil.dp2px(4), DpUtil.dp2px(4));
                dotLayoutParams.gravity = Gravity.CENTER;
                dotView.setLayoutParams(dotLayoutParams);
                dotView.setBackgroundResource(R.drawable.shape_dc3c23_dot);
                linearLayout.addView(dotView);

                addView(linearLayout);

                if (i < (list.size()-1)) {
                    TextView comma = new TextView(getContext());
                    comma.setText(",");
                    comma.setTextSize(14);
                    comma.setTextColor(getResources().getColor(R.color.c_333333));
                    addView(comma);
                }
            }
        }
    }
}
