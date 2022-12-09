package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveMatchView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveBean> list);
}
