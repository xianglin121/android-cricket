package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.getDayInfo;

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
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.model.CricketNewBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketNewAdapter extends BaseQuickAdapter<CricketNewBean, BaseViewHolder> {
    CricketNewFragment fragment;
    private int lastCount = 0;

    public CricketNewAdapter(CricketNewFragment fragment, int layoutResId, @Nullable List<CricketNewBean> data) {
        super(layoutResId, data);
        this.fragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketNewBean item) {
        //是否是新的一天
        if(helper.getLayoutPosition() == 0 || item.isHasTitle || (helper.getLayoutPosition() == lastCount && fragment.isMore)){//
            item.isHasTitle = true;
            lastCount = this.getItemCount();
            helper.getView(R.id.rl_time_title).setVisibility(View.VISIBLE);
            String dates[] = getDayInfo(item.date);
//            fragment.setDayInfo(dates);
            helper.setText(R.id.tv_date,dates[0]);
            helper.setText(R.id.tv_month,dates[1]);
            helper.setText(R.id.tv_day,dates[2]);
        }else{
            helper.getView(R.id.rl_time_title).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.ll_title);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        } else {
            helper.setText(R.id.tv_title, "");
        }
        RecyclerView rv_inner = helper.getView(R.id.rv_inner);
        rv_inner.setLayoutManager(new LinearLayoutManager(mContext));
        List<CricketNewBean.CricketMatchNewBean> tempList = item.getCricketMatch();

        CricketInnerNewAdapter innerAdapter = new CricketInnerNewAdapter(fragment,R.layout.item_cricket_inner_new, tempList,item);
        innerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.tv_state_live){
                //跳转主播直播页
                if(innerAdapter.getItem(position).getLiveId() != 0 && innerAdapter.getItem(position).getLiveStatus() == 1){
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
