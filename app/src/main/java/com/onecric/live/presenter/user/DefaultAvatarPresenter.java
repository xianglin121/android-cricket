package com.onecric.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.DefaultAvatarActivity;
import com.onecric.live.event.UpdateUserInfoEvent;
import com.onecric.live.model.AvatarBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.DefaultAvatarView;

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
