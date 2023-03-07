package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.CommunityCommentActivity;
import com.onecric.live.adapter.ThemeCommunityAdapter;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.theme.ThemeCommunityFollowPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.theme.ThemeCommunityFollowView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class ThemeCommunityFollowFragment extends MvpFragment<ThemeCommunityFollowPresenter> implements ThemeCommunityFollowView {

    public static ThemeCommunityFollowFragment newInstance() {
        ThemeCommunityFollowFragment fragment = new ThemeCommunityFollowFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_community;
    private ThemeCommunityAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_community_follow;
    }

    @Override
    protected ThemeCommunityFollowPresenter createPresenter() {
        return new ThemeCommunityFollowPresenter(this);
    }

    @Override
    protected void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        rv_community = findViewById(R.id.rv_community);
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
                mvpPresenter.getData(false, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getData(true, 1);
            }
        });

        mAdapter = new ThemeCommunityAdapter(R.layout.item_theme_community, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommunityCommentActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getId());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_like) {
                    CommunityBean item = mAdapter.getItem(position);
                    int like = item.getLikes();
                    if (item.getIs_likes() == 1) {
                        like--;
                        item.setIs_likes(0);
                    } else {
                        like++;
                        item.setIs_likes(1);
                    }
                    item.setLikes(like);
                    mAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    mvpPresenter.doCommunityLike(item.getId());
                }
            }
        });
        rv_community.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_community.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getList(boolean isRefresh, List<CommunityBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                } else {
                    showEmptyView();
                }
                mAdapter.setNewData(list);
            } else {
                mAdapter.setNewData(new ArrayList<>());
                showEmptyView();
            }
        } else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            } else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }
}
