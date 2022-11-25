package com.longya.live.presenter.match;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.match.BasketballMatchSettingView;
import com.longya.live.view.match.FootballMatchSettingView;

public class BasketballMatchSettingPresenter extends BasePresenter<BasketballMatchSettingView> {
    public BasketballMatchSettingPresenter(BasketballMatchSettingView view) {
        attachView(view);
    }
}
