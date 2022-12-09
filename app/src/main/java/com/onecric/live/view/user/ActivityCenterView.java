package com.onecric.live.view.user;

import com.onecric.live.model.ActivityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface ActivityCenterView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ActivityBean> list);
}
