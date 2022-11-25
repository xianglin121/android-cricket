package com.longya.live.view.match;

import com.longya.live.model.Channel;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface HotMatchView extends BaseView<JsonBean> {
    void getDataSuccess(List<Channel> hotMatch, List<Channel> football, List<Channel> basketball);
}
