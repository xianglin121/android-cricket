package com.onecric.live.view.theme;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface ThemeCommunityHotView extends BaseView<JsonBean> {
    void getDataSuccess(List<ThemeClassifyBean> classifyList, CommunityBean refining);

    void getList(boolean isRefresh, List<CommunityBean> list);
}
