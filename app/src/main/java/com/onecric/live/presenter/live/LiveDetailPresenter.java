package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.BasketballDetailBean;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.LiveRoomBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveDetailView;
import com.tencent.qcloud.tuikit.tuichat.interfaces.GroupChatEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class LiveDetailPresenter extends BasePresenter<LiveDetailView> {
    private String mGroupId;
    private GroupChatEventListener groupChatEventListener;

    public LiveDetailPresenter(LiveDetailView view) {
        attachView(view);
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public void getInfo(boolean isLoginUpdate,int mLiveId) {
        addSubscription(apiStores.getLiveDetail(CommonAppConfig.getInstance().getToken(), mLiveId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if(isLoginUpdate){
                            mvpView.getUpdateUserData(JSONObject.parseObject(data, LiveRoomBean.class));
                        }else{
                            mvpView.getDataSuccess(JSONObject.parseObject(data, LiveRoomBean.class));
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

    public void doFollow(int id) {
        addSubscription(apiStores.doFollow(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doFollowSuccess();
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

    public void getFootballDetail(int id) {
        addSubscription(apiStores.getFootballDetail(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseObject(data, FootballDetailBean.class));
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

    public void getBasketballDetail(int id) {
        addSubscription(apiStores.getBasketballDetail(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(JSONObject.parseObject(data, BasketballDetailBean.class));
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

    public void getUserInfo() {
        addSubscription(apiStores.getUserInfo(CommonAppConfig.getInstance().getToken(), 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            mvpView.getDataSuccess(JSONObject.parseObject(data, UserBean.class));
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

    public void sendBgDanmu(int id, int anchorId, int level, String text) {
        addSubscription(apiStores.sendBgDanmu(CommonAppConfig.getInstance().getToken(), id, anchorId, text),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.sendBgDanmuSuccess(id, anchorId, level, text, "");
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.sendBgDanmuSuccess(id, anchorId, level, text, msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.sendBgDanmuSuccess(id, anchorId, level, text, msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void sendBroadcast(String content) {
        addSubscription(apiStores.sendBroadcast(CommonAppConfig.getInstance().getToken(), content),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.sendBroadcastResponse(true, "");
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.sendBroadcastResponse(false, msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.sendBroadcastResponse(false, msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getMatchDetail(int matchId) {
        JSONObject jsonObject = new JSONObject();
        TimeZone timeZone = TimeZone.getDefault();
        jsonObject.put("timezone", timeZone.getID());
        jsonObject.put("match_id", matchId);
        addSubscription(apiStores.getCricketDetail(CommonAppConfig.getInstance().getToken(),getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getMatchDataSuccess(JSONObject.parseObject(data, CricketMatchBean.class));
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

    public void getUpdatesDetail(int matchId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", matchId);
        jsonObject.put("type", 0);
        addSubscription(apiStores.getCricketDetailUpdates(getRequestBody(jsonObject)), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mvpView.getUpdatesDataSuccess(JSONObject.parseArray(data, UpdatesBean.class));
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

    public void goLike(int id, int isLike) {
        addSubscription(apiStores.getLiveLike(CommonAppConfig.getInstance().getToken(),id, isLike),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.showLikeSuccess();
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
