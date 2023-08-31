package com.onecric.live.presenter.live;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.GameBannerBean;
import com.onecric.live.model.GameHistoryBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.OneGameView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class OneGamePresenter extends BasePresenter<OneGameView> {
    public OneGamePresenter(OneGameView view) {
        attachView(view);
    }
    public void getAllData(){
        addSubscription(apiStores.getGameHome(TimeZone.getDefault().getID()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<BannerBean> advertList = JSONObject.parseArray(JSONObject.parseObject(data).getString("banner"), BannerBean.class);
                            mvpView.getAdvertSuccess(advertList);
                            List<GameBannerBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), GameBannerBean.class);
                            mvpView.getBannerSuccess(list);
                            List<LiveAuthorBean> AnchorList = JSONObject.parseArray(JSONObject.parseObject(data).getString("users"), LiveAuthorBean.class);
                            mvpView.getAllDataSuccess(AnchorList);
                        }else{
                            mvpView.getAdvertSuccess(new ArrayList<>());
                            mvpView.getBannerSuccess(new ArrayList<>());
                            mvpView.getAllDataSuccess(new ArrayList<>());
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

    public void getHistoryList(boolean isRefresh,int page) {
        addSubscription(apiStores.getGameHistory(CommonAppConfig.getInstance().getToken(),page,TimeZone.getDefault().getID()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<GameHistoryBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), GameHistoryBean.class);
                            mvpView.getDataHistorySuccess(isRefresh,list);
                        }else{
                            mvpView.getDataHistorySuccess(isRefresh,new ArrayList<>());
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

    public void doFollow(int id,boolean isFollow) {
        addSubscription(apiStores.doFollow(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doFollowSuccess(id,isFollow);
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

    public void doLike(int id,int isLike) {
        addSubscription(apiStores.getLiveLike(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(),id, isLike),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {

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

    public void clickAdvert(int type,int id,String url){
        addSubscription(apiStores.getClickAdv(id,url,type),
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
