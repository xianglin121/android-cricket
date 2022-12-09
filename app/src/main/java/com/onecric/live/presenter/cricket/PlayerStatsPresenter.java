package com.onecric.live.presenter.cricket;

import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.view.cricket.PlayerStatsView;

public class PlayerStatsPresenter extends BasePresenter<PlayerStatsView> {
    public PlayerStatsPresenter(PlayerStatsView view) {
        attachView(view);
    }
}
