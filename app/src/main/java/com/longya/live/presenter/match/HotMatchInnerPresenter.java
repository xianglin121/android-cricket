package com.longya.live.presenter.match;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.Channel;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.match.HotMatchInnerView;

import java.util.ArrayList;
import java.util.List;

public class HotMatchInnerPresenter extends BasePresenter<HotMatchInnerView> {
    public HotMatchInnerPresenter(HotMatchInnerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, Channel channel, int page) {
        int type = 0;
        String football = "";
        String basketball = "";
        if (channel.sourceid > 0) {
            if (channel.getType() == 1) {
                type = 2;
                basketball = String.valueOf(channel.sourceid);
            }else {
                type = 1;
                football = String.valueOf(channel.sourceid);
            }
        }
        addSubscription(apiStores.getHotMatchList(CommonAppConfig.getInstance().getToken(), type, football, basketball, page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<MatchListBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MatchListBean.class);
                            mvpView.getDataSuccess(isRefresh, list);
                        }else {
                            mvpView.getDataSuccess(isRefresh, new ArrayList<>());
                        }
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
