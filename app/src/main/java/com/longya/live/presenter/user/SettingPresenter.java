package com.longya.live.presenter.user;

import android.content.Context;

import com.longya.live.CommonAppConfig;
import com.longya.live.activity.MainActivity;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.user.SettingView;
import com.longya.live.view.user.UserView;

public class SettingPresenter extends BasePresenter<SettingView> {
    public SettingPresenter(SettingView view) {
        attachView(view);
    }

    public void signOut(Context context) {
        addSubscription(apiStores.signOut(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        CommonAppConfig.getInstance().clearLoginInfo();
                        MainActivity.loginForward(context);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
