package com.onecric.live.view.video;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.VideoDetailBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface VideoDetailView extends BaseView<JsonBean> {
    void getDataSuccess(List<ViewMoreBean> list , VideoDetailBean bean);
    void getVideoSuccess(List<ViewMoreBean> list);
}
