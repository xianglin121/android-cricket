package com.longya.live.view.cricket;

import com.longya.live.model.CricketMatchBean;
import com.longya.live.model.CricketPointsBean;
import com.longya.live.model.CricketStatsBean;
import com.longya.live.model.CricketTeamBean;
import com.longya.live.model.CricketTournamentBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UpdatesBean;
import com.longya.live.view.BaseView;

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
