package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.CricketTournamentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketAdapter extends BaseQuickAdapter<CricketTournamentBean, BaseViewHolder> {
    MainActivity mainActivity;

    public CricketAdapter(MainActivity mainActivity, int layoutResId, @Nullable List<CricketTournamentBean> data) {
        super(layoutResId, data);
        this.mainActivity = mainActivity;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketTournamentBean item) {
        helper.addOnClickListener(R.id.tv_title);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        } else {
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
            } else {
                list = tempList;
                helper.getView(R.id.tv_see_more).setVisibility(View.GONE);
            }
        }
        CricketInnerAdapter innerAdapter = new CricketInnerAdapter(mainActivity,R.layout.item_cricket_inner, list);
        innerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(innerAdapter.getItem(position).getLive_id()!=0 && innerAdapter.getItem(position).getStatus() == 1){
                    //fixme 显示直播item 点击去直播详情
                    LiveDetailActivity.forward(mContext,innerAdapter.getItem(position).getLive_uid(),innerAdapter.getItem(position).getId(),innerAdapter.getItem(position).getLive_id());
                }else{
                    CricketDetailActivity.forward(mContext, innerAdapter.getItem(position).getId());
                }
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
