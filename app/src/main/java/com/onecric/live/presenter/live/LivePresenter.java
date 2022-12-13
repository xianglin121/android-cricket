package com.onecric.live.presenter.live;

import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.view.live.LiveView;


public class LivePresenter extends BasePresenter<LiveView> {
    public LivePresenter(LiveView view) {
        attachView(view);
    }
}
