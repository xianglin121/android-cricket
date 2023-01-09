package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.LiveRankingAdapter;
import com.onecric.live.model.RankingBean;
import com.onecric.live.presenter.live.LiveRankingPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveRankingView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class LiveRankingFragment extends MvpFragment<LiveRankingPresenter> implements LiveRankingView, View.OnClickListener {
    public static LiveRankingFragment newInstance(int anchorId) {
        LiveRankingFragment fragment = new LiveRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("anchorId", anchorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mAnchorId;
    private TextView tv_daily_list, tv_week_list;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_ranking;
    private LiveRankingAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_ranking;
    }

    @Override
    protected LiveRankingPresenter createPresenter() {
        return new LiveRankingPresenter(this);
    }

    @Override
    protected void initUI() {
        mAnchorId = getArguments().getInt("anchorId");

        tv_daily_list = findViewById(R.id.tv_daily_list);
        tv_week_list = findViewById(R.id.tv_week_list);
        smart_rl = findViewById(R.id.smart_rl);
        rv_ranking = findViewById(R.id.rv_ranking);

        tv_daily_list.setOnClickListener(this);
        tv_week_list.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_daily_list.setSelected(true);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(mAnchorId, tv_daily_list.isSelected()?0:1);
            }
        });

        mAdapter = new LiveRankingAdapter(R.layout.item_live_ranking, new ArrayList<>());
        rv_ranking.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ranking.setAdapter(mAdapter);

        mvpPresenter.getList(mAnchorId, tv_daily_list.isSelected()?0:1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_daily_list:
                if (tv_daily_list.isSelected()) {
                    return;
                }
                tv_daily_list.setSelected(true);
                tv_week_list.setSelected(false);
                mvpPresenter.getList(mAnchorId, 0);
                break;
            case R.id.tv_week_list:
                if (tv_week_list.isSelected()) {
                    return;
                }
                tv_daily_list.setSelected(false);
                tv_week_list.setSelected(true);
                mvpPresenter.getList(mAnchorId, 1);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<RankingBean> list) {
        smart_rl.finishRefresh();
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
