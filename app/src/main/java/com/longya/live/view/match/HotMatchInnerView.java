package com.longya.live.view.match;

import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface HotMatchInnerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MatchListBean> list);
}
