package com.onecric.live.view.video;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface VideoPublishView extends BaseView<JsonBean> {
    void getTokenSuccess(String token);
}
