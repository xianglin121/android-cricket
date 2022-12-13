package com.onecric.live.view.user;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MySpaceVideoOneView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);
}
