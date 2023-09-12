package com.onecric.live.presenter.cricket;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.CricketSquadBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketSquadView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class CricketSquadPresenter extends BasePresenter<CricketSquadView> {
    public CricketSquadPresenter(CricketSquadView view) {
        attachView(view);
    }

    public void getList(int matchId,int tournament_id,int home_id,int away_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        jsonObject.put("tournament_id", tournament_id);
        jsonObject.put("home_id", home_id);
        jsonObject.put("away_id", away_id);
        jsonObject.put("timezone", TimeZone.getDefault().getID());
        addSubscription(apiStores.getCricketDetailSquadNew(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketSquadBean> benchList = JSONObject.parseArray(data, CricketSquadBean.class);
                //判空
                List<CricketSquadBean> mBList = new ArrayList<>();
                if(benchList != null){
                    mBList.addAll(benchList);
                    for(CricketSquadBean bean : benchList){
                        if(TextUtils.isEmpty(bean.getHome_player_name()) || TextUtils.isEmpty(bean.getAway_player_name())){
                            mBList.remove(bean);
                        }
                    }
                }
                mvpView.getDataSuccess(mBList,1);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(msg);
            }

            @Override
            public void onError(String msg) {
                mvpView.getDataFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getBenchList(int matchId,int tournament_id,int home_id,int away_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        jsonObject.put("tournament_id", tournament_id);
        jsonObject.put("home_id", home_id);
        jsonObject.put("away_id", away_id);
        jsonObject.put("timezone", TimeZone.getDefault().getID());
        addSubscription(apiStores.getCricketDetailSquadBench(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketSquadBean> benchList = JSONObject.parseArray(data, CricketSquadBean.class);
                //判空
                List<CricketSquadBean> mBList = new ArrayList<>();
                if(benchList != null){
                    mBList.addAll(benchList);
                    for(CricketSquadBean bean : benchList){
                        if(TextUtils.isEmpty(bean.getHome_player_name()) || TextUtils.isEmpty(bean.getAway_player_name())){
                            mBList.remove(bean);
                        }
                    }
                }
                mvpView.getDataSuccess(mBList,2);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(msg);
            }

            @Override
            public void onError(String msg) {
                mvpView.getDataFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
