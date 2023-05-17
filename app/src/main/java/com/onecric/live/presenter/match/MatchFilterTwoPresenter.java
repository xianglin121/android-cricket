package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.FilterBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.MatchFilterTwoView;

import java.util.List;
import java.util.TimeZone;

public class MatchFilterTwoPresenter extends BasePresenter<MatchFilterTwoView> {
    public MatchFilterTwoPresenter(MatchFilterTwoView view) {
        attachView(view);
    }

    public void getList(int filter, int type) {
        addSubscription(apiStores.getMatchFilterList(TimeZone.getDefault().getID(),filter, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        int countryCount = JSONObject.parseObject(data).getIntValue("count_country");
                        int selectCountryCount = JSONObject.parseObject(data).getIntValue("count_country_check");
                        List<FilterBean> competitionList = JSONObject.parseArray(JSONObject.parseObject(data).getString("competition"), FilterBean.class);
                        List<FilterBean> countryList = JSONObject.parseArray(JSONObject.parseObject(data).getString("country"), FilterBean.class);
                        mvpView.getDataSuccess(competitionList, countryList, countryCount, selectCountryCount);
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
