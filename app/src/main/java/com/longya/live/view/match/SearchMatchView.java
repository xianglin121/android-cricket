package com.longya.live.view.match;

import com.longya.live.model.Channel;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchMatchView extends BaseView<List<MatchListBean>> {
    void getHotMatchClassifySuccess(List<Channel> list);
}
