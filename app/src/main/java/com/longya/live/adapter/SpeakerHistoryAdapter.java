package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.SpeakerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class SpeakerHistoryAdapter extends BaseQuickAdapter<SpeakerBean, BaseViewHolder> {
    public SpeakerHistoryAdapter(int layoutResId, @Nullable List<SpeakerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SpeakerBean item) {
        if (!TextUtils.isEmpty(item.getContent())) {
            helper.setText(R.id.tv_title, item.getContent());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (!TextUtils.isEmpty(item.getAddtime().getString("string"))) {
            helper.setText(R.id.tv_time, item.getAddtime().getString("string"));
        }else {
            helper.setText(R.id.tv_time, "");
        }
        if (!TextUtils.isEmpty(item.getStatus().getString("string"))) {
            helper.setText(R.id.tv_state, item.getStatus().getString("string"));
        }else {
            helper.setText(R.id.tv_state, "");
        }
    }
}
