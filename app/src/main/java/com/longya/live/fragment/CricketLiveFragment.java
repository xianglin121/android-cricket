package com.longya.live.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.longya.live.adapter.CricketNewLiveAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.CricketLiveBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.cricket.CricketFantasyPresenter;
import com.longya.live.presenter.cricket.CricketLivePresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.cricket.CricketFantasyView;
import com.longya.live.view.cricket.CricketLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    private CricketNewLiveAdapter mAdapter;
    private List<CricketLiveBean> mCricketLiveBeanList;

    public static final int TYPE_REFRESH = 1;
    public static final int TYPE_LOADMORE = 2;
    private int mPage = 1;
    public static final int mLimit = 2;

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
        mCricketLiveBeanList = new ArrayList<>();
        mMatchId = getArguments().getInt("matchId");
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));

        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mvpPresenter.getList(mMatchId, mPage, mLimit, TYPE_LOADMORE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mvpPresenter.getList(mMatchId, mPage, mLimit, TYPE_REFRESH);
            }
        });

        mAdapter = new CricketNewLiveAdapter(mCricketLiveBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        mAdapter.setEmptyView(inflate);

        mvpPresenter.getList(mMatchId, mPage, mLimit, TYPE_REFRESH);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<CricketLiveBean> list) {
        mPage = 1;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        }
        finishRefreshAndLoadMore();
    }

    @Override
    public void getDataFail(String msg) {
        mPage = 1;
        finishRefreshAndLoadMore();
    }

    @Override
    public void loadMoreDataSuccess(List<CricketLiveBean> list) {
        if (list != null && list.size()>0) {
            mAdapter.addData(list);
        } else {
            smart_rl.setEnableLoadMore(false);
        }
        finishRefreshAndLoadMore();
    }

    @Override
    public void loadMoreDataFailed() {
        mPage--;
        finishRefreshAndLoadMore();
    }

    private void finishRefreshAndLoadMore() {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }
}
