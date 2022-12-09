package com.onecric.live.view.cricket;

import com.onecric.live.model.CricketPointsBean;
import com.onecric.live.model.CricketStatsBean;
import com.onecric.live.model.CricketTeamBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketInnerView extends BaseView<JsonBean> {
    void getTeamDataSuccess(List<CricketTeamBean> list);

    void getPointsDataSuccess(List<CricketPointsBean> list);

    void getUpdatesDataSuccess(List<UpdatesBean> list);

    void getStatsDataSuccess(CricketStatsBean cricketStatsBean);
}
