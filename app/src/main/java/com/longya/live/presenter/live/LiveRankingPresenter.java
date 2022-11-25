package com.longya.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.RankingBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveAnchorView;
import com.longya.live.view.live.LiveRankingView;

import java.util.List;


public class LiveRankingPresenter extends BasePresenter<LiveRankingView> {
    public LiveRankingPresenter(LiveRankingView view) {
        attachView(view);
    }

    public void getList(int anchorId, int type) {
        addSubscription(apiStores.getLiveRankingList(CommonAppConfig.getInstance().getToken(), anchorId, type),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<RankingBean> list = JSONObject.parseArray(data, RankingBean.class);
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
