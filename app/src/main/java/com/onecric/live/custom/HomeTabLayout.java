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

    private ImageView iv_match, iv_theme, iv_live, iv_video,iv_game;
    private TextView tv_match, tv_theme, tv_live, tv_video,tv_game;

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
        iv_game = findViewById(R.id.iv_game);
        tv_game = findViewById(R.id.tv_game);

        findViewById(R.id.btn_match).setOnClickListener(this);
        findViewById(R.id.btn_theme).setOnClickListener(this);
        findViewById(R.id.btn_live).setOnClickListener(this);
        findViewById(R.id.btn_video).setOnClickListener(this);
        findViewById(R.id.btn_game).setOnClickListener(this);

        iv_theme.setSelected(true);
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
            case R.id.btn_game:
                if (iv_game.isSelected()) {
                    return;
                }
                toggleBtn(4);
                if (mOnSwitchTabListener != null) {
                    mOnSwitchTabListener.onSwitch(TRANSTYPE.GAME);
                }
                break;
        }
    }

    public void toggleBtn(int position) {
        if (position == 0) {
            iv_theme.setSelected(true);
            tv_theme.setTextColor(getResources().getColor(R.color.c_1444F5));
        }else {
            iv_theme.setSelected(false);
            tv_theme.setTextColor(getResources().getColor(R.color.c_4E4E4E));
        }
        if (position == 1) {
            iv_match.setSelected(true);
            tv_match.setTextColor(getResources().getColor(R.color.c_1444F5));
        }else {
            iv_match.setSelected(false);
            tv_match.setTextColor(getResources().getColor(R.color.c_4E4E4E));
        }
        if (position == 2) {
            iv_live.setSelected(true);
            tv_live.setTextColor(getResources().getColor(R.color.c_1444F5));
        }else {
            iv_live.setSelected(false);
            tv_live.setTextColor(getResources().getColor(R.color.c_4E4E4E));
        }
        if (position == 3) {
            iv_video.setSelected(true);
            tv_video.setTextColor(getResources().getColor(R.color.c_1444F5));
        }else {
            iv_video.setSelected(false);
            tv_video.setTextColor(getResources().getColor(R.color.c_4E4E4E));
        }
        if (position == 4) {
            iv_game.setSelected(true);
            tv_game.setTextColor(getResources().getColor(R.color.c_1444F5));
        }else {
            iv_game.setSelected(false);
            tv_game.setTextColor(getResources().getColor(R.color.c_4E4E4E));
        }

    }

    public interface OnSwitchTabListener {
        void onSwitch(TRANSTYPE type);
    }
}
