package com.onecric.live.view.live;

import com.onecric.live.model.BasketballDetailBean;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.LiveRoomBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;
import com.tencent.liteav.demo.superplayer.model.SquadDataBean;

import java.util.List;

public interface LiveDetailView extends BaseView<LiveRoomBean> {
    void getDataSuccess(FootballDetailBean bean);

    void getDataSuccess(BasketballDetailBean bean);

    void doFollowSuccess();

    void getDataSuccess(UserBean bean);

    void getUpdateUserData(LiveRoomBean bean);

    void sendBgDanmuSuccess(int id, int anchorId, int level, String text, String msg);

    void sendBroadcastResponse(boolean isSuccess, String msg);

    void getMatchDataSuccess(CricketMatchBean model);

    void getUpdatesDataSuccess(List<UpdatesBean> list);

    void showLikeSuccess();

    void getSquadData(List<SquadDataBean> beanList);
}
