package com.longya.live.presenter.theme;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.CommunityBean;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.theme.GroupSelectView;
import com.longya.live.view.theme.ThemeView;

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
