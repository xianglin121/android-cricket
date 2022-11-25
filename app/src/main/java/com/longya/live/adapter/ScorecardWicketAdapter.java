package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.ScorecardBowlerBean;
import com.longya.live.model.ScorecardWicketBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class ScorecardWicketAdapter extends BaseQuickAdapter<ScorecardWicketBean, BaseViewHolder> {
    public ScorecardWicketAdapter(int layoutResId, @Nullable List<ScorecardWicketBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ScorecardWicketBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_score, String.valueOf(item.getScore()));
        helper.setText(R.id.tv_over, String.valueOf(item.getOver()));
        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }
    }
}
