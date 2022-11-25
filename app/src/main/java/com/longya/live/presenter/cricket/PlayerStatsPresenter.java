package com.longya.live.presenter.cricket;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.cricket.PlayerProfileView;
import com.longya.live.view.cricket.PlayerStatsView;

public class PlayerStatsPresenter extends BasePresenter<PlayerStatsView> {
    public PlayerStatsPresenter(PlayerStatsView view) {
        attachView(view);
    }
}
