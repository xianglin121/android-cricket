package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.BasketballMatchDetailActivity;
import com.longya.live.activity.FootballMatchDetailActivity;
import com.longya.live.adapter.LiveClassifyAdapter;
import com.longya.live.adapter.MatchSelectDateAdapter;
import com.longya.live.model.DateBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.presenter.live.LiveClassifyPresenter;
import com.longya.live.presenter.live.SearchLiveMatchPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.live.LiveClassifyView;
import com.longya.live.view.live.SearchLiveMatchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchLiveMatchFragment extends MvpFragment<SearchLiveMatchPresenter> implements SearchLiveMatchView, View.OnClickListener {

    public static SearchLiveMatchFragment newInstance(String content) {
        SearchLiveMatchFragment fragment = new SearchLiveMatchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mContent;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LiveClassifyAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_live_match;
    }

    @Override
    protected SearchLiveMatchPresenter createPresenter() {
        return new SearchLiveMatchPresenter(this);
    }

    @Override
    protected void initUI() {
        mContent = getArguments().getString("content");
        smart_rl = rootView.findViewById(R.id.smart_rl);
        recyclerview = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.searchMatch(false, mContent);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.searchMatch(true, mContent);
            }
        });
        mAdapter = new LiveClassifyAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getItem(position).getType() == 0) {
                    FootballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }else if (mAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_state) {
                    if (mAdapter.getItem(position).getStatus_type() == 0 && mAdapter.getItem(position).getReserve() == 0) {
                        mvpPresenter.doReserve(position, mAdapter.getItem(position).getSourceid(), mAdapter.getItem(position).getType());
                    }
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(mAdapter);

        mvpPresenter.searchMatch(true, mContent);
    }

    public void updateData(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent = content;
            if (mvpPresenter != null) {
                mvpPresenter.searchMatch(true, mContent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<MatchListBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.setNewData(list);
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
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
    public void doReserveSuccess(int position) {
        MatchListBean item = mAdapter.getItem(position);
        if (item.getReserve() == 0) {
            item.setReserve(1);
        }else {
            item.setReserve(0);
        }
        mAdapter.notifyItemChanged(position);
    }
}
