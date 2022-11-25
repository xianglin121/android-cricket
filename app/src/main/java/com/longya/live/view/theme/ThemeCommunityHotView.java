package com.longya.live.view.theme;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ThemeCommunityHotView extends BaseView<JsonBean> {
    void getDataSuccess(List<ThemeClassifyBean> classifyList, CommunityBean refining);

    void getList(boolean isRefresh, List<CommunityBean> list);
}
