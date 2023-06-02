package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.SearchLiveSecondView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class SearchLiveSecondPresenter extends BasePresenter<SearchLiveSecondView> {
    public SearchLiveSecondPresenter(SearchLiveSecondView view) {
        attachView(view);
    }

    public void getList(String content) {
        addSubscription(apiStores.searchLive(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), content),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveBean.class);
                            mvpView.getDataSuccess(list);
                        }else {
                            mvpView.getDataSuccess(new ArrayList<>());
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
