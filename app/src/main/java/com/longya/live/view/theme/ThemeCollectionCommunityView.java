package com.longya.live.view.theme;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ThemeCollectionCommunityView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<CommunityBean> list);
}
