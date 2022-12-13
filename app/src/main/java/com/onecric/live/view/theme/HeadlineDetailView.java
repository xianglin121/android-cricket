package com.onecric.live.view.theme;

import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface HeadlineDetailView extends BaseView<HeadlineBean> {
    void getDataSuccess(HeadlineBean model, List<HeadlineBean> list);

    void getTokenSuccess(String token);

    void getList(boolean isRefresh, List<MovingBean> list);

    void doCommentSuccess(Integer cid);

    void doFollowSuccess();
}
