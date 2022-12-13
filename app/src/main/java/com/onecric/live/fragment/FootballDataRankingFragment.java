package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.FootballDataRankingAdapter;
import com.onecric.live.model.FootballDataRankingBean;
import com.onecric.live.presenter.match.FootballDataRankingPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.FootballDataRankingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class FootballDataRankingFragment extends MvpFragment<FootballDataRankingPresenter> implements FootballDataRankingView, View.OnClickListener {
    public static FootballDataRankingFragment newInstance() {
        FootballDataRankingFragment fragment = new FootballDataRankingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mSeasonId;
    private TextView tv_all;
    private View line_all;
    private TextView tv_home;
    private View line_home;
    private TextView tv_away;
    private View line_away;
    private RecyclerView rv_ranking;
    private FootballDataRankingAdapter mAdapter;

    public int mType;//0:全部  1:主场  2:客场

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_data_ranking;
    }

    @Override
    protected FootballDataRankingPresenter createPresenter() {
        return new FootballDataRankingPresenter(this);
    }

    @Override
    protected void initUI() {
        tv_all = findViewById(R.id.tv_all);
        line_all = findViewById(R.id.line_all);
        tv_home = findViewById(R.id.tv_home);
        line_home = findViewById(R.id.line_home);
        tv_away = findViewById(R.id.tv_away);
        line_away = findViewById(R.id.line_away);
        rv_ranking = findViewById(R.id.rv_ranking);

        findViewById(R.id.ll_all).setOnClickListener(this);
        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_away).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new FootballDataRankingAdapter(R.layout.item_football_data_ranking, new ArrayList<>(), this);
        rv_ranking.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ranking.setAdapter(mAdapter);
    }

    public void getList(int id) {
        mSeasonId = id;
        mvpPresenter.getList(id, mType);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<FootballDataRankingBean> list) {
        if (list != null) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                if (mType == 0) {
                    return;
                }
                mType = 0;
                toggleType();
                mvpPresenter.getList(mSeasonId, mType);
                break;
            case R.id.ll_home:
                if (mType == 1) {
                    return;
                }
                mType = 1;
                toggleType();
                mvpPresenter.getList(mSeasonId, mType);
                break;
            case R.id.ll_away:
                if (mType == 2) {
                    return;
                }
                mType = 2;
                toggleType();
                mvpPresenter.getList(mSeasonId, mType);
                break;
        }
    }

    private void toggleType() {
        if (mType == 0) {
            tv_all.setTextColor(getResources().getColor(R.color.c_E3AC72));
            line_all.setVisibility(View.VISIBLE);
            tv_home.setTextColor(getResources().getColor(R.color.c_666666));
            line_home.setVisibility(View.GONE);
            tv_away.setTextColor(getResources().getColor(R.color.c_666666));
            line_away.setVisibility(View.GONE);
        }else if (mType == 1) {
            tv_all.setTextColor(getResources().getColor(R.color.c_666666));
            line_all.setVisibility(View.GONE);
            tv_home.setTextColor(getResources().getColor(R.color.c_E3AC72));
            line_home.setVisibility(View.VISIBLE);
            tv_away.setTextColor(getResources().getColor(R.color.c_666666));
            line_away.setVisibility(View.GONE);
        }else {
            tv_all.setTextColor(getResources().getColor(R.color.c_666666));
            line_all.setVisibility(View.GONE);
            tv_home.setTextColor(getResources().getColor(R.color.c_666666));
            line_home.setVisibility(View.GONE);
            tv_away.setTextColor(getResources().getColor(R.color.c_E3AC72));
            line_away.setVisibility(View.VISIBLE);
        }
    }
}
