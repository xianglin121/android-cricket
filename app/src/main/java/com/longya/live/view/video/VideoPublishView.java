package com.longya.live.view.video;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface VideoPublishView extends BaseView<JsonBean> {
    void getTokenSuccess(String token);
}
