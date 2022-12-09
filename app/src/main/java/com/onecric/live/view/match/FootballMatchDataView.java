package com.onecric.live.view.match;

import com.onecric.live.model.DataInfoBean;
import com.onecric.live.model.DataSeasonBean;
import com.onecric.live.model.FootballDataStatusBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballMatchDataView extends BaseView<JsonBean> {
    void getDetailSuccess(List<DataSeasonBean> seasonList, DataInfoBean info, FootballDataStatusBean statusBean);
}
