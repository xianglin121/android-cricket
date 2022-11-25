package com.longya.live.view.match;

import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface FootballMatchScheduleView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<FootballMatchBean> list);
}
