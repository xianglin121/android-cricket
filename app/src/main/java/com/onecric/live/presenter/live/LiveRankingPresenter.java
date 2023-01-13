package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.RankingBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveRankingView;

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
