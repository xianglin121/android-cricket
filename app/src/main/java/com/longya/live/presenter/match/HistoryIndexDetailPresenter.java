package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.HistoryIndexDetailBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.HistoryIndexDetailView;
import com.longya.live.view.match.RangqiuIndexDetailView;

public class HistoryIndexDetailPresenter extends BasePresenter<HistoryIndexDetailView> {
    public HistoryIndexDetailPresenter(HistoryIndexDetailView view) {
        attachView(view);
    }

    public void getDetail(int id, int companyId) {
        addSubscription(apiStores.getBasketballHistoryIndexDetail(CommonAppConfig.getInstance().getToken(), id, companyId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseObject(data, HistoryIndexDetailBean.class));
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
