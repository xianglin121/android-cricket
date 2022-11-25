package com.longya.live.adapter;

import android.content.Context;
import android.view.View;

import com.longya.live.R;
import com.longya.live.custom.CustomPagerMatchTitleView;
import com.longya.live.model.Channel;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class ChannelNavigatorAdapter extends CommonNavigatorAdapter {
    private List<Channel> mTitles;
    private OnChannelChangeListener mOnChannelChangeListener;

    public ChannelNavigatorAdapter(List<Channel> titles) {
        this.mTitles = titles;
    }

    public void setOnChannelChangeListener(OnChannelChangeListener onChannelChangeListener) {
        mOnChannelChangeListener = onChannelChangeListener;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        CustomPagerMatchTitleView titleView = new CustomPagerMatchTitleView(context);
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.rightMargin = DpUtil.dp2px(12);
//                titleView.setContentView(R.layout.item_ym_indicator);
//                titleView.setLayoutParams(layoutParams);
//                SuperTextView tv_name = titleView.findViewById(R.id.tv_name);
        titleView.setText(mTitles.get(index).short_name_zh);
        if (index == 0) {
            titleView.setImageResource(R.mipmap.icon_all);
        }else {
            if (mTitles.get(index).getType() == 1) {
                titleView.setImageResource(R.mipmap.icon_basketball);
            }else {
                titleView.setImageResource(R.mipmap.icon_football);
            }
        }
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChannelChangeListener != null) {
                    mOnChannelChangeListener.onChange(index);
                }
            }
        });
        return titleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }

    public interface OnChannelChangeListener {
        void onChange(int index);
    }
}
