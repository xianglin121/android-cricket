package com.tencent.liteav.demo.superplayer.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.ScorecardBatterBean;

import java.util.List;

public class ScorecardBatterAdapter extends BaseQuickAdapter<ScorecardBatterBean, BaseViewHolder> {
    public ScorecardBatterAdapter(int layoutResId, @Nullable List<ScorecardBatterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ScorecardBatterBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_r, String.valueOf(item.getRuns()));
        helper.setText(R.id.tv_b, String.valueOf(item.getBall_number()));
        helper.setText(R.id.tv_4s, String.valueOf(item.getFours()));
        helper.setText(R.id.tv_6s, String.valueOf(item.getSixes()));
        helper.setText(R.id.tv_sr, String.valueOf(item.getStrike_rate()));
        if (!TextUtils.isEmpty(item.getType())) {
            helper.setText(R.id.tv_type, item.getType());
            helper.getView(R.id.tv_type).setVisibility(View.VISIBLE);
        }else {
            helper.setText(R.id.tv_type, "");
            helper.getView(R.id.tv_type).setVisibility(View.GONE);
        }
    }
}
