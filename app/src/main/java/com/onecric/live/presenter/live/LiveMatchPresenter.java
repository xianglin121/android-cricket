package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.LiveMatchView;

import java.util.ArrayList;
import java.util.List;


public class LiveMatchPresenter extends BasePresenter<LiveMatchView> {
    public LiveMatchPresenter(LiveMatchView view) {
        attachView(view);
    }

    public void getList(int type, boolean isHot) {
        addSubscription(apiStores.getLivingListFiltrate(CommonAppConfig.getInstance().getToken(),  type, isHot?1:0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<LiveFiltrateBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), LiveFiltrateBean.class);
                            mvpView.getDataSuccess(isHot, list);
                        }else {
                            mvpView.getDataSuccess(isHot, new ArrayList<>());
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
