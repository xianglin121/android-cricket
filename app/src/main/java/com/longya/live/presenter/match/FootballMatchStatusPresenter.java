package com.longya.live.presenter.match;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.match.FootballMatchDetailView;
import com.longya.live.view.match.FootballMatchStatusView;

public class FootballMatchStatusPresenter extends BasePresenter<FootballMatchStatusView> {
    public FootballMatchStatusPresenter(FootballMatchStatusView view) {
        attachView(view);
    }
}
