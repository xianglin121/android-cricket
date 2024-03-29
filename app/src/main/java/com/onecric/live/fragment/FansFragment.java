package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.MyFansAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.user.FansPresenter;
import com.onecric.live.presenter.user.MyFansPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.user.MyFansView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class FansFragment extends MvpFragment<FansPresenter> implements MyFansView {
    public static FansFragment newInstance(int uid) {
        FansFragment fragment = new FansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_follow;
    private MyFansAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_fans;
    }

    @Override
    protected FansPresenter createPresenter() {
        return new FansPresenter(this);
    }

    @Override
    public void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        rv_follow = findViewById(R.id.rv_follow);
    }

    @Override
    public void initData() {
        int mUid = getArguments().getInt("uid");
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
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

        mAdapter = new MyFansAdapter(R.layout.item_my_follow_inner, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_follow) {
                    mvpPresenter.doFollow(mAdapter.getItem(position).getUid());
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
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<UserBean> list) {
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
    public void doFollowSuccess(int id) {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).getUid() == id) {
                if (mAdapter.getItem(i).isIs_attention() == 0) {
                    mAdapter.getItem(i).setIs_attention(1);
                } else {
                    mAdapter.getItem(i).setIs_attention(0);
                }
                mAdapter.notifyItemChanged(i);
            }
        }

    }
}
