package com.longya.live.view.live;

import com.longya.live.model.Channel;
import com.longya.live.model.MatchListBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchLiveView extends BaseView<List<MatchListBean>> {
    void getHotMatchClassifySuccess(List<Channel> list);
}
