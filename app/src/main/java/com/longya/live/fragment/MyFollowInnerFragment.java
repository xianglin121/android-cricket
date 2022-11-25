package com.longya.live.fragment;

import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.adapter.MyFollowAdapter;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.user.MyFollowFirstPresenter;
import com.longya.live.presenter.user.MyFollowInnerPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.user.MyFollowFirstView;
import com.longya.live.view.user.MyFollowInnerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class MyFollowInnerFragment extends MvpFragment<MyFollowInnerPresenter> implements MyFollowInnerView {
    public static final int TYPE_ANCHOR = 0;
    public static final int TYPE_AUTHOR = 1;
    public static final int TYPE_USER = 2;

    public static MyFollowInnerFragment newInstance(int type) {
        MyFollowInnerFragment fragment = new MyFollowInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_follow;
    private MyFollowAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_follow_inner;
    }

    @Override
    protected MyFollowInnerPresenter createPresenter() {
        return new MyFollowInnerPresenter(this);
    }

    @Override
    public void initUI() {
        mType = getArguments().getInt("type");
        smart_rl = findViewById(R.id.smart_rl);
        rv_follow = findViewById(R.id.rv_follow);
    }

    @Override
    public void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mType, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mType, 1);
            }
        });

        mAdapter = new MyFollowAdapter(R.layout.item_my_follow_inner, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_follow) {
                    mvpPresenter.doFollow(mAdapter.getItem(position).getFollowed_id());
                }
            }
        });
        rv_follow.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_follow.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<UserBean> list) {
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
    public void doFollowSuccess(int id) {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).getFollowed_id() == id) {
                mAdapter.remove(i);
            }
        }
    }

    @Override
    public void getDataSuccess(List<UserBean> list) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
