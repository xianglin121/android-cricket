package com.longya.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.MessageBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.user.MyLevelView;
import com.longya.live.view.user.MyMessageView;

import java.util.List;

public class MyMessagePresenter extends BasePresenter<MyMessageView> {
    public MyMessagePresenter(MyMessageView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page) {
        addSubscription(apiStores.getMessageList(CommonAppConfig.getInstance().getToken(), page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<MessageBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MessageBean.class);
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
