package com.longya.live.view.live;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchCommunityView extends BaseView<JsonBean> {
    void getList(boolean isRefresh, List<CommunityBean> list);
}
