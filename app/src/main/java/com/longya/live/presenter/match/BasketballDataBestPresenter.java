package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.DataBestRightBean;
import com.longya.live.model.DataRankingBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballDataBestView;
import com.longya.live.view.match.BasketballDataRankingView;

import java.util.List;

public class BasketballDataBestPresenter extends BasePresenter<BasketballDataBestView> {
    public BasketballDataBestPresenter(BasketballDataBestView view) {
        attachView(view);
    }

    public void getTeamList(int seasonId, int type) {
        addSubscription(apiStores.getBasketballMatchDataBestTeam(seasonId, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataBestRightBean> list = JSONObject.parseArray(data, DataBestRightBean.class);
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

    public void getMemberList(int seasonId, int type) {
        addSubscription(apiStores.getBasketballMatchDataBestMember(seasonId, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataBestRightBean> list = JSONObject.parseArray(data, DataBestRightBean.class);
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
