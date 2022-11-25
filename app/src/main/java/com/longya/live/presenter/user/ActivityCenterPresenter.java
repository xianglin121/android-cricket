package com.longya.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.ActivityBean;
import com.longya.live.model.MessageBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.user.ActivityCenterView;
import com.longya.live.view.user.MyMessageView;

import java.util.List;

public class ActivityCenterPresenter extends BasePresenter<ActivityCenterView> {
    public ActivityCenterPresenter(ActivityCenterView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page) {
        addSubscription(apiStores.getActivityList(CommonAppConfig.getInstance().getToken(), page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ActivityBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ActivityBean.class);
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
