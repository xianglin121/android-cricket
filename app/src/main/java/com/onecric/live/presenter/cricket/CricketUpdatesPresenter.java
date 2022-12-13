package com.onecric.live.presenter.cricket;

import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.view.cricket.CricketUpdatesView;

public class CricketUpdatesPresenter extends BasePresenter<CricketUpdatesView> {
    public CricketUpdatesPresenter(CricketUpdatesView view) {
        attachView(view);
    }
}
