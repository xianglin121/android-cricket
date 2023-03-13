package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.getDayInfo;

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
import com.onecric.live.model.CricketDayBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CricketDayAdapter extends BaseQuickAdapter<CricketDayBean, BaseViewHolder> {
    CricketNewFragment fragment;

    public CricketDayAdapter(CricketNewFragment fragment,int layoutResId, List<CricketDayBean> list) {
        super(layoutResId,list);
        this.fragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketDayBean item) {
        String dates[] = getDayInfo(item.date);
        fragment.setDayInfo(dates,new Date().getTime());
        helper.setText(R.id.tv_date,dates[0]);
        helper.setText(R.id.tv_month,dates[1]);
        helper.setText(R.id.tv_day,dates[2]);

        CricketNewAdapter mAdapter = new CricketNewAdapter(R.layout.item_cricket_new, new ArrayList<>());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_title) {
                CricketInnerActivity.forward(mContext, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getTournamentId());
            }
        });
        RecyclerView rv_cricket = helper.getView(R.id.rv_cricket);
        rv_cricket.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cricket.setAdapter(mAdapter);
    }
}
