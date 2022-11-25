package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.DataScheduleBean;
import com.longya.live.model.DataStageBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballDataScheduleView;
import com.longya.live.view.match.FootballDataScheduleView;

import java.util.List;

public class FootballDataSchedulePresenter extends BasePresenter<FootballDataScheduleView> {
    public FootballDataSchedulePresenter(FootballDataScheduleView view) {
        attachView(view);
    }

    public void getStageList(int id) {
        addSubscription(apiStores.getFootballMatchDataStage(id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataStageBean> list = JSONObject.parseArray(data, DataStageBean.class);
                        mvpView.getStageDataSuccess(list);
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

    public void getList(int id, int seasonId, int stageId, int sonId) {
        addSubscription(apiStores.getFootballMatchDataDetail(id, seasonId, stageId, sonId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataScheduleBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), DataScheduleBean.class);
                        mvpView.getDataSuccess(list);
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
