package com.longya.live.view.cricket;

import com.longya.live.model.BattingBean;
import com.longya.live.model.BowlingBean;
import com.longya.live.model.FieldingBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.PlayerProfileBean;
import com.longya.live.model.RecentMatchesBean;
import com.longya.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface PlayerProfileView extends BaseView<PlayerProfileBean> {
    void getDataSuccess(String teams, List<RecentMatchesBean> list, String profile);

    void getDataSuccess(BattingBean batting, BowlingBean bowling, FieldingBean fielding, List<RecentMatchesBean> list);
}
