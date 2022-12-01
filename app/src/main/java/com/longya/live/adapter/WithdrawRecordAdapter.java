package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.BalanceBean;
import com.longya.live.model.WithdrawBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawBean, BaseViewHolder> {
    public WithdrawRecordAdapter(int layoutResId, @Nullable List<WithdrawBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WithdrawBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            helper.setText(R.id.tv_time, item.getAddtime());
        }else {
            helper.setText(R.id.tv_time, "");
        }
        if (!TextUtils.isEmpty(item.getArrive_amount())) {
            helper.setText(R.id.tv_amount, "+" + item.getArrive_amount());
        }else {
            helper.setText(R.id.tv_amount, "");
        }
        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_state, "Successful withdrawal");
        }else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_state, "Withdrawal failure");
        }else {
            helper.setText(R.id.tv_state, "Applying");
        }
    }
}
