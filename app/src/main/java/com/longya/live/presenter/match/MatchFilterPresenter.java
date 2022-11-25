package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.FilterBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.MatchFilterView;
import com.longya.live.view.match.MatchView;

import java.util.List;

public class MatchFilterPresenter extends BasePresenter<MatchFilterView> {
    public MatchFilterPresenter(MatchFilterView view) {
        attachView(view);
    }
}
