package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketDetailAdapter extends BaseQuickAdapter<CricketMatchBean, BaseViewHolder> {
    public CricketDetailAdapter(int layoutResId, @Nullable List<CricketMatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketMatchBean item) {
        if (item.getStatus() == 2) {//已结束
            helper.getView(R.id.ll_alarm).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.ll_alarm).setVisibility(View.VISIBLE);
            if (item.getStatus() == 0) {//未开始
                helper.getView(R.id.iv_alarm).setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(item.getLive_time())) {
                    helper.setText(R.id.tv_time, item.getLive_time());
                }else {
                    helper.setText(R.id.tv_time, "");
                }
            }else {//已开始
                helper.getView(R.id.iv_alarm).setVisibility(View.GONE);
                helper.setText(R.id.tv_time, mContext.getString(R.string.live2));
                helper.setTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.c_DC3C23));
            }
        }
        if(!TextUtils.isEmpty(item.getMatch_num())) {
            helper.setText(R.id.tv_date, item.getMatch_num());
        }else {
            helper.setText(R.id.tv_date, "");
        }
        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getHome_logo(), iv_home_logo);
        if(!TextUtils.isEmpty(item.getHome_name())) {
            helper.setText(R.id.tv_home_name, item.getHome_name());
        }else {
            helper.setText(R.id.tv_home_name, "");
        }
        if(!TextUtils.isEmpty(item.getHome_display_score())) {
            helper.setText(R.id.tv_home_score, item.getHome_display_score());
        }else {
            helper.setText(R.id.tv_home_score, "");
        }
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getAway_logo(), iv_away_logo);
        if(!TextUtils.isEmpty(item.getAway_name())) {
            helper.setText(R.id.tv_away_name, item.getAway_name());
        }else {
            helper.setText(R.id.tv_away_name, "");
        }
        if(!TextUtils.isEmpty(item.getAway_display_score())) {
            helper.setText(R.id.tv_away_score, item.getAway_display_score());
        }else {
            helper.setText(R.id.tv_away_score, "");
        }
        if(!TextUtils.isEmpty(item.getMatch_result())) {
            helper.setText(R.id.tv_result, item.getMatch_result());
        }else {
            helper.setText(R.id.tv_result, "");
        }
    }
}
