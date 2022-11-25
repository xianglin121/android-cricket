package com.longya.live.presenter.theme;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.LiveBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.theme.ThemeHeadlineInnerView;
import com.longya.live.view.theme.ThemeHeadlineView;

import java.util.ArrayList;
import java.util.List;


public class ThemeHeadlineInnerPresenter extends BasePresenter<ThemeHeadlineInnerView> {
    public ThemeHeadlineInnerPresenter(ThemeHeadlineInnerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int id, int page) {
        addSubscription(apiStores.getHeadlineList(CommonAppConfig.getInstance().getToken(), page, id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<HeadlineBean> list = JSONObject.parseArray(JSONObject.parseObject(JSONObject.parseObject(data).getString("list")).getString("data"), HeadlineBean.class);
                            List<HeadlineBean> banners = JSONObject.parseArray(JSONObject.parseObject(data).getString("banner"), HeadlineBean.class);
                            mvpView.getDataSuccess(isRefresh, list, banners);
                        }else {
                            mvpView.getDataSuccess(isRefresh, new ArrayList<>(),  new ArrayList<>());
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
}
