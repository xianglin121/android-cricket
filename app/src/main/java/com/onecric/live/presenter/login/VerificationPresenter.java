package com.onecric.live.presenter.login;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.login.VerificationView;

public class VerificationPresenter extends BasePresenter<VerificationView> {
    public VerificationPresenter(VerificationView view) {
        attachView(view);
    }

    public void getCode(String phone,int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", type);
        addSubscription(apiStores.oneSendCode(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.sendSuccess();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.sendFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.sendFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void verifyCode(String phone,String code,int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("code", code);
        jsonObject.put("type", type);
        addSubscription(apiStores.oneVerifyCode(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.verifySuccess();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.verifyFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.verifyFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

}
