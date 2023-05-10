package com.onecric.live.view.login;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface RegisterView extends BaseView<JsonBean> {
    void registerSuccess(String msg);

    void registerFail(String msg);

    void showCountryList();
}
