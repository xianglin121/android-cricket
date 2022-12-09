package com.onecric.live.view.live;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SearchCommunityView extends BaseView<JsonBean> {
    void getList(boolean isRefresh, List<CommunityBean> list);
}
