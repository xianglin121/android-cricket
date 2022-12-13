package com.onecric.live.view.match;

import com.onecric.live.model.FootballMatchBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballMatchScheduleView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<FootballMatchBean> list);
}
