package com.longya.live.presenter.cricket;

import android.app.job.JobScheduler;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.ScorecardBatterBean;
import com.longya.live.model.ScorecardBowlerBean;
import com.longya.live.model.ScorecardWicketBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.cricket.CricketScorecardView;
import com.longya.live.view.cricket.PlayerStatsView;

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
