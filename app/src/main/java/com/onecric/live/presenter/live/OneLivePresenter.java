package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.OneLiveView;

import java.util.ArrayList;
import java.util.List;


public class OneLivePresenter extends BasePresenter<OneLiveView> {
    public OneLivePresenter(OneLiveView view) {
        attachView(view);
    }

    public void getOtherList() {
        addSubscription(apiStores.getLivingListFiltrate(CommonAppConfig.getInstance().getToken(), 1, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveFiltrateBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveFiltrateBean.class);
                            mvpView.getOtherDataSuccess(list);
                        }else {
                            mvpView.getOtherDataSuccess(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getOtherDataSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getOtherDataSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }
}
