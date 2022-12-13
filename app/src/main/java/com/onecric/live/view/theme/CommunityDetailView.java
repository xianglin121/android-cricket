package com.onecric.live.view.theme;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface CommunityDetailView extends BaseView<ThemeClassifyBean> {
    void getDataSuccess(boolean isRefresh, List<CommunityBean> list);
}
