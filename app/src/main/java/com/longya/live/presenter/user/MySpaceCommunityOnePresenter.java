package com.longya.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.CommunityBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.theme.ThemeCommunityFollowView;
import com.longya.live.view.user.MySpaceCommunityOneView;

import java.util.List;


public class MySpaceCommunityOnePresenter extends BasePresenter<MySpaceCommunityOneView> {
    public MySpaceCommunityOnePresenter(MySpaceCommunityOneView view) {
        attachView(view);
    }

    public void getData(boolean isRefresh, int page, int uid) {
        addSubscription(apiStores.getCommunityPublishList(CommonAppConfig.getInstance().getToken(), page, uid),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<CommunityBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CommunityBean.class);
                        mvpView.getList(isRefresh, list);
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

    public void doCommunityLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doCommunityLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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
}
