package com.onecric.live.view.match;

import com.onecric.live.model.FootballMatchBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballMatchInnerView extends BaseView<List<FootballMatchBean>> {
    void getDataSuccess(boolean isRefresh, List<FootballMatchBean> list);
}
