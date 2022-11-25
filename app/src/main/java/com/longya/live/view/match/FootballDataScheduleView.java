package com.longya.live.view.match;

import com.longya.live.model.DataScheduleBean;
import com.longya.live.model.DataStageBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface FootballDataScheduleView extends BaseView<JsonBean> {
    void getStageDataSuccess(List<DataStageBean> list);

    void getDataSuccess(List<DataScheduleBean> list);
}
