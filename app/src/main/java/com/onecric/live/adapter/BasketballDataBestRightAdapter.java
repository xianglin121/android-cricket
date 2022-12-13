package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.fragment.BasketballDataBestFragment;
import com.onecric.live.model.DataBestRightBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataBestRightAdapter extends BaseQuickAdapter<DataBestRightBean, BaseViewHolder> {
    private BasketballDataBestFragment mFragment;

    public BasketballDataBestRightAdapter(int layoutResId, @Nullable List<DataBestRightBean> data, BasketballDataBestFragment fragment) {
        super(layoutResId, data);
        mFragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataBestRightBean item) {
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
                helper.setText(R.id.tv_count, String.valueOf(item.getPoints()));
            }else if (mFragment.mTypeId == 1) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPoints_against()));
            }else if (mFragment.mTypeId == 2) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTwo_pointers_scored()));
            }else if (mFragment.mTypeId == 3) {
                helper.setText(R.id.tv_count, String.valueOf(item.getThree_pointers_scored()));
            }else if (mFragment.mTypeId == 4) {
                helper.setText(R.id.tv_count, String.valueOf(item.getField_goals_scored()));
            }else if (mFragment.mTypeId == 5) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTotal_fouls()));
            }else if (mFragment.mTypeId == 6) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRebounds()));
            }else if (mFragment.mTypeId == 7) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDefensive_rebounds()));
            }else if (mFragment.mTypeId == 8) {
                helper.setText(R.id.tv_count, String.valueOf(item.getOffensive_rebounds()));
            }else if (mFragment.mTypeId == 9) {
                helper.setText(R.id.tv_count, String.valueOf(item.getAssists()));
            }else if (mFragment.mTypeId == 10) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTurnovers()));
            }else if (mFragment.mTypeId == 11) {
                helper.setText(R.id.tv_count, String.valueOf(item.getSteals()));
            }else if (mFragment.mTypeId == 12) {
                helper.setText(R.id.tv_count, String.valueOf(item.getBlocks()));
            }else if (mFragment.mTypeId == 13) {
                helper.setText(R.id.tv_count, String.valueOf(item.getFree_throws_scored()));
            }else {
                helper.setText(R.id.tv_count, "-");
            }
        }else {//球员
            if (mFragment.mTypeId == 0) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPoints()));
            }else if (mFragment.mTypeId == 1) {
                helper.setText(R.id.tv_count, String.valueOf(item.getMinutes_played()));
            }else if (mFragment.mTypeId == 2) {
                helper.setText(R.id.tv_count, String.valueOf(item.getFree_throws_scored()));
            }else if (mFragment.mTypeId == 3) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTwo_pointers_scored()));
            }else if (mFragment.mTypeId == 4) {
                helper.setText(R.id.tv_count, String.valueOf(item.getThree_pointers_scored()));
            }else if (mFragment.mTypeId == 5) {
                helper.setText(R.id.tv_count, String.valueOf(item.getField_goals_scored()));
            }else if (mFragment.mTypeId == 6) {
                helper.setText(R.id.tv_count, String.valueOf(item.getRebounds()));
            }else if (mFragment.mTypeId == 7) {
                helper.setText(R.id.tv_count, String.valueOf(item.getDefensive_rebounds()));
            }else if (mFragment.mTypeId == 8) {
                helper.setText(R.id.tv_count, String.valueOf(item.getOffensive_rebounds()));
            }else if (mFragment.mTypeId == 9) {
                helper.setText(R.id.tv_count, String.valueOf(item.getAssists()));
            }else if (mFragment.mTypeId == 10) {
                helper.setText(R.id.tv_count, String.valueOf(item.getTurnovers()));
            }else if (mFragment.mTypeId == 11) {
                helper.setText(R.id.tv_count, String.valueOf(item.getSteals()));
            }else if (mFragment.mTypeId == 12) {
                helper.setText(R.id.tv_count, String.valueOf(item.getBlocks()));
            }else if (mFragment.mTypeId == 13) {
                helper.setText(R.id.tv_count, String.valueOf(item.getPersonal_fouls()));
            }else {
                helper.setText(R.id.tv_count, "-");
            }
        }
    }
}
