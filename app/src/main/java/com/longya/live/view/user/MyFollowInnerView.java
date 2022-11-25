package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MyFollowInnerView extends BaseView<List<UserBean>> {
    void getDataSuccess(boolean isRefresh, List<UserBean> list);

    void doFollowSuccess(int id);
}
