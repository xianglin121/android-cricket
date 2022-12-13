package com.onecric.live.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.onecric.live.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;


/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class CustomPagerInnerTitleView extends FrameLayout implements IPagerTitleView {
    private ImageView iv_bg;
    private TextView tv_name;
    private TextView tv_count;

    public CustomPagerInnerTitleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.item_inner_indicator, null);
        iv_bg = child.findViewById(R.id.iv_bg);
        tv_name = child.findViewById(R.id.tv_name);
        tv_count = child.findViewById(R.id.tv_count);
//        setPadding(0,0, DpUtil.dp2px(12), 0);
        addView(child);
    }

    public void setText(String text) {
        tv_name.setText(text);
    }

    public void setCount(String count) {
        tv_count.setText(count);
    }

    public void setCountVisibility(int visibility) {
        tv_count.setVisibility(visibility);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        tv_name.setTextColor(getResources().getColor(R.color.c_DC3C23));
        iv_bg.setBackgroundResource(R.drawable.shape_dc3c23_13dp_stroke_rec);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        tv_name.setTextColor(getResources().getColor(R.color.white));
        iv_bg.setBackground(null);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

    }
}
