package com.onecric.live.view.login;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface VerificationView extends BaseView<JsonBean> {
    void sendSuccess();

    void sendFail(String msg);

    void verifySuccess();

    void verifyFail(String msg);
}
