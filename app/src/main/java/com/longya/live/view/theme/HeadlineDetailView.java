package com.longya.live.view.theme;

import com.longya.live.model.HeadlineBean;
import com.longya.live.model.MovingBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface HeadlineDetailView extends BaseView<HeadlineBean> {
    void getDataSuccess(HeadlineBean model, List<HeadlineBean> list);

    void getTokenSuccess(String token);

    void getList(boolean isRefresh, List<MovingBean> list);

    void doCommentSuccess(Integer cid);

    void doFollowSuccess();
}
