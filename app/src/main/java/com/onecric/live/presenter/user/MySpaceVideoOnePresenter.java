package com.onecric.live.presenter.user;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.MySpaceVideoOneView;

import java.util.List;

public class MySpaceVideoOnePresenter extends BasePresenter<MySpaceVideoOneView> {
    public MySpaceVideoOnePresenter(MySpaceVideoOneView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page, int uid) {
        addSubscription(apiStores.getVideoPublishList(CommonAppConfig.getInstance().getToken(), page, uid),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ShortVideoBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ShortVideoBean.class);
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
}
