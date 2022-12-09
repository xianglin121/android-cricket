package com.onecric.live.view.login;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface LoginView extends BaseView<JsonBean> {
    void loginIsSuccess(boolean isSuccess);
    void showCountryList();
}
