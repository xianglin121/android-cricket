package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.DataScheduleBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballDataScheduleAdapter extends BaseQuickAdapter<DataScheduleBean, BaseViewHolder> {
    public FootballDataScheduleAdapter(int layoutResId, @Nullable List<DataScheduleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataScheduleBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.getView(R.id.ll_view).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            helper.getView(R.id.ll_view).setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
        }
        if (!TextUtils.isEmpty(item.getDay()) && !TextUtils.isEmpty(item.getHour())) {
            helper.setText(R.id.tv_date, item.getDay() + "\n" + item.getHour());
        }else {
            helper.setText(R.id.tv_date, "");
        }
        if (!TextUtils.isEmpty(item.getAway_name())) {
            helper.setText(R.id.tv_away_name, item.getAway_name());
        }else {
            helper.setText(R.id.tv_away_name, "");
        }
        if (!TextUtils.isEmpty(item.getHome_name())) {
            helper.setText(R.id.tv_home_name, item.getHome_name());
        }else {
            helper.setText(R.id.tv_home_name, "");
        }
        TextView tv_score_total = helper.getView(R.id.tv_score_total);
        TextView tv_away_score_half = helper.getView(R.id.tv_away_score_half);
        TextView tv_home_score_half = helper.getView(R.id.tv_home_score_half);
        tv_score_total.setText(item.getHome_scores_total() + "-" + item.getAway_scores_total());
        tv_away_score_half.setText(String.valueOf(item.getAway_scores_halftime()));
        tv_home_score_half.setText(String.valueOf(item.getHome_scores_halftime()));
        if (item.getAway_scores_total() > item.getHome_scores_total()) {
            tv_score_total.setTextColor(mContext.getResources().getColor(R.color.c_6CA1FC));
        }else if (item.getAway_scores_total() < item.getHome_scores_total()) {
            tv_score_total.setTextColor(mContext.getResources().getColor(R.color.c_FF7B7F));
        }else {
            tv_score_total.setTextColor(mContext.getResources().getColor(R.color.c_44AC3D));
        }
        helper.setText(R.id.tv_corner, String.valueOf(item.getHome_corner_licks() + item.getAway_corner_licks()));
    }
}
