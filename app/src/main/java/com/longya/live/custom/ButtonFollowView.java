package com.longya.live.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.longya.live.R;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/12
 */
public class ButtonFollowView extends FrameLayout {
    private LinearLayout ll_bg;
    private ImageView iv_icon;
    private TextView tv_name;

    public ButtonFollowView(@NonNull Context context) {
        this(context, null);
    }

    public ButtonFollowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonFollowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_follow_btn, this, true);
        ll_bg = findViewById(R.id.ll_bg);
        iv_icon = findViewById(R.id.iv_icon);
        tv_name = findViewById(R.id.tv_name);
    }

    public void setFollow(boolean isFollow) {
        if (isFollow) {
            iv_icon.setVisibility(View.GONE);
            tv_name.setText(getContext().getString(R.string.followed));
            ll_bg.setBackgroundResource(R.drawable.shape_cccccc_10dp_rec);
        }else {
            iv_icon.setVisibility(View.VISIBLE);
            tv_name.setText(getContext().getString(R.string.follow));
            ll_bg.setBackgroundResource(R.mipmap.bg_follow_btn);
        }
    }
}
