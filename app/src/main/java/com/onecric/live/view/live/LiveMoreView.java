package com.onecric.live.view.live;

import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveMoreView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveBean> list);
    void getDataHistorySuccess(boolean isRefresh, List<HistoryLiveBean> list);
    void getMatchVideoSuccess(List<HistoryLiveBean> list);
    void getTournamentSuccess(List<LiveVideoBean.LBean> list);
    void getVideoSuccess(boolean isRefresh,List<ViewMoreBean> list);
}
