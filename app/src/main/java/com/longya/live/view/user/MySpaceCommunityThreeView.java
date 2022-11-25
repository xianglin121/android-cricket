package com.longya.live.view.user;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MySpaceCommunityThreeView extends BaseView<JsonBean> {
    void getList(boolean isRefresh, List<CommunityBean> list);
}
