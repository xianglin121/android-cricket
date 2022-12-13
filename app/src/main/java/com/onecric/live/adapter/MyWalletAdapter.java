package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.BalanceBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyWalletAdapter extends BaseQuickAdapter<BalanceBean, BaseViewHolder> {
    public MyWalletAdapter(int layoutResId, @Nullable List<BalanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BalanceBean item) {
        if (!TextUtils.isEmpty(item.getType())) {
            helper.setText(R.id.tv_title, item.getType());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            helper.setText(R.id.tv_time, item.getAddtime());
        }else {
            helper.setText(R.id.tv_time, "");
        }
        if (item.getType2() == 1) {
            if (!TextUtils.isEmpty(item.getChange())) {
                helper.setText(R.id.tv_amount, "+" + item.getChange());
            }else {
                helper.setText(R.id.tv_amount, "");
            }
        }else {
            if (!TextUtils.isEmpty(item.getChange())) {
                helper.setText(R.id.tv_amount, "-" + item.getChange());
            }else {
                helper.setText(R.id.tv_amount, "");
            }
        }
    }
}
