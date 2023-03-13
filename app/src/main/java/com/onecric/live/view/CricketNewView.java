package com.onecric.live.view;

import com.onecric.live.model.CricketDayBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.CricketNewBean;
import com.onecric.live.model.CricketTournamentBean;
import com.onecric.live.model.JsonBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketNewView extends BaseView<JsonBean> {
    void getDataSuccess(List<CricketFiltrateBean> list);

    void getDataSuccess(int type, List<CricketDayBean> list, String lastDay, String endDay);

    void getDataFail(int type ,String msg);

}
