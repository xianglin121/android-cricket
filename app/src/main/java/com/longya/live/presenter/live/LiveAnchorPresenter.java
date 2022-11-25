package com.longya.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.LiveBean;
import com.longya.live.model.MovingBean;
import com.longya.live.model.ReserveLiveBean;
import com.longya.live.model.ReserveMatchBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveAnchorView;
import com.longya.live.view.live.LiveChatView;

import java.util.ArrayList;
import java.util.List;


public class LiveAnchorPresenter extends BasePresenter<LiveAnchorView> {
    public LiveAnchorPresenter(LiveAnchorView view) {
        attachView(view);
    }

    public void getMovingList(int page, int anchorId) {
        addSubscription(apiStores.getAnchorMoving(CommonAppConfig.getInstance().getToken(), page, anchorId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<MovingBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MovingBean.class);
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

    public void getReserveLiveList(int page, int anchorId) {
        addSubscription(apiStores.getReserveLiveList(CommonAppConfig.getInstance().getToken(), page, anchorId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<ReserveLiveBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ReserveLiveBean.class);
                            mvpView.getReserveListSuccess(list);
                        }else {
                            mvpView.getReserveListSuccess(new ArrayList<>());
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

    public void doLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doMovingLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {

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

    public void doReserve(int position, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("live_id", id);
        addSubscription(apiStores.reserveMatchOne(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doReserveSuccess(position);
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
