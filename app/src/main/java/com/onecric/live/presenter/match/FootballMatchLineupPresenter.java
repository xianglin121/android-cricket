package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.FootballLineupBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.FootballMatchLineupView;

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
