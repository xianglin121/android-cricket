package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.model.CricketNewBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketNewAdapter extends BaseQuickAdapter<CricketNewBean, BaseViewHolder> {
    public CricketNewAdapter(int layoutResId, @Nullable List<CricketNewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketNewBean item) {
        helper.addOnClickListener(R.id.ll_title);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        } else {
            helper.setText(R.id.tv_title, "");
        }
        RecyclerView rv_inner = helper.getView(R.id.rv_inner);
        rv_inner.setLayoutManager(new LinearLayoutManager(mContext));
        List<CricketNewBean.CricketMatchNewBean> tempList = item.getCricketMatch();

        CricketInnerNewAdapter innerAdapter = new CricketInnerNewAdapter(R.layout.item_cricket_inner_new, tempList);
        innerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.tv_state_live){
                //跳转主播直播页
                if(innerAdapter.getItem(position).getLiveId() != 0 && "1".equals(innerAdapter.getItem(position).getLiveStatus())){
                    LiveDetailActivity.forward(mContext,innerAdapter.getItem(position).getLiveId(),innerAdapter.getItem(position).getId(),innerAdapter.getItem(position).getLiveId());
                }
            }else if(view.getId() == R.id.tv_state_watch_live){
                //跳转赛程直播页
                CricketDetailActivity.forward(mContext, innerAdapter.getItem(position).getId(),1);
            }
        });

        innerAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(innerAdapter.getItem(position).getStatus() == 1){
                //跳赛程比分tab
                CricketDetailActivity.forward(mContext, innerAdapter.getItem(position).getId(),2);
            }else{
                CricketDetailActivity.forward(mContext, innerAdapter.getItem(position).getId());
            }
        });
        rv_inner.setAdapter(innerAdapter);

    }

}
