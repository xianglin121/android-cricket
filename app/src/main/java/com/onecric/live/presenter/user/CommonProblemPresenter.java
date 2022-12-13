package com.onecric.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ProblemBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.CommonProblemView;

public class CommonProblemPresenter extends BasePresenter<CommonProblemView> {
    public CommonProblemPresenter(CommonProblemView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getCommonProblemList(CommonAppConfig.getInstance().getToken(), 2),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseArray(data, ProblemBean.class));
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
