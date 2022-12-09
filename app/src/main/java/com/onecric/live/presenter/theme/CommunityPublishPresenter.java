package com.onecric.live.presenter.theme;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.theme.CommunityPublishView;


public class CommunityPublishPresenter extends BasePresenter<CommunityPublishView> {
    public CommunityPublishPresenter(CommunityPublishView view) {
        attachView(view);
    }

    public void getToken() {
        addSubscription(apiStores.getQiniuToken(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        String token = JSONObject.parseObject(data).getString("token");
                        mvpView.getTokenSuccess(token);
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

    public void doPublish(int classifyId, String title, String file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("classification_id", classifyId);
        jsonObject.put("title", title);
        if (!TextUtils.isEmpty(file)) {
            jsonObject.put("flie", file);
        }
        addSubscription(apiStores.publishCommunity(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(null);
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
