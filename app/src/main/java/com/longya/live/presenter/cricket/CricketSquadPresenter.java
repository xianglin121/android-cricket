package com.longya.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.CricketSquadBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.cricket.CricketLiveView;
import com.longya.live.view.cricket.CricketSquadView;

import java.util.List;

public class CricketSquadPresenter extends BasePresenter<CricketSquadView> {
    public CricketSquadPresenter(CricketSquadView view) {
        attachView(view);
    }

    public void getList(int matchId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        addSubscription(apiStores.getCricketDetailSquad(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketSquadBean> list = JSONObject.parseArray(data, CricketSquadBean.class);
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
