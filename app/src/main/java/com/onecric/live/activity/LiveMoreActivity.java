package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LiveMorePresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class LiveMoreActivity extends MvpActivity<LiveMorePresenter> implements LiveMoreView, View.OnClickListener {

    public static void forward(Context context, int type) {
        Intent intent = new Intent(context, LiveMoreActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private int mType;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LiveRecommendAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected LiveMorePresenter createPresenter() {
        return new LiveMorePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_more;
    }

    @Override
    protected void initView() {
        mType = getIntent().getIntExtra("type", 0);
        if (mType == 1) {
            setTitleText(getString(R.string.today_live));
        }else if (mType == 2) {
            setTitleText(getString(R.string.history));
        }else {
            setTitleText(getString(R.string.free_live));
        }

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(this));
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
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

        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveDetailActivity.forward(mActivity, mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
            }
        });
        recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerview.addItemDecoration(new GridDividerItemDecoration(this, 10, 2));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.setNewData(list);
            }else {
                mAdapter.setNewData(new ArrayList<>());
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
