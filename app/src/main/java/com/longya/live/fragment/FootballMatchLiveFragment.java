package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.longya.live.R;
import com.longya.live.activity.LiveDetailActivity;
import com.longya.live.adapter.FootballEventLiveAdapter;
import com.longya.live.adapter.FootballMatchImportantAdapter;
import com.longya.live.adapter.FootballMatchLiveAnchorAdapter;
import com.longya.live.adapter.FootballMatchStatisticAdapter;
import com.longya.live.adapter.LiveRecommendAdapter;
import com.longya.live.adapter.decoration.GridDividerItemDecoration;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveAnchorBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.LiveUserBean;
import com.longya.live.presenter.match.FootballMatchLivePresenter;
import com.longya.live.presenter.match.FootballMatchStatusPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchLiveView;
import com.longya.live.view.match.FootballMatchStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FootballMatchLiveFragment extends MvpFragment<FootballMatchLivePresenter> implements FootballMatchLiveView, View.OnClickListener {

    public static FootballMatchLiveFragment newInstance(int id) {
        FootballMatchLiveFragment fragment = new FootballMatchLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private RecyclerView rv_anchor;
    private FootballMatchLiveAnchorAdapter mAnchorAdapter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mLiveAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_live;
    }

    @Override
    protected FootballMatchLivePresenter createPresenter() {
        return new FootballMatchLivePresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");
        rv_anchor = rootView.findViewById(R.id.rv_anchor);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        rv_live = rootView.findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAnchorAdapter = new FootballMatchLiveAnchorAdapter(R.layout.item_football_match_live_anchor, new ArrayList<>());
        mAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveDetailActivity.forward(getContext(), mAnchorAdapter.getItem(position).getId(), 0, mId);
            }
        });
        rv_anchor.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_anchor.setAdapter(mAnchorAdapter);

        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, 0, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 0, 1);
            }
        });

        mLiveAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mLiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveDetailActivity.forward(getContext(), mLiveAdapter.getItem(position).getUid(), mLiveAdapter.getItem(position).getType(), mLiveAdapter.getItem(position).getMatch_id());
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mLiveAdapter);

        mvpPresenter.getList(true, 0, 1);
        mvpPresenter.getAnchorList(mId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<LiveAnchorBean> list) {
        if (list != null) {
            mAnchorAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mLiveAdapter.setNewData(list);
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mLiveAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
