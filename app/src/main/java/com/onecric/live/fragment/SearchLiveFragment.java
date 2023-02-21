package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.SearchLiveDetailActivity;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.SearchLiveSecondPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.SearchLiveSecondView;

import java.util.ArrayList;
import java.util.List;

public class SearchLiveFragment extends MvpFragment<SearchLiveSecondPresenter> implements SearchLiveSecondView, View.OnClickListener {
    public static SearchLiveFragment newInstance(String content) {
        SearchLiveFragment fragment = new SearchLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mContent;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_live;
    }

    @Override
    protected SearchLiveSecondPresenter createPresenter() {
        return new SearchLiveSecondPresenter(this);
    }

    @Override
    protected void initUI() {
        mContent = getArguments().getString("content");
        rv_live = findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mAdapter.getItem(position).getIslive() == 0){
                    LiveNotStartDetailActivity.forward(getContext(),mAdapter.getItem(position).getUid(),mAdapter.getItem(position).getMatch_id());
                }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                    ((SearchLiveDetailActivity)getActivity()).loginDialog.show();
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                }
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);

        mvpPresenter.getList(mContent);
    }

    public void updateData(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent = content;
            if (mvpPresenter != null) {
                mvpPresenter.getList(mContent);
            }
        }
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
