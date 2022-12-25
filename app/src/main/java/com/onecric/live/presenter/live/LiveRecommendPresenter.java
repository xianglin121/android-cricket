package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveRecommendView;

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
                            int lastPage = JSONObject.parseObject(JSONObject.parseObject(data).getString("last_page"), Integer.class);
                            mvpView.getDataSuccess(isRefresh, list,type);
                            //fixme 分页判断,未测试
                            if(type==-1 && lastPage>page){
                                getList(false, -1, page+1);
                            }
                        }else {
                            mvpView.getDataSuccess(isRefresh, new ArrayList<>(),type);
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
