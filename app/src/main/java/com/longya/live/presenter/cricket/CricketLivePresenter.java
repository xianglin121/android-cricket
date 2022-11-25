package com.longya.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.CricketTournamentBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.cricket.CricketLiveView;
import com.longya.live.view.cricket.CricketStatsView;

import java.util.List;
import java.util.TimeZone;

public class CricketLivePresenter extends BasePresenter<CricketLiveView> {
    public CricketLivePresenter(CricketLiveView view) {
        attachView(view);
    }

    public void getList(int matchId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        addSubscription(apiStores.getCricketDetailLive(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<String> list = JSONObject.parseArray(data, String.class);
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
