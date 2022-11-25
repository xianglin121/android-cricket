package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.event.UpdateBasketballCollectCountEvent;
import com.longya.live.event.UpdateFootballCollectCountEvent;
import com.longya.live.model.BasketballMatchBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballMatchInnerView;
import com.longya.live.view.match.FootballMatchInnerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class BasketballMatchInnerPresenter extends BasePresenter<BasketballMatchInnerView> {
    public BasketballMatchInnerPresenter(BasketballMatchInnerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int type, int page, String id) {
        addSubscription(apiStores.getBasketballList(CommonAppConfig.getInstance().getToken(), type, "", page, type==4?null:id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<BasketballMatchBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), BasketballMatchBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
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

    public void doCollect(int type, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("id", id);
        addSubscription(apiStores.doMatchCollect(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        EventBus.getDefault().post(new UpdateBasketballCollectCountEvent());
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
