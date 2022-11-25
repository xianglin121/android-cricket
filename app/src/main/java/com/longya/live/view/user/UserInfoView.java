package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

public interface UserInfoView extends BaseView<UserBean> {
    void getTokenSuccess(String token);
}
