package com.longya.live.view.match;

import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataScheduleBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface BasketballDataScheduleView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<DataScheduleBean> list);
}
