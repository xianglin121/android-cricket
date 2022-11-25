package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.RecentMatchesBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class RecentMatchesAdapter extends BaseQuickAdapter<RecentMatchesBean, BaseViewHolder> {
    public RecentMatchesAdapter(int layoutResId, @Nullable List<RecentMatchesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecentMatchesBean item) {
        if (!TextUtils.isEmpty(item.getMatch_home_name())) {
            helper.setText(R.id.tv_name, item.getMatch_home_name());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getBat())) {
            helper.setText(R.id.tv_bat, item.getBat());
        }else {
            helper.setText(R.id.tv_bat, "");
        }
        if (!TextUtils.isEmpty(item.getBowl())) {
            helper.setText(R.id.tv_bowl, item.getBowl());
        }else {
            helper.setText(R.id.tv_bowl, "");
        }
        if (!TextUtils.isEmpty(item.getType())) {
            helper.setText(R.id.tv_type, item.getType());
        }else {
            helper.setText(R.id.tv_type, "");
        }
    }
}
