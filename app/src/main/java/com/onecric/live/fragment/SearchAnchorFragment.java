package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.MyFollowAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.live.SearchAnchorPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.SearchAnchorView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class SearchAnchorFragment extends MvpFragment<SearchAnchorPresenter> implements SearchAnchorView {

    public static SearchAnchorFragment newInstance(String content) {
        SearchAnchorFragment fragment = new SearchAnchorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mContent;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_anchor;
    private MyFollowAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_anchor;
    }

    @Override
    protected SearchAnchorPresenter createPresenter() {
        return new SearchAnchorPresenter(this);
    }

    @Override
    public void initUI() {
        mContent = getArguments().getString("content");
        smart_rl = findViewById(R.id.smart_rl);
        rv_anchor = findViewById(R.id.rv_anchor);
    }

    @Override
    public void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mContent, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mContent, 1);
            }
        });

        mAdapter = new MyFollowAdapter(R.layout.item_my_follow_inner, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_follow) {
                    mvpPresenter.doFollow(mAdapter.getItem(position).getId());
                }
            }
        });
        rv_anchor.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_anchor.setAdapter(mAdapter);

        mvpPresenter.getList(true, mContent, 1);
    }

    public void updateData(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent = content;
            if (mvpPresenter != null) {
                mvpPresenter.getList(true, mContent, 1);
            }
        }
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
    public void doFollowSuccess(int id) {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).getId() == id) {
                mAdapter.remove(i);
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
