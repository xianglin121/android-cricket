package com.longya.live.presenter.cricket;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.cricket.ExpertAnalysisView;
import com.longya.live.view.cricket.PlayerStatsView;

public class ExpertAnalysisPresenter extends BasePresenter<ExpertAnalysisView> {
    public ExpertAnalysisPresenter(ExpertAnalysisView view) {
        attachView(view);
    }
}
