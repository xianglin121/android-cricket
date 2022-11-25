package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchLiveMatchView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MatchListBean> list);

    void doReserveSuccess(int position);
}
