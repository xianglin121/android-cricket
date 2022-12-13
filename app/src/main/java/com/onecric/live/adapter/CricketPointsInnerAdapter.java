package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketPointsInnerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketPointsInnerAdapter extends BaseQuickAdapter<CricketPointsInnerBean, BaseViewHolder> {
    public CricketPointsInnerAdapter(int layoutResId, @Nullable List<CricketPointsInnerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketPointsInnerBean item) {
        if (!TextUtils.isEmpty(item.getCompetitor())) {
            helper.setText(R.id.tv_vrs, item.getCompetitor());
        }else {
            helper.setText(R.id.tv_vrs, "");
        }
        if (!TextUtils.isEmpty(item.getDate())) {
            helper.setText(R.id.tv_date, item.getDate());
        }else {
            helper.setText(R.id.tv_date, "");
        }
        if (!TextUtils.isEmpty(item.getVenue_name())) {
            helper.setText(R.id.tv_place, item.getVenue_name());
        }else {
            helper.setText(R.id.tv_place, "");
        }
        if (!TextUtils.isEmpty(item.getMatch_result())) {
            helper.setText(R.id.tv_result, item.getMatch_result());
        }else {
            helper.setText(R.id.tv_result, "");
        }
        TextView tv_result = helper.getView(R.id.tv_result);
        if (item.getWinner() == 1) {//赢
            tv_result.setTextColor(mContext.getResources().getColor(R.color.c_219430));
        }else if (item.getWinner() == 2) {//输
            tv_result.setTextColor(mContext.getResources().getColor(R.color.c_DC3C23));
        }else{//无结果
            tv_result.setTextColor(mContext.getResources().getColor(R.color.c_666666));
        }
    }
}
