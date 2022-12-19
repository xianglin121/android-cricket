package com.onecric.live.view.login;

import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

public interface MainView extends BaseView<JsonBean> {
    void getVisitorUserSigSuccess(String userId, String userSig);

    void getDataSuccess(UserBean model);

    void getConfigSuccess(ConfigurationBean bean);
}
