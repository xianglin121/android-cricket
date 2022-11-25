package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MySpaceVideoOneView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);
}
