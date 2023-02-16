package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SubscribePresenter extends BasePresenter {

    public void doSubscribe(String mid, int start, int out, int wickets, int miles, int delay, int result, DisposableObserver observer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", CommonAppConfig.getInstance().getToken());
        jsonObject.put("mid", mid);
        jsonObject.put("start", start);
        jsonObject.put("out", out);
        jsonObject.put("wickets", wickets);
        jsonObject.put("miles", miles);
        jsonObject.put("delay", delay);
        jsonObject.put("result", result);
        ApiClient.retrofit().create(ApiStores.class)
                .doSubscribe(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
