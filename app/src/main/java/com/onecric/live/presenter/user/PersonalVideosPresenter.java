package com.onecric.live.presenter.user;


import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.video.VideoView;

import java.util.List;

public class PersonalVideosPresenter extends BasePresenter<VideoView> {
    public PersonalVideosPresenter(VideoView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page, int type, int id) {
        addSubscription(apiStores.getUserVideoList(CommonAppConfig.getInstance().getToken(), page, type, id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ShortVideoBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ShortVideoBean.class);
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