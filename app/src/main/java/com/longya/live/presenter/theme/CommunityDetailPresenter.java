package com.longya.live.presenter.theme;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.model.CommunityBean;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.theme.CommunityDetailView;
import com.longya.live.view.theme.ThemeCommunityView;

import java.util.List;


public class CommunityDetailPresenter extends BasePresenter<CommunityDetailView> {
    public CommunityDetailPresenter(CommunityDetailView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page, int id, int is_refining, int is_reply) {
        addSubscription(apiStores.getCommunityDetail(CommonAppConfig.getInstance().getToken(), page, id, is_refining, is_reply),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ThemeClassifyBean info = JSONObject.parseObject(JSONObject.parseObject(data).getString("info"), ThemeClassifyBean.class);
                        List<CommunityBean> list = JSONObject.parseArray(JSONObject.parseObject(JSONObject.parseObject(data).getString("list")).getString("data"), CommunityBean.class);
                        mvpView.getDataSuccess(info);
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

    public void doCommunityLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doCommunityLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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
