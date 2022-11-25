package com.longya.live.view.login;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface LoginView extends BaseView<JsonBean> {
    void loginIsSuccess(boolean isSuccess);
}
