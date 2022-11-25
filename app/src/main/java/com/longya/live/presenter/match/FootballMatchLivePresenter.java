package com.longya.live.presenter.match;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.CommunityBean;
import com.longya.live.model.LiveAnchorBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.LiveUserBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.FootballMatchLiveView;
import com.longya.live.view.match.FootballMatchSettingView;

import java.util.ArrayList;
import java.util.List;

public class FootballMatchLivePresenter extends BasePresenter<FootballMatchLiveView> {
    public FootballMatchLivePresenter(FootballMatchLiveView view) {
        attachView(view);
    }

    public void getAnchorList(int id) {
        addSubscription(apiStores.getMatchAnchorList(CommonAppConfig.getInstance().getToken(), id),
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
