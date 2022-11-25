package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CoinBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class ChargeAdapter extends BaseQuickAdapter<CoinBean, BaseViewHolder> {
    public ChargeAdapter(int layoutResId, @Nullable List<CoinBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CoinBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getMoney())) {
            helper.setText(R.id.tv_cost, "¥ " + item.getMoney());
        }else {
            helper.setText(R.id.tv_cost, "");
        }
        LinearLayout ll_coin = helper.getView(R.id.ll_coin);
        ll_coin.setSelected(item.isSelect());
    }
}
