package com.longya.live.view.theme;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface CommunityPublishView extends BaseView<JsonBean> {
    void getTokenSuccess(String token);
}
