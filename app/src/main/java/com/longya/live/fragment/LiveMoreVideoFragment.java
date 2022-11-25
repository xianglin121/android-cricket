package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.LiveDetailActivity;
import com.longya.live.adapter.LiveRankingAdapter;
import com.longya.live.adapter.LiveRecommendAdapter;
import com.longya.live.adapter.decoration.GridDividerItemDecoration;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.presenter.live.LiveMoreVideoPresenter;
import com.longya.live.presenter.live.LiveRankingPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.live.LiveMoreVideoView;
import com.longya.live.view.live.LiveRankingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiveMoreVideoFragment extends MvpFragment<LiveMoreVideoPresenter> implements LiveMoreVideoView, View.OnClickListener {
    public static LiveMoreVideoFragment newInstance() {
        LiveMoreVideoFragment fragment = new LiveMoreVideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_live;
    private LiveRecommendAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_more_video;
    }

    @Override
    protected LiveMoreVideoPresenter createPresenter() {
        return new LiveMoreVideoPresenter(this);
    }

    @Override
    protected void initUI() {
        rv_live = findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                getActivity().finish();
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);

        mvpPresenter.getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<LiveBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
