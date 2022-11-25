package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchAnchorView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<UserBean> list);

    void doFollowSuccess(int id);
}
