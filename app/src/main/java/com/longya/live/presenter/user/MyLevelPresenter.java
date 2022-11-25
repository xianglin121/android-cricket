package com.longya.live.presenter.user;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.user.MyFollowFirstView;
import com.longya.live.view.user.MyLevelView;

public class MyLevelPresenter extends BasePresenter<MyLevelView> {
    public MyLevelPresenter(MyLevelView view) {
        attachView(view);
    }
}
