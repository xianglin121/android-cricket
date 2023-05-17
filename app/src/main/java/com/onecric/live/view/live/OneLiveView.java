package com.onecric.live.view.live;

import com.onecric.live.model.BannerBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.model.OneHistoryLiveBean;
import com.onecric.live.model.PlayCardsBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface OneLiveView extends BaseView<JsonBean> {
    void getAllDataSuccess(List<LiveAuthorBean> aList);

    void getDataSuccess(List<LiveMatchBean> list);

    void getBannerSuccess(List<BannerBean> list);

    void getMatchSuccess(List<PlayCardsBean> today);
    void getUpcomingSuccess(List<LiveMatchListBean.MatchItemBean> upcoming);
    void getDataHistorySuccess(List<OneHistoryLiveBean> list);
}
