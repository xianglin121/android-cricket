package com.longya.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.Channel;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.SearchLiveView;

import java.util.List;

public class SearchLivePresenter extends BasePresenter<SearchLiveView> {
    public SearchLivePresenter(SearchLiveView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getHotMatchClassify(),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<Channel> hotMatch = JSONObject.parseArray(JSONObject.parseObject(data).getString("hot_match"), Channel.class);
                        mvpView.getHotMatchClassifySuccess(hotMatch);
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
