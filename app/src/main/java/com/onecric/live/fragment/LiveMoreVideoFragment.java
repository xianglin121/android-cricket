package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LiveMoreVideoPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveMoreVideoView;

import java.util.ArrayList;
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
                if(mAdapter.getItem(position).getIslive() == 0){
                    ToastUtil.show("The broadcast has not started");
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                    getActivity().finish();
                }
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
