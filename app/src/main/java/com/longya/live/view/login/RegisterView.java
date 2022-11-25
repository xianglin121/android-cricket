package com.longya.live.view.login;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface RegisterView extends BaseView<JsonBean> {
    void registerSuccess(String msg);

    void registerFail(String msg);
}
