package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface LiveMatchView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveBean> list);
}
