package com.onecric.live.view.user;

import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

public interface PersonalHomepageView extends BaseView<UserBean> {
    void doFollowSuccess(int id);
}
