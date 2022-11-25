package com.longya.live.presenter.theme;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.theme.ThemeHeadlineView;

import java.util.List;


public class ThemeHeadlinePresenter extends BasePresenter<ThemeHeadlineView> {
    public ThemeHeadlinePresenter(ThemeHeadlineView view) {
        attachView(view);
    }

    public void getClassify() {
        addSubscription(apiStores.getHeadlineClassify(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ThemeClassifyBean> list = JSONObject.parseArray(data, ThemeClassifyBean.class);
                        mvpView.getDataSuccess(list);
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
