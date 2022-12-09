package com.onecric.live.view.theme;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface CommunityPublishView extends BaseView<JsonBean> {
    void getTokenSuccess(String token);
}
