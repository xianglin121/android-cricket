package com.onecric.live.view.match;

import com.onecric.live.model.DataScheduleBean;
import com.onecric.live.model.DataStageBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballDataScheduleView extends BaseView<JsonBean> {
    void getStageDataSuccess(List<DataStageBean> list);

    void getDataSuccess(List<DataScheduleBean> list);
}
