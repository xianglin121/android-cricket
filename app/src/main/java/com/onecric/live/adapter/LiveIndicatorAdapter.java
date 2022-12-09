package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.IndicatorBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveIndicatorAdapter extends BaseQuickAdapter<IndicatorBean, BaseViewHolder> {
    public LiveIndicatorAdapter(int layoutResId, @Nullable List<IndicatorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IndicatorBean item) {
        ImageView iv_indicator = helper.getView(R.id.iv_indicator);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (item.isSelected()) {
            iv_indicator.setVisibility(View.VISIBLE);
        }else {
            iv_indicator.setVisibility(View.GONE);
        }
    }
}
