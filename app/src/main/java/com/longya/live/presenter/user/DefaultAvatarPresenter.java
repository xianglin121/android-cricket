package com.longya.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.activity.DefaultAvatarActivity;
import com.longya.live.event.UpdateUserInfoEvent;
import com.longya.live.model.ActivityBean;
import com.longya.live.model.AvatarBean;
import com.longya.live.model.BasketballDetailBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.user.ActivityCenterView;
import com.longya.live.view.user.DefaultAvatarView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DefaultAvatarPresenter extends BasePresenter<DefaultAvatarView> {
    public DefaultAvatarPresenter(DefaultAvatarView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getDefaultAvatarList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<JSONObject> list = JSONObject.parseArray(data, JSONObject.class);
                        List<AvatarBean> avatarList = new ArrayList<>();
                        if (list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                AvatarBean avatarBean = new AvatarBean();
                                avatarBean.setAvatar(list.get(i).getString("url"));
                                avatarList.add(avatarBean);
                            }
                            mvpView.getDataSuccess(avatarList);
                        }
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

    public void updateAvatar(String avatar) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("avatar", avatar);
        addSubscription(apiStores.updateUserInfo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((DefaultAvatarActivity)mvpView).getString(R.string.tip_modify_avatar_success);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
                        ((DefaultAvatarActivity)mvpView).finish();
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
