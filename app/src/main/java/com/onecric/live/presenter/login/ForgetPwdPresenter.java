package com.onecric.live.presenter.login;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.login.ForgetPwdView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {
    public ForgetPwdPresenter(ForgetPwdView view) {
        attachView(view);
    }

    public void getCode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", 2);
        apiStores.getCode(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(null);
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

    public void changePwd(String phone, String code, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("code", code);
        jsonObject.put("password", password);
        apiStores.changePwd(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.forgetPwdSuccess(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.forgetPwdFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.forgetPwdFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
