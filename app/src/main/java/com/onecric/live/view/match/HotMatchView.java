package com.onecric.live.view.match;

import com.onecric.live.model.Channel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface HotMatchView extends BaseView<JsonBean> {
    void getDataSuccess(List<Channel> hotMatch, List<Channel> football, List<Channel> basketball);
}
