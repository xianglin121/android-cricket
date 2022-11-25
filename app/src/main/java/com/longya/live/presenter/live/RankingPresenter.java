package com.longya.live.presenter.live;

import com.longya.live.CommonAppConfig;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.OpenNobleView;
import com.longya.live.view.live.RankingView;


public class RankingPresenter extends BasePresenter<RankingView> {
    public RankingPresenter(RankingView view) {
        attachView(view);
    }
}
