package com.longya.live.view.video;

import com.longya.live.model.JsonBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface VideoView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);
}
