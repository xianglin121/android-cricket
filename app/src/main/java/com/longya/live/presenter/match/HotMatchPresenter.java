package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.Channel;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.HotMatchView;

import java.util.List;

public class HotMatchPresenter extends BasePresenter<HotMatchView> {
    public HotMatchPresenter(HotMatchView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getHotMatchClassify(),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<Channel> hotMatch = JSONObject.parseArray(JSONObject.parseObject(data).getString("hot_match"), Channel.class);
                        List<Channel> football = JSONObject.parseArray(JSONObject.parseObject(data).getString("football"), Channel.class);
                        List<Channel> basketball = JSONObject.parseArray(JSONObject.parseObject(data).getString("basketball"), Channel.class);
                        mvpView.getDataSuccess(hotMatch, football, basketball);
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
