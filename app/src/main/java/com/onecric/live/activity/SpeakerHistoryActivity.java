package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.SpeakerHistoryAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.SpeakerBean;
import com.onecric.live.presenter.user.SpeakerHistoryPresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.SpeakerHistoryView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class SpeakerHistoryActivity extends MvpActivity<SpeakerHistoryPresenter> implements SpeakerHistoryView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, SpeakerHistoryActivity.class);
        context.startActivity(intent);
    }

    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private SpeakerHistoryAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected SpeakerHistoryPresenter createPresenter() {
        return new SpeakerHistoryPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_speaker_history;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.user_speaker_history));
        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
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

        mAdapter = new SpeakerHistoryAdapter(R.layout.item_speaker_history, new ArrayList<>());
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<SpeakerBean> list) {
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
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
