package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.getDayInfo;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.CricketInnerActivity;
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.model.CricketAllBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CricketDayAdapter extends BaseQuickAdapter<CricketAllBean.CricketDayBean, BaseViewHolder> {
    CricketNewFragment fragment;

    public CricketDayAdapter(CricketNewFragment fragment,int layoutResId, List<CricketAllBean.CricketDayBean> list) {
        super(layoutResId,list);
        this.fragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketAllBean.CricketDayBean item) {
        String dates[] = getDayInfo(item.getDay());
//        fragment.setDayInfo(dates,new Date().getTime());
        helper.setText(R.id.tv_date,dates[0]);
        helper.setText(R.id.tv_month,dates[1]);
        helper.setText(R.id.tv_day,dates[2]);
        if(dates[0].equals("Today")){
            fragment.todayPosition = helper.getLayoutPosition();
        }

        CricketNewAdapter mAdapter = new CricketNewAdapter(R.layout.item_cricket_new, item.getList());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_title && !TextUtils.isEmpty(mAdapter.getItem(position).getTournamentId())) {
                CricketInnerActivity.forward(mContext, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getType(), Integer.parseInt(mAdapter.getItem(position).getTournamentId()));
            }
        });
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_match_rv_empty, null, false);
        mAdapter.setEmptyView(inflate);
        RecyclerView rv_cricket = helper.getView(R.id.rv_cricket);
        rv_cricket.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cricket.setAdapter(mAdapter);
    }
}
