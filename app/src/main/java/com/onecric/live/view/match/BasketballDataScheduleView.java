package com.onecric.live.view.match;

import com.onecric.live.model.DataScheduleBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface BasketballDataScheduleView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<DataScheduleBean> list);
}
