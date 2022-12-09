package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketMatchBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class TeamComparisonAdapter extends BaseQuickAdapter<CricketMatchBean, BaseViewHolder> {
    public TeamComparisonAdapter(int layoutResId, @Nullable List<CricketMatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketMatchBean item) {
        if (item.getHome_id() == item.getWinner_id()) {
            helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_219430));
        }else {
            helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_DC3C23));
        }
        if (!TextUtils.isEmpty(item.getHome_display_score())) {
            helper.setText(R.id.tv_home_score, item.getHome_display_score());
        }else {
            helper.setText(R.id.tv_home_score, "");
        }
        if (!TextUtils.isEmpty(item.getHome_display_overs())) {
            helper.setText(R.id.tv_home_round, item.getHome_display_overs());
        }else {
            helper.setText(R.id.tv_home_round, "");
        }
        if (!TextUtils.isEmpty(item.getDate())) {
            helper.setText(R.id.tv_date, item.getDate());
        }else {
            helper.setText(R.id.tv_date, "");
        }
        if (!TextUtils.isEmpty(item.getVenue())) {
            helper.setText(R.id.tv_venue, item.getVenue());
        }else {
            helper.setText(R.id.tv_venue, "");
        }
        if (!TextUtils.isEmpty(item.getMatch_result())) {
            helper.setText(R.id.tv_result, item.getMatch_result());
        }else {
            helper.setText(R.id.tv_result, "");
        }
        if (!TextUtils.isEmpty(item.getOpponent_name())) {
            helper.setText(R.id.tv_away_name, "vs " + item.getOpponent_name());
        }else {
            helper.setText(R.id.tv_away_name, "");
        }
        if (!TextUtils.isEmpty(item.getAway_display_score())) {
            helper.setText(R.id.tv_away_score, item.getAway_display_score());
        }else {
            helper.setText(R.id.tv_away_score, "");
        }
        if (!TextUtils.isEmpty(item.getAway_display_overs())) {
            helper.setText(R.id.tv_away_round, item.getAway_display_overs());
        }else {
            helper.setText(R.id.tv_away_round, "");
        }
        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }
    }
}
