package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveMovingCommentView extends BaseView<JsonBean> {
    void getData(MovingBean bean);

    void getTokenSuccess(String token);

    void getList(List<MovingBean> list);

    void doCommentSuccess(Integer cid);
}
