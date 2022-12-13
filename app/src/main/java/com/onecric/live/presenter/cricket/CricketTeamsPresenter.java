package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketPlayerBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketTeamsView;

public class CricketTeamsPresenter extends BasePresenter<CricketTeamsView> {
    public CricketTeamsPresenter(CricketTeamsView view) {
        attachView(view);
    }

    public void getList(int teamId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("competitor_id", teamId);
        addSubscription(apiStores.getTournamentTeamPlayerList(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                JSONObject dataJson = JSONObject.parseObject(data);
                JSONObject batterJson = JSONObject.parseObject(dataJson.getString("batters"));
                JSONObject bowlerJson = JSONObject.parseObject(dataJson.getString("bowlers"));
                JSONObject allRounderJson = JSONObject.parseObject(dataJson.getString("all_rounder"));
                mvpView.getDataSuccess(JSONObject.parseArray(batterJson.getString("players"), CricketPlayerBean.class), batterJson.getString("count"),
                        JSONObject.parseArray(bowlerJson.getString("players"), CricketPlayerBean.class), bowlerJson.getString("count"),
                        JSONObject.parseArray(allRounderJson.getString("players"), CricketPlayerBean.class), allRounderJson.getString("count"));
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
