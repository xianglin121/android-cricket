package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.fragment.BasketballDataBestFragment;
import com.longya.live.fragment.FootballDataBestFragment;
import com.longya.live.model.DataBestRightBean;
import com.longya.live.model.FootballDataBestRightBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class FootballDataBestRightAdapter extends BaseQuickAdapter<FootballDataBestRightBean, BaseViewHolder> {
    private FootballDataBestFragment mFragment;

    public FootballDataBestRightAdapter(int layoutResId, @Nullable List<FootballDataBestRightBean> data, FootballDataBestFragment fragment) {
        super(layoutResId, data);
        mFragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballDataBestRightBean item) {
        helper.setText(R.id.tv_number, String.valueOf(helper.getLayoutPosition()+1));
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getLogo(), iv_logo);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (mFragment.mType == 0) {//球队
            if (mFragment.mTypeId == 0) {
                helper.setText(R.id.tv_count, String.valueOf(item.getGoals()));
            }else if (mFragment.mTypeId == 1) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPenalty()));
            }else if (mFragment.mTypeId == 2) {
                helper.setText(R.id.tv_count, String.valueOf(item.getAssists()));
            }else if (mFragment.mTypeId == 3) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRed_cards()));
            }else if (mFragment.mTypeId == 4) {
                helper.setText(R.id.tv_count, String.valueOf(item.getYellow_cards()));
            }else if (mFragment.mTypeId == 5) {
                helper.setText(R.id.tv_count, String.valueOf(item.getShots()));
            }else if (mFragment.mTypeId == 6) {
                helper.setText(R.id.tv_count, String.valueOf(item.getShots_on_target()));
            }else if (mFragment.mTypeId == 7) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDribble()));
            }else if (mFragment.mTypeId == 8) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDribble_succ()));
            }else if (mFragment.mTypeId == 9) {
                helper.setText(R.id.tv_count, String.valueOf(item.getClearances()));
            }else if (mFragment.mTypeId == 10) {
                helper.setText(R.id.tv_count, String.valueOf(item.getBlocked_shots()));
            }else if (mFragment.mTypeId == 11) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTackles()));
            }else if (mFragment.mTypeId == 12) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPasses()));
            }else if (mFragment.mTypeId == 13) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPasses_accuracy()));
            }else if (mFragment.mTypeId == 14) {
                helper.setText(R.id.tv_count, String.valueOf(item.getKey_passes()));
            }else if (mFragment.mTypeId == 15) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCrosses()));
            }else if (mFragment.mTypeId == 16) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCrosses_accuracy()));
            }else if (mFragment.mTypeId == 17) {
                helper.setText(R.id.tv_count, String.valueOf(item.getLong_balls()));
            }else if (mFragment.mTypeId == 18) {
                helper.setText(R.id.tv_count, String.valueOf(item.getLong_balls_accuracy()));
            }else if (mFragment.mTypeId == 19) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDuels()));
            }else if (mFragment.mTypeId == 20) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDuels_won()));
            }else if (mFragment.mTypeId == 21) {
                helper.setText(R.id.tv_count, String.valueOf(item.getFouls()));
            }else if (mFragment.mTypeId == 22) {
                helper.setText(R.id.tv_count, String.valueOf(item.getWas_fouled()));
            }else if (mFragment.mTypeId == 23) {
                helper.setText(R.id.tv_count, String.valueOf(item.getGoals_against()));
            }else if (mFragment.mTypeId == 24) {
                helper.setText(R.id.tv_count, String.valueOf(item.getInterceptions()));
            }else if (mFragment.mTypeId == 25) {
                helper.setText(R.id.tv_count, String.valueOf(item.getOffsides()));
            }else if (mFragment.mTypeId == 26) {
                helper.setText(R.id.tv_count, String.valueOf(item.getYellow2red_cards()));
            }else if (mFragment.mTypeId == 27) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCorner_kicks()));
            }else if (mFragment.mTypeId == 28) {
                helper.setText(R.id.tv_count, String.valueOf(item.getBall_possession()));
            }else {
                helper.setText(R.id.tv_count, "-");
            }
        }else {//球员
            if (mFragment.mTypeId == 0) {
                helper.setText(R.id.tv_count, String.valueOf(item.getMatches()));
            }else if (mFragment.mTypeId == 1) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCourt()));
            }else if (mFragment.mTypeId == 2) {
                helper.setText(R.id.tv_count, String.valueOf(item.getFirst()));
            }else if (mFragment.mTypeId == 3) {
                helper.setText(R.id.tv_count, String.valueOf(item.getGoals()));
            }else if (mFragment.mTypeId == 4) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPenalty()));
            }else if (mFragment.mTypeId == 5) {
                helper.setText(R.id.tv_count, String.valueOf(item.getAssists()));
            }else if (mFragment.mTypeId == 6) {
                helper.setText(R.id.tv_count, String.valueOf(item.getMinutes_played()));
            }else if (mFragment.mTypeId == 7) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRed_cards()));
            }else if (mFragment.mTypeId == 8) {
                helper.setText(R.id.tv_count, String.valueOf(item.getYellow_cards()));
            }else if (mFragment.mTypeId == 9) {
                helper.setText(R.id.tv_count, String.valueOf(item.getShots()));
            }else if (mFragment.mTypeId == 10) {
                helper.setText(R.id.tv_count, String.valueOf(item.getShots_on_target()));
            }else if (mFragment.mTypeId == 11) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDribble()));
            }else if (mFragment.mTypeId == 12) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDribble_succ()));
            }else if (mFragment.mTypeId == 13) {
                helper.setText(R.id.tv_count, String.valueOf(item.getClearances()));
            }else if (mFragment.mTypeId == 14) {
                helper.setText(R.id.tv_count, String.valueOf(item.getBlocked_shots()));
            }else if (mFragment.mTypeId == 15) {
                helper.setText(R.id.tv_count, String.valueOf(item.getInterceptions()));
            }else if (mFragment.mTypeId == 16) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTackles()));
            }else if (mFragment.mTypeId == 17) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPasses()));
            }else if (mFragment.mTypeId == 18) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPasses_accuracy()));
            }else if (mFragment.mTypeId == 19) {
                helper.setText(R.id.tv_count, String.valueOf(item.getKey_passes()));
            }else if (mFragment.mTypeId == 20) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCrosses()));
            }else if (mFragment.mTypeId == 21) {
                helper.setText(R.id.tv_count, String.valueOf(item.getCrosses_accuracy()));
            }else if (mFragment.mTypeId == 22) {
                helper.setText(R.id.tv_count, String.valueOf(item.getLong_balls()));
            }else if (mFragment.mTypeId == 23) {
                helper.setText(R.id.tv_count, String.valueOf(item.getLong_balls_accuracy()));
            }else if (mFragment.mTypeId == 24) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDuels()));
            }else if (mFragment.mTypeId == 25) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDuels_won()));
            }else if (mFragment.mTypeId == 26) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDispossessed()));
            }else if (mFragment.mTypeId == 27) {
                helper.setText(R.id.tv_count, String.valueOf(item.getFouls()));
            }else if (mFragment.mTypeId == 28) {
                helper.setText(R.id.tv_count, String.valueOf(item.getWas_fouled()));
            }else if (mFragment.mTypeId == 29) {
                helper.setText(R.id.tv_count, String.valueOf(item.getOffsides()));
            }else if (mFragment.mTypeId == 30) {
                helper.setText(R.id.tv_count, String.valueOf(item.getYellow2red_cards()));
            }else if (mFragment.mTypeId == 31) {
                helper.setText(R.id.tv_count, String.valueOf(item.getSaves()));
            }else if (mFragment.mTypeId == 32) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPunches()));
            }else if (mFragment.mTypeId == 33) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRuns_out()));
            }else if (mFragment.mTypeId == 34) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRuns_out_succ()));
            }else if (mFragment.mTypeId == 35) {
                helper.setText(R.id.tv_count, String.valueOf(item.getGood_high_claim()));
            }else {
                helper.setText(R.id.tv_count, "-");
            }
        }
    }
}
