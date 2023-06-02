package com.onecric.live.presenter.theme;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.theme.ThemeHeadlineInnerView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class ThemeHeadlineInnerPresenter extends BasePresenter<ThemeHeadlineInnerView> {
    public ThemeHeadlineInnerPresenter(ThemeHeadlineInnerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int id, int page) {
//        addSubscription(apiStores.getHeadlineListNew(CommonAppConfig.getInstance().getToken(), page, id),
        addSubscription(apiStores.getHeadlineList(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), page, id),
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
