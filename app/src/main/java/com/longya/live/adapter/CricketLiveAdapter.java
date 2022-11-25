package com.longya.live.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/31
 */
public class CricketLiveAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CricketLiveAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (!TextUtils.isEmpty(item)) {
            helper.setText(R.id.tv_text, item);
        }else {
            helper.setText(R.id.tv_text, "");
        }
    }
}
