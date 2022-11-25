package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.RecentMatchesBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class PlayerBioAdapter extends BaseQuickAdapter<RecentMatchesBean, BaseViewHolder> {
    public PlayerBioAdapter(int layoutResId, @Nullable List<RecentMatchesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecentMatchesBean item) {
        String name = "";
        if (!TextUtils.isEmpty(item.getMatch_home_name())) {
            name += item.getMatch_home_name();
        }
        name += " vs ";
        if (!TextUtils.isEmpty(item.getMatch_away_name())) {
            name += item.getMatch_away_name();
        }
        if (!TextUtils.isEmpty(item.getMatch_time())) {
            name = name + "\n" + item.getMatch_time();
        }
        helper.setText(R.id.tv_name, name);
        if (!TextUtils.isEmpty(item.getBat())) {
            helper.setText(R.id.tv_bat, item.getBat());
        }else {
            helper.setText(R.id.tv_bat, "");
        }
        if (!TextUtils.isEmpty(item.getBowl())) {
            helper.setText(R.id.tv_bowl, item.getBowl());
        }else {
            helper.setText(R.id.tv_bowl, "");
        }
        if (!TextUtils.isEmpty(item.getTournament_type())) {
            helper.setText(R.id.tv_type, item.getTournament_type());
        }else {
            helper.setText(R.id.tv_type, "");
        }
    }
}
