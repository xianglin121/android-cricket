package com.longya.live.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.activity.PlayerStatsActivity;
import com.longya.live.activity.TeamComparisonActivity;
import com.longya.live.adapter.CricketAdapter;
import com.longya.live.adapter.CricketLiveAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.cricket.CricketFantasyPresenter;
import com.longya.live.presenter.cricket.CricketLivePresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.cricket.CricketFantasyView;
import com.longya.live.view.cricket.CricketLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketLiveFragment extends MvpFragment<CricketLivePresenter> implements CricketLiveView {
    public static CricketLiveFragment newInstance(int matchId) {
        CricketLiveFragment fragment = new CricketLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("matchId", matchId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mMatchId;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private CricketLiveAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_live;
    }

    @Override
    protected CricketLivePresenter createPresenter() {
        return new CricketLivePresenter(this);
    }

    @Override
    protected void initUI() {
        mMatchId = getArguments().getInt("matchId");
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(mMatchId);
            }
        });
        mAdapter = new CricketLiveAdapter(R.layout.item_cricket_live, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mvpPresenter.getList(mMatchId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<String> list) {
        smart_rl.finishRefresh();
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
