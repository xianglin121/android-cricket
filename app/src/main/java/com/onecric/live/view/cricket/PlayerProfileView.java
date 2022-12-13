package com.onecric.live.view.cricket;

import com.onecric.live.model.BattingBean;
import com.onecric.live.model.BowlingBean;
import com.onecric.live.model.FieldingBean;
import com.onecric.live.model.PlayerProfileBean;
import com.onecric.live.model.RecentMatchesBean;
import com.onecric.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface PlayerProfileView extends BaseView<PlayerProfileBean> {
    void getDataSuccess(String teams, List<RecentMatchesBean> list, String profile);

    void getDataSuccess(BattingBean batting, BowlingBean bowling, FieldingBean fielding, List<RecentMatchesBean> list);
}
