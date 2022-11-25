package com.longya.live.presenter.match;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.match.BasketballMatchStatusView;
import com.longya.live.view.match.FootballMatchStatusView;

public class BasketballMatchStatusPresenter extends BasePresenter<BasketballMatchStatusView> {
    public BasketballMatchStatusPresenter(BasketballMatchStatusView view) {
        attachView(view);
    }
}
