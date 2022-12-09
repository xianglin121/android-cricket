package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveRecommendMatchAdapter extends BaseQuickAdapter<LiveMatchBean, BaseViewHolder> {
    public LiveRecommendMatchAdapter(int layoutResId, @Nullable List<LiveMatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveMatchBean item) {
        ImageView iv_logo_one = helper.getView(R.id.iv_logo_one);
        ImageView iv_logo_two = helper.getView(R.id.iv_logo_two);
        if (!TextUtils.isEmpty(item.getCompetition())) {
            helper.setText(R.id.tv_title, item.getCompetition());
        }else {
            helper.setText(R.id.tv_title, "");
        }

        GlideUtil.loadTeamImageDefault(mContext, item.getHome_team_logo(), iv_logo_one);
        if (!TextUtils.isEmpty(item.getHome_team_name())) {
            helper.setText(R.id.tv_home_team, item.getHome_team_name());
        }else {
            helper.setText(R.id.tv_home_team, "");
        }
        helper.setText(R.id.tv_home_score, String.valueOf(item.getHome_scores()));

        GlideUtil.loadTeamImageDefault(mContext, item.getAway_team_logo(), iv_logo_two);
        if (!TextUtils.isEmpty(item.getAway_team_name())) {
            helper.setText(R.id.tv_away_team, item.getAway_team_name());
        }else {
            helper.setText(R.id.tv_away_team, "");
        }
        helper.setText(R.id.tv_away_score, String.valueOf(item.getAway_scores()));

        TextView tv_reserve = helper.getView(R.id.tv_reserve);
        if (item.getStatus_type() == 1) {
            tv_reserve.setText(R.string.underway);
            tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            tv_reserve.setBackground(null);
        }else if (item.getStatus_type() == 2) {
            tv_reserve.setText(R.string.finished);
            tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            tv_reserve.setBackground(null);
        }else {
            if (item.getReserve() == 0) {
                tv_reserve.setText(mContext.getString(R.string.book));
                tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
                tv_reserve.setBackgroundResource(R.drawable.bg_live_classify);
            }else {
                tv_reserve.setText(mContext.getString(R.string.booked));
                tv_reserve.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                tv_reserve.setBackgroundResource(R.drawable.bg_reserve_selected);
            }
        }

        helper.addOnClickListener(R.id.tv_reserve);

        if (item.getStatus_id() == 1) {
            helper.getView(R.id.ll_date).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getMatch_time())) {
                String match_time = item.getMatch_time();
                match_time = match_time.replace(" ", "\n");
                helper.setText(R.id.tv_date, match_time);
            }else {
                helper.setText(R.id.tv_date, "");
            }
        }else {
            helper.getView(R.id.ll_date).setVisibility(View.GONE);
        }
    }
}
