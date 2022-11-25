package com.longya.live.view.user;

import com.longya.live.model.AccountBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface BindAccountView extends BaseView<JsonBean> {
    void bindAccountSuccess();
}
