package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface MyTaskView extends BaseView<JsonBean> {
    void getDataSuccess(String url, String img);
}
