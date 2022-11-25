package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface MyTaskView extends BaseView<JsonBean> {
    void getDataSuccess(String url, String img);
}
