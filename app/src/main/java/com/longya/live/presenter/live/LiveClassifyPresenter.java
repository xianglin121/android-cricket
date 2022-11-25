package com.longya.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.LiveClassifyBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveClassifyView;
import com.longya.live.view.live.LiveView;

import java.util.List;


public class LiveClassifyPresenter extends BasePresenter<LiveClassifyView> {
    public LiveClassifyPresenter(LiveClassifyView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int type, String date, int page) {
        addSubscription(apiStores.getLiveMatchList(CommonAppConfig.getInstance().getToken(), page, date, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<LiveClassifyBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveClassifyBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
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

    public void doReserve(int position, int matchId, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        jsonObject.put("type", type);
        addSubscription(apiStores.reserveMatchTwo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doReserveSuccess(position);
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
