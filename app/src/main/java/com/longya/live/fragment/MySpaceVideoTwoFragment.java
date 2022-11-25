package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.VideoPagerActivity;
import com.longya.live.adapter.VideoAdapter;
import com.longya.live.adapter.decoration.StaggeredDividerItemDecoration;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.presenter.user.MySpaceVideoOnePresenter;
import com.longya.live.presenter.user.MySpaceVideoTwoPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.user.MySpaceVideoOneView;
import com.longya.live.view.user.MySpaceVideoTwoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MySpaceVideoTwoFragment extends MvpFragment<MySpaceVideoTwoPresenter> implements MySpaceVideoTwoView {
    public static MySpaceVideoTwoFragment newInstance(int uid) {
        MySpaceVideoTwoFragment fragment = new MySpaceVideoTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mUid;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_video;
    private VideoAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_space_video_two;
    }

    @Override
    protected MySpaceVideoTwoPresenter createPresenter() {
        return new MySpaceVideoTwoPresenter(this);
    }

    @Override
    protected void initUI() {
        mUid = getArguments().getInt("uid");
        smart_rl = findViewById(R.id.smart_rl);
        rv_video = findViewById(R.id.rv_video);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage, mUid);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1, mUid);
            }
        });

        mAdapter = new VideoAdapter(R.layout.item_video, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoPagerActivity.forward(getContext(), mAdapter.getData(), position, mPage);
            }
        });
        rv_video.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_video.addItemDecoration(new StaggeredDividerItemDecoration(getContext(), 10, 2));
        rv_video.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.getData().clear();
                mAdapter.getData().addAll(list);
                mAdapter.notifyItemInserted(list.size());
                if (list.size() > 0) {
                    hideEmptyView();
                }else {
                    showEmptyView();
                }
            }else {
                mAdapter.setNewData(new ArrayList<>());
                showEmptyView();
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
}
