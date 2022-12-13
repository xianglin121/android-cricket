package com.onecric.live.view.live;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface CommunityCommentView extends BaseView<JsonBean> {
    void getData(CommunityBean bean);

    void getTokenSuccess(String token);

    void getList(List<CommunityBean> list);

    void doCommentSuccess(Integer cid);
}
