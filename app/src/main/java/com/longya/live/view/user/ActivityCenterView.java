package com.longya.live.view.user;

import com.longya.live.model.ActivityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MessageBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ActivityCenterView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ActivityBean> list);
}
