package com.onecric.live.presenter.match;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.LiveAnchorBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.BasketballMatchLiveView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class BasketballMatchLivePresenter extends BasePresenter<BasketballMatchLiveView> {
    public BasketballMatchLivePresenter(BasketballMatchLiveView view) {
        attachView(view);
    }

    public void getAnchorList(int id) {
        addSubscription(apiStores.getMatchAnchorList(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<LiveAnchorBean> list = JSONObject.parseArray(data, LiveAnchorBean.class);
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

    public void getList(boolean isRefresh, int type, int page) {
        addSubscription(apiStores.getLivingList(CommonAppConfig.getInstance().getToken(), page, type, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveBean.class);
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
