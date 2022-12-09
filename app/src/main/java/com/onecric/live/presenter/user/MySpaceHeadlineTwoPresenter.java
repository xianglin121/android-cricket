package com.onecric.live.presenter.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.user.MySpaceHeadlineTwoView;

import java.util.List;


public class MySpaceHeadlineTwoPresenter extends BasePresenter<MySpaceHeadlineTwoView> {
    public MySpaceHeadlineTwoPresenter(MySpaceHeadlineTwoView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page, int uid) {
        addSubscription(apiStores.getHeadlineCollectionList(CommonAppConfig.getInstance().getToken(), page, uid, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<HeadlineBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), HeadlineBean.class);
                            mvpView.getDataSuccess(isRefresh, list);
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

    public void doLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doHeadlineCommentLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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
