package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.MatchDataClassifyBean;
import com.longya.live.model.MatchDataFirstBean;
import com.longya.live.model.MatchDataFollowBean;
import com.longya.live.model.MatchDataSecondBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.MatchDataView;
import com.longya.live.view.match.MatchFilterView;

import java.util.List;

public class MatchDataPresenter extends BasePresenter<MatchDataView> {
    public MatchDataPresenter(MatchDataView view) {
        attachView(view);
    }

    public void getFollowList() {
        addSubscription(apiStores.getDataDefaultFollowList(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<MatchDataFollowBean> attention = JSONObject.parseArray(JSONObject.parseObject(data).getString("attention"), MatchDataFollowBean.class);
                mvpView.getFollowListSuccess(attention);
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

    public void getClassifyList() {
        addSubscription(apiStores.getMatchDataClassify(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<MatchDataClassifyBean> list = JSONObject.parseArray(data, MatchDataClassifyBean.class);
                mvpView.getClassifyListSuccess(list);
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

    public void getCountryList(int id) {
        addSubscription(apiStores.getMatchDataCountry(id), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<MatchDataFirstBean> list = JSONObject.parseArray(data, MatchDataFirstBean.class);
                mvpView.getCountryListSuccess(list);
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

    public void getBasketBallList(int position, int countryId, int classifyId) {
        addSubscription(apiStores.getBasketBallMatchDataList(countryId, classifyId), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<MatchDataSecondBean> list = JSONObject.parseArray(data, MatchDataSecondBean.class);
                mvpView.getListSuccess(position, list);
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

    public void getFootBallList(int position, int countryId, int classifyId) {
        addSubscription(apiStores.getFootBallMatchDataList(countryId, classifyId), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<MatchDataSecondBean> list = JSONObject.parseArray(data, MatchDataSecondBean.class);
                mvpView.getListSuccess(position, list);
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
