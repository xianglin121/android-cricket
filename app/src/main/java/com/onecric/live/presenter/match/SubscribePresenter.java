package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.presenter.BasePresenter;

import io.reactivex.observers.DisposableObserver;

public class SubscribePresenter extends BasePresenter {

    public void doSubscribe(String mid, DisposableObserver observer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", CommonAppConfig.getInstance().getToken());
        jsonObject.put("mid", mid);
        addSubscription(apiStores.doSubscribe(getRequestBody(jsonObject)), observer);
    }
}
