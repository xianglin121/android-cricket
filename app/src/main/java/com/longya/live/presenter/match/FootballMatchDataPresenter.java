package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataMatchTypeBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.model.FootballDataStatusBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballMatchDataView;
import com.longya.live.view.match.FootballMatchDataView;

import java.util.List;

public class FootballMatchDataPresenter extends BasePresenter<FootballMatchDataView> {
    public FootballMatchDataPresenter(FootballMatchDataView view) {
        attachView(view);
    }

    public void getDetail(int id, int seasonId, int stageId, int sonId) {
        addSubscription(apiStores.getFootballMatchDataDetail(id, seasonId, stageId, sonId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataSeasonBean> seasonList = JSONObject.parseArray(JSONObject.parseObject(data).getString("season"), DataSeasonBean.class);
                        DataInfoBean info = JSONObject.parseObject(JSONObject.parseObject(data).getString("info"), DataInfoBean.class);
                        FootballDataStatusBean dataStatusBean = JSONObject.parseObject(data, FootballDataStatusBean.class);
                        mvpView.getDetailSuccess(seasonList, info, dataStatusBean);
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
