package com.onecric.live.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.BasketballDataRankingAdapter;
import com.onecric.live.model.DataRankingBean;
import com.onecric.live.presenter.match.BasketballDataRankingPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.BasketballDataRankingView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataRankingFragment extends MvpFragment<BasketballDataRankingPresenter> implements BasketballDataRankingView {
    public static BasketballDataRankingFragment newInstance() {
        BasketballDataRankingFragment fragment = new BasketballDataRankingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_ranking;
    private BasketballDataRankingAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_data_ranking;
    }

    @Override
    protected BasketballDataRankingPresenter createPresenter() {
        return new BasketballDataRankingPresenter(this);
    }

    @Override
    protected void initUI() {
        rv_ranking = findViewById(R.id.rv_ranking);
    }

    @Override
    protected void initData() {

    }

    public void getList(int id) {
        mvpPresenter.getList(id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<DataRankingBean> list) {
        if (list != null) {
            mAdapter = new BasketballDataRankingAdapter(R.layout.item_basketball_data_ranking, list);
            rv_ranking.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_ranking.setAdapter(mAdapter);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
