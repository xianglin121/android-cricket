package com.longya.live.view.match;

import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataMatchTypeBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.model.FootballDataStatusBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface FootballMatchDataView extends BaseView<JsonBean> {
    void getDetailSuccess(List<DataSeasonBean> seasonList, DataInfoBean info, FootballDataStatusBean statusBean);
}
