package com.onecric.live.presenter.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.UserInfoActivity;
import com.onecric.live.event.UpdateUserInfoEvent;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.UserInfoView;

import org.greenrobot.eventbus.EventBus;

public class UserInfoPresenter extends BasePresenter<UserInfoView> {
    public UserInfoPresenter(UserInfoView view) {
        attachView(view);
    }

    public void getUserInfo() {
        addSubscription(apiStores.getUserInfo(CommonAppConfig.getInstance().getToken()),
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

    public void getToken() {
        addSubscription(apiStores.getQiniuToken(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        String token = JSONObject.parseObject(data).getString("token");
                        mvpView.getTokenSuccess(token);
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

    public void updateAvatar(String avatar) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("avatar", avatar);
        addSubscription(apiStores.updateUserInfo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((UserInfoActivity)mvpView).getString(R.string.tip_modify_avatar_success);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
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

    public void updateNickname(String nickname) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_nickname", nickname);
        addSubscription(apiStores.updateUserInfo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((UserInfoActivity)mvpView).getString(R.string.tip_modify_name_success);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
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

    public void updateIntroduction(String introduction) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("signature", introduction);
        addSubscription(apiStores.updateUserInfo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((UserInfoActivity)mvpView).getString(R.string.tip_modify_profile_success);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
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

    public void updatePhone(String phone, String code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("code", code);
        addSubscription(apiStores.updateUserInfo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((UserInfoActivity)mvpView).getString(R.string.tip_modify_phone_success);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
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
