package com.onecric.live.view.user;

import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

public interface UserInfoView extends BaseView<UserBean> {
    void getTokenSuccess(String token);
}
