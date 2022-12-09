package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketFantasyBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.TeamComparisonView;

public class TeamComparisonPresenter extends BasePresenter<TeamComparisonView> {
    public TeamComparisonPresenter(TeamComparisonView view) {
        attachView(view);
    }

    public void getData(int matchId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        addSubscription(apiStores.getCricketDetailFantasyInfo(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getDataSuccess(JSONObject.parseObject(data, CricketFantasyBean.class));
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
