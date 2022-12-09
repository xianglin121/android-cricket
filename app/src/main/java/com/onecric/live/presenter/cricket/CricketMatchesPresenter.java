package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketMatchesView;

import java.util.List;
import java.util.TimeZone;

public class CricketMatchesPresenter extends BasePresenter<CricketMatchesView> {
    public CricketMatchesPresenter(CricketMatchesView view) {
        attachView(view);
    }

    public void getMatchList(boolean isRefresh, int tournamentId, int page) {
        JSONObject jsonObject = new JSONObject();
        TimeZone timeZone = TimeZone.getDefault();
        jsonObject.put("timezone", timeZone.getID());
        jsonObject.put("tournament_id", tournamentId);
        jsonObject.put("page", page);
        addSubscription(apiStores.getCricketTournamentMatchList(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketMatchBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CricketMatchBean.class);
                mvpView.getDataSuccess(isRefresh, list);
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
