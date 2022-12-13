package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.ScorecardBatterBean;
import com.onecric.live.model.ScorecardBowlerBean;
import com.onecric.live.model.ScorecardWicketBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketScorecardView;

import java.util.List;

public class CricketScorecardPresenter extends BasePresenter<CricketScorecardView> {
    public CricketScorecardPresenter(CricketScorecardView view) {
        attachView(view);
    }

    public void getData(boolean isHome, int id, int teamId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("team_id", teamId);
        addSubscription(apiStores.getMatchScorecard(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                try {
                    List<ScorecardBatterBean> battingList = JSONObject.parseArray(JSONObject.parseObject(data).getString("batting_info"), ScorecardBatterBean.class);
                    List<ScorecardBowlerBean> bowlList = JSONObject.parseArray(JSONObject.parseObject(data).getString("bowling_info"), ScorecardBowlerBean.class);
                    List<ScorecardWicketBean> wicketList = JSONObject.parseArray(JSONObject.parseObject(data).getString("partnerships"), ScorecardWicketBean.class);
                    if (isHome) {
                        mvpView.getHomeDataSuccess(battingList, bowlList, wicketList, JSONObject.parseObject(data).getString("extras"), JSONObject.parseObject(data).getString("no_batting_name"));
                    } else {
                        mvpView.getAwayDataSuccess(battingList, bowlList, wicketList, JSONObject.parseObject(data).getString("extras"), JSONObject.parseObject(data).getString("no_batting_name"));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
