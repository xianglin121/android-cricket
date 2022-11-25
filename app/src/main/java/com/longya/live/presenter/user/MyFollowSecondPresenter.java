package com.longya.live.presenter.user;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.user.MyFollowFirstView;
import com.longya.live.view.user.MyFollowSecondView;

public class MyFollowSecondPresenter extends BasePresenter<MyFollowSecondView> {
    public MyFollowSecondPresenter(MyFollowSecondView view) {
        attachView(view);
    }
}
