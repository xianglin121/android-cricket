package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CoinBean;
import com.longya.live.model.RedEnvelopeBean;
import com.longya.live.util.StringUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class RedEnvelopeAdapter extends BaseQuickAdapter<RedEnvelopeBean, BaseViewHolder> {
    public RedEnvelopeAdapter(int layoutResId, @Nullable List<RedEnvelopeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RedEnvelopeBean item) {
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        } else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_diamond, item.getAmount() + mContext.getString(R.string.diamond));
        helper.setText(R.id.tv_time, StringUtil.getDurationText(item.getCountdown_time() * 1000) + mContext.getString(R.string.second));
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, RedEnvelopeBean item, @NonNull List<Object> payloads) {
        super.convertPayloads(helper, item, payloads);
        if (payloads != null && payloads.size() > 0) {
            helper.setText(R.id.tv_time, StringUtil.getDurationText(item.getCountdown_time() * 1000) + mContext.getString(R.string.second));
        }
    }
}
