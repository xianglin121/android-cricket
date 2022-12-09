package com.onecric.live.view.login;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface ForgetPwdView extends BaseView<JsonBean> {
    void forgetPwdSuccess(String msg);

    void forgetPwdFail(String msg);
}
