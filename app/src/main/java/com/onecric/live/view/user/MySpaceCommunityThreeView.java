package com.onecric.live.view.user;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MySpaceCommunityThreeView extends BaseView<JsonBean> {
    void getList(boolean isRefresh, List<CommunityBean> list);
}
