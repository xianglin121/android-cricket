package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CricketTournamentBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class SelectTournamentAdapter extends BaseQuickAdapter<CricketTournamentBean, BaseViewHolder> {
    public SelectTournamentAdapter(int layoutResId, @Nullable List<CricketTournamentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketTournamentBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.getView(R.id.iv_check).setSelected(item.isCheck());
    }
}
