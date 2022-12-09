package com.onecric.live.presenter.match;

import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.view.match.BasketballMatchStatusView;

public class BasketballMatchStatusPresenter extends BasePresenter<BasketballMatchStatusView> {
    public BasketballMatchStatusPresenter(BasketballMatchStatusView view) {
        attachView(view);
    }
}
