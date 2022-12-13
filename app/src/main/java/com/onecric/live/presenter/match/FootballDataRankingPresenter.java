package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.FootballDataRankingBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.FootballDataRankingView;

import java.util.List;

public class FootballDataRankingPresenter extends BasePresenter<FootballDataRankingView> {
    public FootballDataRankingPresenter(FootballDataRankingView view) {
        attachView(view);
    }

    public void getList(int seasonId, int type) {
        addSubscription(apiStores.getFootballMatchDataRanking(seasonId, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<FootballDataRankingBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("integral"), FootballDataRankingBean.class);
                        mvpView.getDataSuccess(list);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
