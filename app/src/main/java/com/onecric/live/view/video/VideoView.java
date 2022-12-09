package com.onecric.live.view.video;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface VideoView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);
}
