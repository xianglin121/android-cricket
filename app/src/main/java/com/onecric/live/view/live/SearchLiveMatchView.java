package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SearchLiveMatchView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MatchListBean> list);

    void doReserveSuccess(int position);
}
