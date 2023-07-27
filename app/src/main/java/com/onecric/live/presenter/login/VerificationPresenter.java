package com.onecric.live.presenter.login;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.UserBean;
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

    public void oneLoginByPwd(String mobile,String pushid,String code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", mobile);
//        jsonObject.put("password", password);
        jsonObject.put("pushid", pushid);
        jsonObject.put("device_type", "android");
//        jsonObject.put("code", getFlavor(mContext));
        jsonObject.put("code", code);
        addSubscription(apiStores.oneLoginByPwd(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(JSON.parseObject(data).getString("id")) &&
                                !TextUtils.isEmpty(JSON.parseObject(data).getString("token"))){
                            CommonAppConfig.getInstance().saveLoginInfo(JSON.parseObject(data).getString("id"),
                                    JSON.parseObject(data).getString("token"), JSONObject.parseObject(data, UserBean.class).getUserSig(), data);
                        }

                        mvpView.verifySuccess();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.verifyFail(msg);
                    }

                    @Override
                    public void onError(String msg) {mvpView.verifyFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void updateJgId(String id) {
        addSubscription(apiStores.updateJgId(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
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
