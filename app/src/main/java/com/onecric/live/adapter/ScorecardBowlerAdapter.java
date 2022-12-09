package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ScorecardBowlerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class ScorecardBowlerAdapter extends BaseQuickAdapter<ScorecardBowlerBean, BaseViewHolder> {
    public ScorecardBowlerAdapter(int layoutResId, @Nullable List<ScorecardBowlerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ScorecardBowlerBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_o, String.valueOf(item.getOvers_bowled()));
        helper.setText(R.id.tv_r, String.valueOf(item.getRuns_conceded()));
        helper.setText(R.id.tv_w, String.valueOf(item.getWides()));
        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }
    }
}
