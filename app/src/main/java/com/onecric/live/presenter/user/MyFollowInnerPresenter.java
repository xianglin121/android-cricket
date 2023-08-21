 package com.onecric.live.presenter.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.AnchorBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.MyFollowInnerView;

import java.util.List;

public class MyFollowInnerPresenter extends BasePresenter<MyFollowInnerView> {
    public MyFollowInnerPresenter(MyFollowInnerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int type, int page) {
        if(type == 3){
            addSubscription(apiStores.getGameAnchorList(CommonAppConfig.getInstance().getToken()),
                    new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            if (!TextUtils.isEmpty(data)) {
                                List<AnchorBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), AnchorBean.class);
                                mvpView.getAnchorDataSuccess(isRefresh, list);
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
        }else if(type == 4){
            addSubscription(apiStores.getGameAnchorList(CommonAppConfig.getInstance().getToken(),2),
                    new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            if (!TextUtils.isEmpty(data)) {
                                List<AnchorBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), AnchorBean.class);
                                mvpView.getAnchorDataSuccess(isRefresh, list);
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
        }else{
            addSubscription(apiStores.getAttentionList(CommonAppConfig.getInstance().getToken(), page, type),
                    new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            List<UserBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), UserBean.class);
                            mvpView.getDataSuccess(isRefresh, list);
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

    public void doFollow(int id,int index) {
        addSubscription(apiStores.doFollow(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doFollowSuccess(id,index);
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
