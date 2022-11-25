package com.longya.live.view.live;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MovingBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface CommunityCommentView extends BaseView<JsonBean> {
    void getData(CommunityBean bean);

    void getTokenSuccess(String token);

    void getList(List<CommunityBean> list);

    void doCommentSuccess(Integer cid);
}
