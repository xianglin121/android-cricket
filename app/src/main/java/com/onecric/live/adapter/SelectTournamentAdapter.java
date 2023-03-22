package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.CricketTournamentBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class SelectTournamentAdapter extends BaseQuickAdapter<CricketFiltrateBean, BaseViewHolder> {
    public SelectTournamentAdapter(int layoutResId, @Nullable List<CricketFiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketFiltrateBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.getView(R.id.iv_check).setSelected(item.isCheck());
    }
}
