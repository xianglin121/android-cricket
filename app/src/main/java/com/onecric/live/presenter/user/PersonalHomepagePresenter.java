package com.onecric.live.presenter.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.PersonalHomepageView;

public class PersonalHomepagePresenter extends BasePresenter<PersonalHomepageView> {
    public PersonalHomepagePresenter(PersonalHomepageView view) {
        attachView(view);
    }

    public void getUserInfo(int id) {
        addSubscription(apiStores.getUserInfo(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            mvpView.getDataSuccess(JSONObject.parseObject(data, UserBean.class));
                        }
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
