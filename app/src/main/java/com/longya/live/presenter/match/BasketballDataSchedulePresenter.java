package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataScheduleBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballDataScheduleView;
import com.longya.live.view.match.BasketballMatchDataView;

import java.util.List;

public class BasketballDataSchedulePresenter extends BasePresenter<BasketballDataScheduleView> {
    public BasketballDataSchedulePresenter(BasketballDataScheduleView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int id, int page, int seasonId, int typeId) {
        addSubscription(apiStores.getBasketballMatchDataDetail(id, page, seasonId, typeId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataScheduleBean> list = JSONObject.parseArray(JSONObject.parseObject(JSONObject.parseObject(data).getString("list")).getString("data"), DataScheduleBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
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
