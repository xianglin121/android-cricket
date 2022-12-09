package com.onecric.live.view.match;

import com.onecric.live.model.BasketballMatchBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface BasketballMatchScheduleView extends BaseView<List<BasketballMatchBean>> {
    void getDataSuccess(boolean isRefresh, List<BasketballMatchBean> list);
}
