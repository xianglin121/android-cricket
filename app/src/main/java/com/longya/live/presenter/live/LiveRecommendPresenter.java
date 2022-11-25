package com.longya.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.BannerBean;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.LiveMatchBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveClassifyView;
import com.longya.live.view.live.LiveRecommendView;

import java.util.ArrayList;
import java.util.List;


public class LiveRecommendPresenter extends BasePresenter<LiveRecommendView> {
    public LiveRecommendPresenter(LiveRecommendView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int type, int page) {
        addSubscription(apiStores.getLivingList(CommonAppConfig.getInstance().getToken(), page, type, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveBean.class);
                            mvpView.getDataSuccess(isRefresh, list);
                        }else {
                            mvpView.getDataSuccess(isRefresh, new ArrayList<>());
                        }
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

    public void getAllList() {
        addSubscription(apiStores.getAllLivingList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveBean> listOne = JSONObject.parseArray(JSONObject.parseObject(data).getString("nolive_broadcast"), LiveBean.class);
                            List<LiveBean> listTwo = JSONObject.parseArray(JSONObject.parseObject(data).getString("live_commentary"), LiveBean.class);
                            List<LiveBean> listThree = JSONObject.parseArray(JSONObject.parseObject(data).getString("robot"), LiveBean.class);
                            mvpView.getDataSuccess(listOne, listTwo, listThree);
                        }else {
                            mvpView.getDataSuccess(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                        }
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

    public void getRecommendList() {
        addSubscription(apiStores.getAllMatch(),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveMatchBean> liveMatchBeans = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveMatchBean.class);
                            mvpView.getDataSuccess(liveMatchBeans);
                        }
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

    public void getBannerList() {
        addSubscription(apiStores.getBannerList(1),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<BannerBean> list = JSONObject.parseArray(data, BannerBean.class);
                            mvpView.getBannerSuccess(list);
                        }
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
