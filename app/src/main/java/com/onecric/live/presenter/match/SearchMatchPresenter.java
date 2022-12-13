package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.Channel;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.SearchMatchView;

import java.util.List;

public class SearchMatchPresenter extends BasePresenter<SearchMatchView> {
    public SearchMatchPresenter(SearchMatchView view) {
        attachView(view);
    }

    public void searchMatch(String content) {
        addSubscription(apiStores.searchMatch(content),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<MatchListBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MatchListBean.class);
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