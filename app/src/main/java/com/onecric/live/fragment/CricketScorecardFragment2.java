package com.onecric.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.CricketScorecardAdapter;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ScorecardBatterBean;
import com.onecric.live.model.ScorecardBowlerBean;
import com.onecric.live.model.ScorecardWicketBean;
import com.onecric.live.presenter.cricket.CricketScorecardPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketScorecardView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketScorecardFragment2 extends MvpFragment<CricketScorecardPresenter>implements CricketScorecardView {
    public static CricketScorecardFragment2 newInstance() {
        CricketScorecardFragment2 fragment = new CricketScorecardFragment2();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView recyclerView;
    private CricketScorecardAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_scorecard2;
    }

    @Override
    protected CricketScorecardPresenter createPresenter() {
        return new CricketScorecardPresenter(this);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new CricketScorecardAdapter(R.layout.item_scorecard,new ArrayList<>());
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mAdapter.setEmptyView(inflate2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    public void getData(CricketMatchBean model) {

        if(model.competition_list != null && model.competition_list.size()>0){
            mAdapter.match_id = model.getMatch_id();
            mAdapter.setNewData(model.competition_list);
        }else{
            mAdapter.setNewData(new ArrayList<>());
        }
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

    }

    @Override
    public void getHomeDataSuccess(List<ScorecardBatterBean> batterList, List<ScorecardBowlerBean> bowlerList, List<ScorecardWicketBean> wicketList, String extras, String noBattingName) {

    }

    @Override
    public void getAwayDataSuccess(List<ScorecardBatterBean> batterList, List<ScorecardBowlerBean> bowlerList, List<ScorecardWicketBean> wicketList, String extras, String noBattingName) {

    }
}
