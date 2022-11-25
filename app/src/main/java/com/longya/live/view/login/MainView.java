package com.longya.live.view.login;

import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

public interface MainView extends BaseView<JsonBean> {
    void getVisitorUserSigSuccess(String userId, String userSig);

    void getDataSuccess(UserBean model);
}
