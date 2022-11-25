package com.longya.live.presenter.live;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.live.LiveView;
import com.longya.live.view.theme.ThemeView;


public class LivePresenter extends BasePresenter<LiveView> {
    public LivePresenter(LiveView view) {
        attachView(view);
    }
}
