package com.longya.live.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.IndexBarBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/18
 */
public class MatchFilterIndexAdapter extends BaseQuickAdapter<IndexBarBean, BaseViewHolder> {
    public MatchFilterIndexAdapter(int layoutResId, @Nullable List<IndexBarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IndexBarBean item) {
        helper.setText(R.id.tv_name, item.getName());
        if (item.isSelect()) {
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_E3AC72));
        }else {
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_666666));
        }
    }
}
