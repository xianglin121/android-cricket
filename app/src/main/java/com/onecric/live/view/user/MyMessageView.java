package com.onecric.live.view.user;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MessageBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MyMessageView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MessageBean> list);
}
