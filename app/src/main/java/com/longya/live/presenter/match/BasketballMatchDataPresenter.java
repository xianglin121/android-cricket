package com.longya.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.model.DataInfoBean;
import com.longya.live.model.DataMatchTypeBean;
import com.longya.live.model.DataSeasonBean;
import com.longya.live.model.DataStatusBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.BasketballMatchDataView;

import java.util.List;

public class BasketballMatchDataPresenter extends BasePresenter<BasketballMatchDataView> {
    public BasketballMatchDataPresenter(BasketballMatchDataView view) {
        attachView(view);
    }

    public void getDetail(int id, int page, int seasonId, int typeId) {
        addSubscription(apiStores.getBasketballMatchDataDetail(id, page, seasonId, typeId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<DataSeasonBean> seasonList = JSONObject.parseArray(JSONObject.parseObject(data).getString("season"), DataSeasonBean.class);
                        DataInfoBean info = JSONObject.parseObject(JSONObject.parseObject(data).getString("info"), DataInfoBean.class);
                        DataStatusBean dataStatusBean = JSONObject.parseObject(data, DataStatusBean.class);
                        if (dataStatusBean != null) {
                            dataStatusBean.setTotal(JSONObject.parseObject(JSONObject.parseObject(data).getString("list")).getString("total"));
                        }
                        List<DataMatchTypeBean> matchTypeList = JSONObject.parseArray(JSONObject.parseObject(data).getString("matchTypeList"), DataMatchTypeBean.class);
                        mvpView.getDetailSuccess(seasonList, info, dataStatusBean, matchTypeList);
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
