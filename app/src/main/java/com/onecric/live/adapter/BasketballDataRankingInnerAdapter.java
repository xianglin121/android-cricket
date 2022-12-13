package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.DataRankingInnerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataRankingInnerAdapter extends BaseQuickAdapter<DataRankingInnerBean, BaseViewHolder> {
    public BasketballDataRankingInnerAdapter(int layoutResId, @Nullable List<DataRankingInnerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataRankingInnerBean item) {
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
        helper.setText(R.id.tv_count, String.valueOf(item.getWon() + item.getLost()));
        helper.setText(R.id.tv_result, item.getWon() + "/" + item.getLost());
        helper.setText(R.id.tv_win_rate, item.getWon_rate() + "%");
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_win_gap, item.getGame_back());
        }else {
            helper.setText(R.id.tv_win_gap, "-");
        }
        helper.setText(R.id.tv_recent, String.valueOf(item.getStreaks()));
    }
}
