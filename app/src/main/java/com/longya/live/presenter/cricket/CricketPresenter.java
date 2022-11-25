package com.longya.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.CricketMatchBean;
import com.longya.live.model.CricketTournamentBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.cricket.CricketView;

import java.util.List;
import java.util.TimeZone;

public class CricketPresenter extends BasePresenter<CricketView> {
    public CricketPresenter(CricketView view) {
        attachView(view);
    }

    public void getTournamentList() {
        addSubscription(apiStores.getTournamentList(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketTournamentBean> list = JSONObject.parseArray(data, CricketTournamentBean.class);
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

    public void getCricketMatchList(boolean isRefresh, String timeType, String tournamentId, int streaming, int page) {
        JSONObject jsonObject = new JSONObject();
        TimeZone timeZone = TimeZone.getDefault();
        jsonObject.put("timezone", timeZone.getID());
        jsonObject.put("timeType", timeType);
        jsonObject.put("tournament_id", tournamentId);
        jsonObject.put("streaming", streaming);
        jsonObject.put("page", page);
        addSubscription(apiStores.getCricketMatchList(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketTournamentBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CricketTournamentBean.class);
                mvpView.getDataSuccess(isRefresh, JSONObject.parseObject(data).getIntValue("total"), list);
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
