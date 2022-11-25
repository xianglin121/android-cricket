package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.fragment.FootballDataRankingFragment;
import com.longya.live.model.DataRankingInnerBean;
import com.longya.live.model.FootballDataRankingBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class FootballDataRankingAdapter extends BaseQuickAdapter<FootballDataRankingBean, BaseViewHolder> {
    private FootballDataRankingFragment mFragment;

    public FootballDataRankingAdapter(int layoutResId, @Nullable List<FootballDataRankingBean> data, FootballDataRankingFragment fragment) {
        super(layoutResId, data);
        mFragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballDataRankingBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.getView(R.id.ll_view).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            helper.getView(R.id.ll_view).setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
        }
        helper.setText(R.id.tv_number, String.valueOf(helper.getLayoutPosition()+1));
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (mFragment.mType == 1) {
            helper.setText(R.id.tv_count, String.valueOf(item.getHome_total()));
            helper.setText(R.id.tv_result, item.getHome_won() + "/" + item.getHome_draw() + "/" + item.getHome_loss());
            helper.setText(R.id.tv_goal_and_lose, item.getHome_goals() + "/" + item.getHome_goals_against());
            helper.setText(R.id.tv_point, String.valueOf(item.getHome_points()));
        }else if (mFragment.mType == 2) {
            helper.setText(R.id.tv_count, String.valueOf(item.getAway_total()));
            helper.setText(R.id.tv_result, item.getAway_won() + "/" + item.getAway_draw() + "/" + item.getAway_loss());
            helper.setText(R.id.tv_goal_and_lose, item.getAway_goals() + "/" + item.getAway_goals_against());
            helper.setText(R.id.tv_point, String.valueOf(item.getAway_points()));
        }else {
            helper.setText(R.id.tv_count, String.valueOf(item.getTotal()));
            helper.setText(R.id.tv_result, item.getWon() + "/" + item.getDraw() + "/" + item.getLoss());
            helper.setText(R.id.tv_goal_and_lose, item.getGoals() + "/" + item.getGoals_against());
            helper.setText(R.id.tv_point, String.valueOf(item.getPoints()));
        }
    }
}
