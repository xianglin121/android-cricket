package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.model.OneHistoryLiveBean;
import com.onecric.live.model.PlayCardsBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.live.OneLiveView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class OneLivePresenter extends BasePresenter<OneLiveView> {
    public OneLivePresenter(OneLiveView view) {
        attachView(view);
    }

    public void getBannerList(int type) {
        addSubscription(apiStores.getBannerList(type ),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
                            List<BannerBean> list = JSONObject.parseArray(data, BannerBean.class);
                            if(type == 1){
                                mvpView.getBannerSuccess(list);
                            }else if(list != null && list.size()>0){
                                mvpView.getAdvertSuccess(list.get(0).getImg(),list.get(0).getUrl());
                            }
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

    public void getAllData(){
        addSubscription(apiStores.getOneLiveTournament(TimeZone.getDefault().getID()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            mvpView.getAllDataSuccess(JSONObject.parseArray(data, LiveAuthorBean.class));
                        }else{
                            mvpView.getAllDataSuccess(new ArrayList<>());
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

    //卡片
    public void getPlayingCards(){
        addSubscription(apiStores.getPlayingCards(TimeZone.getDefault().getID()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
                            List<PlayCardsBean> today = JSONObject.parseArray(data, PlayCardsBean.class);
                            mvpView.getMatchSuccess(today);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
//                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
//                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getUpComingList() {
        TimeZone timeZone = TimeZone.getDefault();
        addSubscription(apiStores.getLiveMatchList(timeZone.getID()), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
//                List<LiveMatchListBean.MatchItemBean> today = JSONObject.parseArray(JSONObject.parseObject(data).getString("toay"), LiveMatchListBean.MatchItemBean.class);
                if (!TextUtils.isEmpty(data)) {
                    List<LiveMatchListBean.MatchItemBean> upcoming = JSONObject.parseArray(JSONObject.parseObject(data).getString("upcoming"), LiveMatchListBean.MatchItemBean.class);
                    mvpView.getUpcomingSuccess(upcoming);
                }else{
                    mvpView.getUpcomingSuccess(new ArrayList<>());
                }

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onError(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getHistoryList(int page) {
        addSubscription(apiStores.getHistoryLive(TimeZone.getDefault().getID(),page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<OneHistoryLiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), OneHistoryLiveBean.class);
                            mvpView.getDataHistorySuccess(list);
                        }else {
                            mvpView.getDataHistorySuccess(new ArrayList<>());
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
