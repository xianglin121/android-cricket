package com.longya.live.view.live;

import com.longya.live.model.BasketballDetailBean;
import com.longya.live.model.FootballDetailBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveRoomBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;
import com.tencent.imsdk.relationship.UserInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;

public interface LiveDetailView extends BaseView<LiveRoomBean> {
    void getDataSuccess(FootballDetailBean bean);

    void getDataSuccess(BasketballDetailBean bean);

    void doFollowSuccess();

    void getDataSuccess(UserBean bean);

    void sendBgDanmuSuccess(int id, int anchorId, int level, String text, String msg);

    void sendBroadcastResponse(boolean isSuccess, String msg);
}
