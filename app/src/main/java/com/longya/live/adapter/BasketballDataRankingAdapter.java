package com.longya.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.DataRankingBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataRankingAdapter extends BaseQuickAdapter<DataRankingBean, BaseViewHolder> {
    public BasketballDataRankingAdapter(int layoutResId, @Nullable List<DataRankingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataRankingBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (item.getList() != null) {
            RecyclerView rv_inner = helper.getView(R.id.rv_inner);
            rv_inner.setLayoutManager(new LinearLayoutManager(mContext));
            rv_inner.setAdapter(new BasketballDataRankingInnerAdapter(R.layout.item_basketball_data_ranking_inner, item.getList()));
        }
    }
}
