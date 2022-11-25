package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CountryCodeBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/5
 */
public class PhonePrefixAdapter extends BaseQuickAdapter<CountryCodeBean, BaseViewHolder> {
    public PhonePrefixAdapter(int layoutResId, @Nullable List<CountryCodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CountryCodeBean item) {
        if (!TextUtils.isEmpty(item.getCode())) {
            helper.setText(R.id.tv_code, item.getCode());
        }else {
            helper.setText(R.id.tv_code, "");
        }
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
    }
}
