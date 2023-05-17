package com.onecric.live.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class LiveUpcomingAdapter extends BaseQuickAdapter<LiveMatchListBean.MatchItemBean, BaseViewHolder> {
    private SimpleDateFormat sfdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public LiveUpcomingAdapter(int layoutResId, @Nullable List<LiveMatchListBean.MatchItemBean> data) {
        super(layoutResId, data);
    }

    int[] bgArr= {R.mipmap.bg_live_upcoming1,R.mipmap.bg_live_upcoming2,R.mipmap.bg_live_upcoming3};

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveMatchListBean.MatchItemBean item) {
        helper.setText(R.id.tv_time, item.getScheduled());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_home_name,item.getHome_name());
        helper.setText(R.id.tv_away_name,item.getAway_name());
        GlideUtil.loadTeamImageDefault(mContext,item.getHome_logo(),helper.getView(R.id.iv_home_logo));
        GlideUtil.loadTeamImageDefault(mContext,item.getAway_logo(),helper.getView(R.id.iv_away_logo));
        helper.setBackgroundRes(R.id.rl_bg,bgArr[helper.getPosition()%3]);
    }

}
