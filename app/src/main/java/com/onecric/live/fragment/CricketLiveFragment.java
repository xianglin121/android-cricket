package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.CricketNewLiveAdapter;
import com.onecric.live.model.CricketLiveBean;
import com.onecric.live.presenter.cricket.CricketLivePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
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
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
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
        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
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

    public void getData(int mId) {
        mMatchId = mId;
        mvpPresenter.getList(mId, mPage, mLimit, TYPE_REFRESH);
    }

}
