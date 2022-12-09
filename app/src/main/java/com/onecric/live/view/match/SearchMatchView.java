package com.onecric.live.view.match;

import com.onecric.live.model.Channel;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SearchMatchView extends BaseView<List<MatchListBean>> {
    void getHotMatchClassifySuccess(List<Channel> list);
}
