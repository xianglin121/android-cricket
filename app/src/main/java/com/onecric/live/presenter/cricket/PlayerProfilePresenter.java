package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.BattingBean;
import com.onecric.live.model.BowlingBean;
import com.onecric.live.model.FieldingBean;
import com.onecric.live.model.PlayerProfileBean;
import com.onecric.live.model.RecentMatchesBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.PlayerProfileView;

public class PlayerProfilePresenter extends BasePresenter<PlayerProfileView> {
    public PlayerProfilePresenter(PlayerProfileView view) {
        attachView(view);
    }

    public void getData(int playerId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("player_id", playerId);
        addSubscription(apiStores.getPlayerProfile(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getDataSuccess(JSONObject.parseObject(data, PlayerProfileBean.class));
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

    public void getBioData(int playerId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("player_id", playerId);
        addSubscription(apiStores.getPlayerProfileBio(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getDataSuccess(JSONObject.parseObject(data).getString("teams"),
                        JSONObject.parseArray(JSONObject.parseObject(data).getString("recent_matches"), RecentMatchesBean.class),
                        JSONObject.parseObject(data).getString("profile"));
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

    public void getCareerData(int playerId, String type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("player_id", playerId);
        jsonObject.put("type", type);
        addSubscription(apiStores.getPlayerProfileCareer(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getDataSuccess(JSONObject.parseObject(JSONObject.parseObject(data).getString("batting"), BattingBean.class),
                        JSONObject.parseObject(JSONObject.parseObject(data).getString("bowling"), BowlingBean.class),
                        JSONObject.parseObject(JSONObject.parseObject(data).getString("fielding"), FieldingBean.class),
                        JSONObject.parseArray(JSONObject.parseObject(data).getString("matches"), RecentMatchesBean.class));
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
