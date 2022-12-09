package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.DataBestRightBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.BasketballDataBestView;

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
