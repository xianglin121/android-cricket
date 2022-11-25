package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.model.MovingBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface LiveMovingCommentView extends BaseView<JsonBean> {
    void getData(MovingBean bean);

    void getTokenSuccess(String token);

    void getList(List<MovingBean> list);

    void doCommentSuccess(Integer cid);
}
