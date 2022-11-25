package com.longya.live.view.live;

import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveClassifyBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface LiveClassifyView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveClassifyBean> list);

    void doReserveSuccess(int position);
}
