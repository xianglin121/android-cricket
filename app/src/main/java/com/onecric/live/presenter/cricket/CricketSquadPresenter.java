package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketSquadBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketSquadView;

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
