package com.onecric.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.MySpaceCommunityTwoView;

import java.util.List;


public class MySpaceCommunityTwoPresenter extends BasePresenter<MySpaceCommunityTwoView> {
    public MySpaceCommunityTwoPresenter(MySpaceCommunityTwoView view) {
        attachView(view);
    }

    public void getData(boolean isRefresh, int page, int uid) {
        addSubscription(apiStores.getCommunityCollectionList(CommonAppConfig.getInstance().getToken(), page, uid, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<CommunityBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CommunityBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
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
        addSubscription(apiStores.doCommunityCommentLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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
