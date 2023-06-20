package com.tencent.liteav.demo.superplayer.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.ScorecardBowlerBean;

import java.util.List;

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
