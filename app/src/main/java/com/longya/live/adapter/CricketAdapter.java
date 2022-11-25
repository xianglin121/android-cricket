package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.activity.CricketDetailActivity;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.CricketMatchBean;
import com.longya.live.model.CricketTournamentBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketAdapter extends BaseQuickAdapter<CricketTournamentBean, BaseViewHolder> {
    public CricketAdapter(int layoutResId, @Nullable List<CricketTournamentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketTournamentBean item) {
        helper.addOnClickListener(R.id.tv_title);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        RecyclerView rv_inner = helper.getView(R.id.rv_inner);
        rv_inner.setLayoutManager(new LinearLayoutManager(mContext));
        List<CricketMatchBean> tempList = item.getCricket_match();
        List<CricketMatchBean> list = new ArrayList<>();
        if (tempList != null) {
            if (tempList.size() > 3) {
                list = tempList.subList(0, 3);
                helper.getView(R.id.tv_see_more).setVisibility(View.VISIBLE);
            }else {
                list = tempList;
                helper.getView(R.id.tv_see_more).setVisibility(View.GONE);
            }
        }
        CricketInnerAdapter innerAdapter = new CricketInnerAdapter(R.layout.item_cricket_inner, list);
        innerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CricketDetailActivity.forward(mContext, innerAdapter.getItem(position).getId());
            }
        });
        rv_inner.setAdapter(innerAdapter);
        helper.getView(R.id.tv_see_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getView(R.id.tv_see_more).setVisibility(View.GONE);
                innerAdapter.setNewData(item.getCricket_match());
            }
        });
    }
}
