package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.BasketballMatchBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballMatchView;
import com.longya.live.view.match.FootballMatchView;

import java.util.List;

public class BasketballMatchPresenter extends BasePresenter<BasketballMatchView> {
    public BasketballMatchPresenter(BasketballMatchView view) {
        attachView(view);
    }

    public void getCollectCount() {
        addSubscription(apiStores.getBasketballList(CommonAppConfig.getInstance().getToken(), 4, "", 1, null),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getCollectCountSuccess(JSONObject.parseObject(data).getIntValue("total"));
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
