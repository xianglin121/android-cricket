package com.onecric.live.view.match;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAnchorBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballMatchLiveView extends BaseView<JsonBean> {
    void getDataSuccess(List<LiveAnchorBean> list);

    void getDataSuccess(boolean isRefresh, List<LiveBean> list);
}
