package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.CommunityCommentActivity;
import com.onecric.live.adapter.MySpaceCommunityTwoAdapter;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.user.MySpaceCommunityTwoPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.user.MySpaceCommunityTwoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MySpaceCommunityTwoFragment extends MvpFragment<MySpaceCommunityTwoPresenter> implements MySpaceCommunityTwoView {

    public static MySpaceCommunityTwoFragment newInstance(int uid) {
        MySpaceCommunityTwoFragment fragment = new MySpaceCommunityTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mUid;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private MySpaceCommunityTwoAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_space_headline_two;
    }

    @Override
    protected MySpaceCommunityTwoPresenter createPresenter() {
        return new MySpaceCommunityTwoPresenter(this);
    }

    @Override
    protected void initUI() {
        mUid = getArguments().getInt("uid");
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
                mvpPresenter.getData(false, mPage, mUid);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getData(true, 1, mUid);
            }
        });

        mAdapter = new MySpaceCommunityTwoAdapter(R.layout.item_my_space_community_two, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_title) {
                    CommunityCommentActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getCircle_id());
                }else if (view.getId() == R.id.ll_like) {
                    CommunityBean item = mAdapter.getItem(position);
                    int like = item.getComment_likes();
                    if (item.getIs_comment_likes() == 0) {
                        item.setIs_comment_likes(1);
                        like++;
                    }else {
                        item.setIs_comment_likes(0);
                        like--;
                    }
                    item.setComment_likes(like);
                    mAdapter.notifyItemChanged(position);
                    mvpPresenter.doLike(item.getId());
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<CommunityBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                }else {
                    showEmptyView();
                }
                mAdapter.setNewData(list);
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
