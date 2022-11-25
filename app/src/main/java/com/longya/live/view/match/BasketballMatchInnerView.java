package com.longya.live.view.match;

import com.longya.live.model.BasketballMatchBean;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface BasketballMatchInnerView extends BaseView<List<BasketballMatchBean>> {
    void getDataSuccess(boolean isRefresh, List<BasketballMatchBean> list);
}
