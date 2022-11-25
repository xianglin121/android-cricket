package com.longya.live.view.live;

import com.longya.live.model.BannerBean;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.LiveMatchBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface LiveRecommendView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveBean> list);

    void getDataSuccess(List<LiveMatchBean> list);

    void getDataSuccess(List<LiveBean> freeList, List<LiveBean> todayList, List<LiveBean> historyList);

    void doReserveSuccess(int position);

    void getBannerSuccess(List<BannerBean> list);
}
