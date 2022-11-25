package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.MessageBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MyMessageView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MessageBean> list);
}
