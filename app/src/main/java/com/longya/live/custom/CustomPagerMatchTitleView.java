package com.longya.live.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.coorchice.library.SuperTextView;
import com.longya.live.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;


/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class CustomPagerMatchTitleView extends FrameLayout implements IPagerTitleView {
    private ImageView iv_bg;
    private ImageView iv_icon;
    private TextView tv_name;

    public CustomPagerMatchTitleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.item_match_indicator, null);
        iv_bg = child.findViewById(R.id.iv_bg);
        iv_icon = child.findViewById(R.id.iv_icon);
        tv_name = child.findViewById(R.id.tv_name);
//        setPadding(0,0, DpUtil.dp2px(12), 0);
        addView(child);
    }

    public void setText(String text) {
        tv_name.setText(text);
    }

    public void setImageResource(@DrawableRes int resId) {
        iv_icon.setImageResource(resId);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        tv_name.setTextColor(getResources().getColor(R.color.c_E3AC72));
        iv_bg.setBackgroundResource(R.drawable.shape_fff1e0_13dp_rec);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        tv_name.setTextColor(getResources().getColor(R.color.c_666666));
        iv_bg.setBackground(null);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

    }
}
