package com.longya.live.view.match;

import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveAnchorBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.LiveUserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface FootballMatchLiveView extends BaseView<JsonBean> {
    void getDataSuccess(List<LiveAnchorBean> list);

    void getDataSuccess(boolean isRefresh, List<LiveBean> list);
}
