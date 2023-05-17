package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.LiveClassifyBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveClassifyView;

import java.util.List;
import java.util.TimeZone;


public class LiveClassifyPresenter extends BasePresenter<LiveClassifyView> {
    public LiveClassifyPresenter(LiveClassifyView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int type, String date, int page) {
        addSubscription(apiStores.getLiveMatchList(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), page, date, type),
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
