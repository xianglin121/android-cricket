package com.onecric.live.presenter.cricket;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.fragment.CricketLiveFragment;
import com.onecric.live.model.CricketLiveBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.cricket.CricketLiveView;

import java.util.List;

public class CricketLivePresenter extends BasePresenter<CricketLiveView> {
    public CricketLivePresenter(CricketLiveView view) {
        attachView(view);
    }

    public void getList(int matchId, int page,int limit, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        jsonObject.put("pagesize", limit);
        jsonObject.put("start", page);
        System.out.println("werqwerqrwee  " + matchId);
        addSubscription(apiStores.getCricketDetailLive(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketLiveBean> list = JSONObject.parseArray(data, CricketLiveBean.class);
                if (list != null) {
                    if (type == CricketLiveFragment.TYPE_LOADMORE) {
                        mvpView.loadMoreDataSuccess(list);
                    } else {
                        mvpView.getDataSuccess(list);
                    }
                } else {
                    if (type == CricketLiveFragment.TYPE_LOADMORE) {
                        mvpView.loadMoreDataFailed();
                    } else {
                        mvpView.getDataFail(msg);
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                if (type == CricketLiveFragment.TYPE_LOADMORE) {
                    mvpView.loadMoreDataFailed();
                } else {
                    mvpView.getDataFail(msg);
                }
            }

            @Override
            public void onError(String msg) {
                if (type == CricketLiveFragment.TYPE_LOADMORE) {
                    mvpView.loadMoreDataFailed();
                } else {
                    mvpView.getDataFail(msg);
                }
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
