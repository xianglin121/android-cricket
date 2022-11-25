package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.FootballDetailBean;
import com.longya.live.model.FootballLineupBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.FootballMatchLineupView;
import com.longya.live.view.match.FootballMatchLiveView;

public class FootballMatchLineupPresenter extends BasePresenter<FootballMatchLineupView> {
    public FootballMatchLineupPresenter(FootballMatchLineupView view) {
        attachView(view);
    }

    public void getLineup(int id) {
        addSubscription(apiStores.getFootballMatchLineup(id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseObject(data, FootballLineupBean.class));
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
