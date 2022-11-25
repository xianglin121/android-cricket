package com.longya.live.presenter.match;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.match.FootballMatchDetailView;
import com.longya.live.view.match.FootballMatchSettingView;

public class FootballMatchSettingPresenter extends BasePresenter<FootballMatchSettingView> {
    public FootballMatchSettingPresenter(FootballMatchSettingView view) {
        attachView(view);
    }
}
