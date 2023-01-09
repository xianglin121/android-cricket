package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.adapter.LiveClassifyAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.presenter.live.SearchLiveMatchPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.SearchLiveMatchView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
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
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
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
