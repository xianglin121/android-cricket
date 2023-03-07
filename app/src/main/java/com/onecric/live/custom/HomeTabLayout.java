package com.onecric.live.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.onecric.live.R;
import com.onecric.live.common.TRANSTYPE;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/10
 */
public class HomeTabLayout extends LinearLayout implements View.OnClickListener {

    private ImageView iv_match, iv_theme, iv_live, iv_video;
    private TextView tv_match, tv_theme, tv_live, tv_video;

    private OnSwitchTabListener mOnSwitchTabListener;

    public void setOnSwitchTabListener(OnSwitchTabListener onSwitchTabListener) {
        mOnSwitchTabListener = onSwitchTabListener;
    }

    public HomeTabLayout(Context context) {
        super(context);
        init();
    }

    public HomeTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_home_tab, this);
        iv_match = findViewById(R.id.iv_match);
        iv_theme = findViewById(R.id.iv_theme);
        iv_live = findViewById(R.id.iv_live);
        iv_video = findViewById(R.id.iv_video);
        tv_match = findViewById(R.id.tv_match);
        tv_theme = findViewById(R.id.tv_theme);
        tv_live = findViewById(R.id.tv_live);
        tv_video = findViewById(R.id.tv_video);

        findViewById(R.id.btn_match).setOnClickListener(this);
        findViewById(R.id.btn_theme).setOnClickListener(this);
        findViewById(R.id.btn_live).setOnClickListener(this);
        findViewById(R.id.btn_video).setOnClickListener(this);

        iv_live.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_theme:
                if (iv_theme.isSelected()) {
                    return;
                }
                toggleBtn(0);
                if (mOnSwitchTabListener != null) {
                    mOnSwitchTabListener.onSwitch(TRANSTYPE.THEME);
                }
                break;
            case R.id.btn_match:
                if (iv_match.isSelected()) {
                    return;
                }
                toggleBtn(1);
                if (mOnSwitchTabListener != null) {
                    mOnSwitchTabListener.onSwitch(TRANSTYPE.MATCH);
                }
                break;
            case R.id.btn_live:
                if (iv_live.isSelected()) {
                    return;
                }
                toggleBtn(2);
                if (mOnSwitchTabListener != null) {
                    mOnSwitchTabListener.onSwitch(TRANSTYPE.LIVE);
                }
                break;
            case R.id.btn_video:
                if (iv_video.isSelected()) {
                    return;
                }
                toggleBtn(3);
                if (mOnSwitchTabListener != null) {
                    mOnSwitchTabListener.onSwitch(TRANSTYPE.VIDEO);
                }
                break;
        }
    }

    public void toggleBtn(int position) {
        if (position == 0) {
            iv_theme.setSelected(true);
            tv_theme.setTextColor(getResources().getColor(R.color.c_DC3C23));
        }else {
            iv_theme.setSelected(false);
            tv_theme.setTextColor(getResources().getColor(R.color.c_959DB0));
        }
        if (position == 1) {
            iv_match.setSelected(true);
            tv_match.setTextColor(getResources().getColor(R.color.c_DC3C23));
        }else {
            iv_match.setSelected(false);
            tv_match.setTextColor(getResources().getColor(R.color.c_959DB0));
        }
        if (position == 2) {
            iv_live.setSelected(true);
            tv_live.setTextColor(getResources().getColor(R.color.c_DC3C23));
        }else {
            iv_live.setSelected(false);
            tv_live.setTextColor(getResources().getColor(R.color.c_959DB0));
        }
        if (position == 3) {
            iv_video.setSelected(true);
            tv_video.setTextColor(getResources().getColor(R.color.c_DC3C23));
        }else {
            iv_video.setSelected(false);
            tv_video.setTextColor(getResources().getColor(R.color.c_959DB0));
        }
    }

    public interface OnSwitchTabListener {
        void onSwitch(TRANSTYPE type);
    }
}
