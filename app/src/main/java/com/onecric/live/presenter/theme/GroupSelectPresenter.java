package com.onecric.live.presenter.theme;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.theme.GroupSelectView;

import java.util.List;


public class GroupSelectPresenter extends BasePresenter<GroupSelectView> {
    public GroupSelectPresenter(GroupSelectView view) {
        attachView(view);
    }

    public void getData() {
        addSubscription(apiStores.getCommunityClassify(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ThemeClassifyBean> classifyList = JSONObject.parseArray(data, ThemeClassifyBean.class);
                        mvpView.getDataSuccess(classifyList);
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
