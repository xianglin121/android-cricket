package com.longya.live.view.theme;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ThemeCommunityFollowView extends BaseView<JsonBean> {
    void getList(boolean isRefresh, List<CommunityBean> list);
}
