package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.MyMessageAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MessageBean;
import com.onecric.live.presenter.user.MyMessagePresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.MyMessageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MyMessageActivity extends MvpActivity<MyMessagePresenter> implements MyMessageView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, MyMessageActivity.class);
        context.startActivity(intent);
    }

    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private MyMessageAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected MyMessagePresenter createPresenter() {
        return new MyMessagePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.user_my_msg));

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(this));
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1);
            }
        });

        mAdapter = new MyMessageAdapter(R.layout.item_my_message, new ArrayList<>());
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<MessageBean> list) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
