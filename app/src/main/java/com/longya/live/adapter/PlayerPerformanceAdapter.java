package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.PerformanceBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class PlayerPerformanceAdapter extends BaseQuickAdapter<PerformanceBean, BaseViewHolder> {
    public PlayerPerformanceAdapter(int layoutResId, @Nullable List<PerformanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PerformanceBean item) {
        helper.setText(R.id.tv_name, item.getText());
        helper.setText(R.id.tv_value, item.getValue());
    }
}
