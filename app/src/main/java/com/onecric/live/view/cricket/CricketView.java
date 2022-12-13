package com.onecric.live.view.cricket;

import com.onecric.live.model.CricketTournamentBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketView extends BaseView<JsonBean> {
    void getDataSuccess(List<CricketTournamentBean> list);

    void getDataSuccess(boolean isRefresh, int total, List<CricketTournamentBean> list);
}
