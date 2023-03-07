package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketStatsListBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketStatsView;

import java.util.List;

public class CricketStatsPresenter extends BasePresenter<CricketStatsView> {
    public CricketStatsPresenter(CricketStatsView view) {
        attachView(view);
    }

    public void getList(int id, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("type", type);
        addSubscription(apiStores.getTournamentStatsList(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketStatsListBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), CricketStatsListBean.class);
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
