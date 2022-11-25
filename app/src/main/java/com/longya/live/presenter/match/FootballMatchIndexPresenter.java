package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.BasketballIndexBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.FootballMatchIndexView;
import com.longya.live.view.match.FootballMatchStatusView;

public class FootballMatchIndexPresenter extends BasePresenter<FootballMatchIndexView> {
    public FootballMatchIndexPresenter(FootballMatchIndexView view) {
        attachView(view);
    }

    public void getDetail(int id) {
        addSubscription(apiStores.getFootballIndexDetail(id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        BasketballIndexBean basketballIndexBean = JSONObject.parseObject(data, BasketballIndexBean.class);
                        mvpView.getDataSuccess(basketballIndexBean);
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
