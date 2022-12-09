package com.onecric.live.presenter.theme;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.theme.ThemeHeadlineView;

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
