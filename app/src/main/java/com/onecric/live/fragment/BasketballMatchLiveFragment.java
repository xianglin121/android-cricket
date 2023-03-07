package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.adapter.FootballMatchLiveAnchorAdapter;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAnchorBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.match.BasketballMatchLivePresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.BasketballMatchLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class BasketballMatchLiveFragment extends MvpFragment<BasketballMatchLivePresenter> implements BasketballMatchLiveView, View.OnClickListener {

    public static BasketballMatchLiveFragment newInstance(int id) {
        BasketballMatchLiveFragment fragment = new BasketballMatchLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private RecyclerView rv_anchor;
    private FootballMatchLiveAnchorAdapter mAnchorAdapter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mLiveAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_match_live;
    }

    @Override
    protected BasketballMatchLivePresenter createPresenter() {
        return new BasketballMatchLivePresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");
        rv_anchor = rootView.findViewById(R.id.rv_anchor);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        rv_live = rootView.findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAnchorAdapter = new FootballMatchLiveAnchorAdapter(R.layout.item_football_match_live_anchor, new ArrayList<>());
        mAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
//                    LoginActivity.forward(getContext());
                }else{
                    LiveDetailActivity.forward(getContext(), mAnchorAdapter.getItem(position).getId(), mId,mAnchorAdapter.getItem(position).getId());
                }
            }
        });
        rv_anchor.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_anchor.setAdapter(mAnchorAdapter);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, 1, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1, 1);
            }
        });

        mLiveAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mLiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mLiveAdapter.getItem(position).getIslive() == 0){
                    LiveNotStartDetailActivity.forward(getContext(),mLiveAdapter.getItem(position).getUid(),
                            mLiveAdapter.getItem(position).getMatch_id(),mLiveAdapter.getItem(position).getLive_id());
                }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
//                    LoginActivity.forward(getContext());
                }else{
                    LiveDetailActivity.forward(getContext(), mLiveAdapter.getItem(position).getUid(), mLiveAdapter.getItem(position).getMatch_id(),mLiveAdapter.getItem(position).getId());
                }
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mLiveAdapter);

        mvpPresenter.getList(true, 1, 1);
        mvpPresenter.getAnchorList(mId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<LiveAnchorBean> list) {
        if (list != null) {
            mAnchorAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mLiveAdapter.setNewData(list);
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mLiveAdapter.addData(list);
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
