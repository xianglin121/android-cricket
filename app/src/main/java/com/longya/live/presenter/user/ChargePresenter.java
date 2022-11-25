package com.longya.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.CoinBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.HotMatchView;
import com.longya.live.view.user.ChargeView;

public class ChargePresenter extends BasePresenter<ChargeView> {
    public ChargePresenter(ChargeView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getCoinList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseArray(data, CoinBean.class));
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

    public void doPay(int id, int type) {
        addSubscription(apiStores.getPayUrl(CommonAppConfig.getInstance().getToken(), id, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.paySuccess(JSONObject.parseObject(data).getString("url"));
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.payFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.payFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
