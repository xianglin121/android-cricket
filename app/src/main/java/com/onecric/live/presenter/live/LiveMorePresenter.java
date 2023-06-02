package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveMoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class LiveMorePresenter extends BasePresenter<LiveMoreView> {
    public LiveMorePresenter(LiveMoreView view) {
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

    public void getHistoryList(boolean isRefresh, int page) {
        addSubscription(apiStores.getHistoryLiveList(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), page,10),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<HistoryLiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), HistoryLiveBean.class);
                            mvpView.getDataHistorySuccess(isRefresh, list);
                        }else {
                            mvpView.getDataHistorySuccess(isRefresh, new ArrayList<>());
                        }
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

    public void getMatchVideoList(String name) {
        addSubscription(apiStores.getTournament(TimeZone.getDefault().getID(),name),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveVideoBean.LBean> list = JSONObject.parseArray(data, LiveVideoBean.LBean.class);
                            mvpView.getTournamentSuccess(list);
                        }else {
                            mvpView.getTournamentSuccess(new ArrayList<>());
                        }
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

    public void getMoreVideoList(boolean isRefresh, String name, int page) {
        addSubscription(apiStores.getTournamentVideoList(TimeZone.getDefault().getID(),name,page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
                            List<ViewMoreBean> list = null;
                            list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ViewMoreBean.class);
                            mvpView.getVideoSuccess(isRefresh,list);
                        }else {
                            mvpView.getVideoSuccess(isRefresh,new ArrayList<>());
                        }
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
}
