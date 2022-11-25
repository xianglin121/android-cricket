package com.longya.live.presenter.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.LiveBean;
import com.longya.live.model.SpeakerBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.user.ActivityCenterView;
import com.longya.live.view.user.SpeakerHistoryView;

import java.util.ArrayList;
import java.util.List;

public class SpeakerHistoryPresenter extends BasePresenter<SpeakerHistoryView> {
    public SpeakerHistoryPresenter(SpeakerHistoryView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page) {
        addSubscription(apiStores.getSpeakerHistoryList(CommonAppConfig.getInstance().getToken(), page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<SpeakerBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), SpeakerBean.class);
                            mvpView.getDataSuccess(isRefresh, list);
                        }else {
                            mvpView.getDataSuccess(isRefresh, new ArrayList<>());
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
