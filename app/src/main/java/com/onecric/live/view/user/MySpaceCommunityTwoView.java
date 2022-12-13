package com.onecric.live.view.user;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MySpaceCommunityTwoView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<CommunityBean> list);
}
