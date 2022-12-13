package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.DataBestLeftBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataBestLeftAdapter extends BaseQuickAdapter<DataBestLeftBean, BaseViewHolder> {
    public BasketballDataBestLeftAdapter(int layoutResId, @Nullable List<DataBestLeftBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataBestLeftBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (item.isSelect()) {
            helper.getView(R.id.cl_root).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.tv_indicator).setVisibility(View.VISIBLE);
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_E3AC72));
        }else {
            helper.getView(R.id.cl_root).setBackgroundColor(mContext.getResources().getColor(R.color.c_F3F3F7));
            helper.getView(R.id.tv_indicator).setVisibility(View.GONE);
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_999999));
        }
    }
}
