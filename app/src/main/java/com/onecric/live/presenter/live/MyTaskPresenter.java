package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.MyTaskView;


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
