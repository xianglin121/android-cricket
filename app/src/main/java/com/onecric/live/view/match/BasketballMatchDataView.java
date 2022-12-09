package com.onecric.live.view.match;

import com.onecric.live.model.DataInfoBean;
import com.onecric.live.model.DataMatchTypeBean;
import com.onecric.live.model.DataSeasonBean;
import com.onecric.live.model.DataStatusBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface BasketballMatchDataView extends BaseView<JsonBean> {
    void getDetailSuccess(List<DataSeasonBean> seasonList, DataInfoBean info, DataStatusBean statusBean, List<DataMatchTypeBean> matchTypeList);
}
