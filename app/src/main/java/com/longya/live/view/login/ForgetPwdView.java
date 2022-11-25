package com.longya.live.view.login;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface ForgetPwdView extends BaseView<JsonBean> {
    void forgetPwdSuccess(String msg);

    void forgetPwdFail(String msg);
}
