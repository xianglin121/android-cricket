package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.OneHistoryLiveBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

public class LiveHistoryAdapter extends BaseQuickAdapter<OneHistoryLiveBean, BaseViewHolder> {
    public LiveHistoryAdapter(int layoutResId, @Nullable List<OneHistoryLiveBean> data) {
        super(layoutResId, data);
    }

    int[] bgArr= {R.mipmap.bg_live_history1,R.mipmap.bg_live_history2,R.mipmap.bg_live_history3};

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OneHistoryLiveBean item) {
        helper.setBackgroundRes(R.id.rl_bg,bgArr[helper.getPosition()%3]);
        helper.setText(R.id.tv_time,item.scheduled);
        helper.setText(R.id.tv_home_name,item.homeName);
        helper.setText(R.id.tv_away_name,item.awayName);
        GlideUtil.loadTeamImageDefault(mContext,item.homeLogo,helper.getView(R.id.iv_home_logo));
        GlideUtil.loadTeamImageDefault(mContext,item.awayLogo,helper.getView(R.id.iv_away_logo));
        helper.setText(R.id.tv_view_number,item.viewers>1000 ? String.format("%.1f",(float)item.viewers/1000) + "K" :item.viewers+"");
        helper.setText(R.id.tv_home_score2, " ");
        if (!TextUtils.isEmpty(item.homeDisplayScore)) {
            if(item.homeDisplayScore.contains("0/0")){
                helper.setText(R.id.tv_home_score, "");
                helper.setText(R.id.tv_home_score2, mContext.getString(R.string.yet_to_bat));
            }else if (item.homeDisplayScore.contains(" ")) {
                String[] split = item.homeDisplayScore.split(" ");
                helper.setText(R.id.tv_home_score, " "+split[0]);
                helper.setText(R.id.tv_home_score2, split[1]);
            } else {
                helper.setText(R.id.tv_home_score, item.homeDisplayScore);
            }
        } else {
            helper.setText(R.id.tv_home_score, "");
        }
        helper.setText(R.id.tv_away_score2, "");
        if (!TextUtils.isEmpty(item.awayDisplayScore)) {
            if(item.awayDisplayScore.contains("0/0")){
                helper.setText(R.id.tv_away_score, "");
                helper.setText(R.id.tv_away_score2, mContext.getString(R.string.yet_to_bat));
            }else if (item.awayDisplayScore.contains(" ")) {
                String[] split = item.awayDisplayScore.split(" ");
                helper.setText(R.id.tv_away_score, " "+split[0]);
                helper.setText(R.id.tv_away_score2, split[1]);
            } else {
                helper.setText(R.id.tv_away_score, item.awayDisplayScore);
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
        }

    }

}
