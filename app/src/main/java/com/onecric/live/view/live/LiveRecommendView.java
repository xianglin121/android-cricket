package com.onecric.live.view.live;

import com.onecric.live.model.BannerBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveRecommendView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveBean> list);

    void getDataSuccess(List<LiveMatchBean> list);

    void getDataSuccess(List<LiveBean> freeList, List<LiveBean> todayList, List<LiveBean> historyList);

    void doReserveSuccess(int position);

    void getBannerSuccess(List<BannerBean> list);
}
