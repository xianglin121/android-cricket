package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketFiltrateBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class CricketFiltrateAdapter extends BaseQuickAdapter<CricketFiltrateBean, BaseViewHolder> {
    public CricketFiltrateAdapter(int layoutResId, @Nullable List<CricketFiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketFiltrateBean item) {
        helper.getView(R.id.tv_name).setSelected(item.isCheck());
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }
        helper.addOnClickListener(R.id.tv_name);
    }

}
