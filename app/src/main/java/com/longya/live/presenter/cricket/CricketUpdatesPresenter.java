package com.longya.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.CricketFantasyBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.cricket.CricketFantasyView;
import com.longya.live.view.cricket.CricketUpdatesView;

public class CricketUpdatesPresenter extends BasePresenter<CricketUpdatesView> {
    public CricketUpdatesPresenter(CricketUpdatesView view) {
        attachView(view);
    }
}
