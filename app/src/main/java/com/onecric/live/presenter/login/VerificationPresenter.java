package com.onecric.live.presenter.login;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.BaseView;

public class VerificationPresenter extends BasePresenter<BaseView<JsonBean>> {
    public VerificationPresenter(BaseView view) {
        attachView(view);
    }

    public void getCode(String phone,int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", type);
        addSubscription(apiStores.getCode(getRequestBody(jsonObject)),
                new ApiCallback() {
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

}
