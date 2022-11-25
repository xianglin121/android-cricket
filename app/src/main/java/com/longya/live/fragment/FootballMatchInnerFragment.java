package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.CommonAppConfig;
import com.longya.live.Constant;
import com.longya.live.R;
import com.longya.live.activity.FootballMatchDetailActivity;
import com.longya.live.adapter.FootballMatchInnerAdapter;
import com.longya.live.custom.FootballGoalDialog;
import com.longya.live.event.ChangeBasketballLanguageEvent;
import com.longya.live.event.ChangeFootballLanguageEvent;
import com.longya.live.event.UpdateFootballCollectCountEvent;
import com.longya.live.event.UpdateRedAndYellowCardEvent;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.model.MatchSocketBean;
import com.longya.live.presenter.match.FootballMatchInnerPresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchInnerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FootballMatchInnerFragment extends MvpFragment<FootballMatchInnerPresenter> implements FootballMatchInnerView {

    public static FootballMatchInnerFragment newInstance(int type) {
        FootballMatchInnerFragment fragment = new FootballMatchInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private FootballMatchInnerAdapter mAdapter;

    private int mPage = 1;
    private String mId;

    public void updateId(String id) {
        mId = id;
        if (smart_rl != null) {
            smart_rl.autoRefresh();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_inner;
    }

    @Override
    protected FootballMatchInnerPresenter createPresenter() {
        return new FootballMatchInnerPresenter(this);
    }

    @Override
    protected void initUI() {
        mType = getArguments().getInt("type");
        smart_rl = rootView.findViewById(R.id.smart_rl);
        recyclerview = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mType, mPage, mId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mType, 1, mId);
            }
        });

        mAdapter = new FootballMatchInnerAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FootballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getId());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_collect) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        ToastUtil.show(getActivity().getString(R.string.please_login));
                        return;
                    }
                    FootballMatchBean item = mAdapter.getItem(position);
                    if (item.getIs_collect() == 1) {
                        item.setIs_collect(0);
                    }else {
                        item.setIs_collect(1);
                    }
                    mvpPresenter.doCollect(2, item.getId());
                    if (mType == 4) {
                        mAdapter.remove(position);
                    }else {
                        mAdapter.notifyItemChanged(position);
                    }
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    public void updateData(MatchSocketBean dataBean) {
        if(mAdapter != null) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                FootballMatchBean item = mAdapter.getItem(i);
                if (item.getId() == dataBean.getId()) {
                    item.setStatus(dataBean.getStatus());
                    item.setHome_score(String.valueOf(dataBean.getHome_score()));
                    item.setAway_score(String.valueOf(dataBean.getAway_score()));
                    item.setHome_yellow_card(dataBean.getHome_yellow_card());
                    item.setAway_yellow_card(dataBean.getAway_yellow_card());
                    item.setHome_red_card(dataBean.getHome_red_card());
                    item.setAway_red_card(dataBean.getAway_red_card());
                    item.setHalf_score(dataBean.getHalf_score());
                    item.setCorner_kick(dataBean.getCorner_kick());
                    item.setMatch_str(dataBean.getMatch_str());
                    mAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    public void updateTime() {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                FootballMatchBean item = mAdapter.getItem(i);
                Long match_time = item.getMatch_time();
                if (match_time != null) {
                    if (item.getStatus() == 2) {
                        match_time = System.currentTimeMillis()/1000 - match_time;
                        if (match_time < (45*60)) {
                            if ((match_time + 60) < (45*60)) {
                                match_time = match_time + 60;
                                item.setMatch_str((match_time/60) + "");
                            }else {
                                item.setMatch_str("45+");
                            }
                        }
                    }else if (item.getStatus() == 4) {
                        match_time = System.currentTimeMillis()/1000 - match_time + (45*60);
                        if (match_time < (90*60)) {
                            if ((match_time + 60) < (90*60)) {
                                match_time = match_time + 60;
                                item.setMatch_str((match_time/60) + "");
                            }else {
                                item.setMatch_str("90+");
                            }
                        }
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<FootballMatchBean> list) {
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<FootballMatchBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null && list.size() > 0) {
                hideEmptyView();
                mAdapter.setNewData(list);
            }else {
                mAdapter.setNewData(new ArrayList<>());
                if (mType == 4) {
                    setEmptyText(getActivity().getString(R.string.empty_match_collect));
                }else {
                    setEmptyText(getActivity().getString(R.string.empty_common));
                }
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
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    public List<FootballMatchBean> getData() {
        List<FootballMatchBean> list = new ArrayList();
        if (mAdapter != null) {
            list.addAll(mAdapter.getData());
        }
        return list;
    }

    public void refreshData() {
        if (smart_rl != null) {
            smart_rl.autoRefresh();
        }
    }
}
