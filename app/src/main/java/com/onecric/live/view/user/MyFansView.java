package com.onecric.live.view.user;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MyFansView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<UserBean> list);

    void doFollowSuccess(int id);
}
