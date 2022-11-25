package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataMatchTypeBean;
import com.longya.live.model.DataRankingBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballDataRankingView;
import com.longya.live.view.match.MatchView;

import java.util.List;

public class BasketballDataRankingPresenter extends BasePresenter<BasketballDataRankingView> {
    public BasketballDataRankingPresenter(BasketballDataRankingView view) {
        attachView(view);
    }

    public void getList(int seasonId) {
        addSubscription(apiStores.getBasketballMatchDataRanking(seasonId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataRankingBean> list = JSONObject.parseArray(data, DataRankingBean.class);
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
