package com.onecric.live.view.user;

import com.onecric.live.model.AnchorBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MyFollowInnerView extends BaseView<List<UserBean>> {
    void getDataSuccess(boolean isRefresh, List<UserBean> list);
    void getAnchorDataSuccess(boolean isRefresh, List<AnchorBean> list);

    void doFollowSuccess(int id);
}
