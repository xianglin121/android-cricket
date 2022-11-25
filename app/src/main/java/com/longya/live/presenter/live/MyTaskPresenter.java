package com.longya.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.TaskBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveView;
import com.longya.live.view.live.MyTaskView;

import java.util.List;


public class MyTaskPresenter extends BasePresenter<MyTaskView> {
    public MyTaskPresenter(MyTaskView view) {
        attachView(view);
    }

    public void getQrCode() {
        addSubscription(apiStores.getInviteQrCode(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseObject(data).getString("url"), JSONObject.parseObject(data).getString("img"));
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
