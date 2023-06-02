package com.onecric.live.presenter.video;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.model.VideoCategoryBean;
import com.onecric.live.model.VideoShowBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.video.VideoView;

import java.util.List;
import java.util.TimeZone;

public class VideoPresenter extends BasePresenter<VideoView> {
    public VideoPresenter(VideoView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page) {
        addSubscription(apiStores.getVideoList(CommonAppConfig.getInstance().getToken(), page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ShortVideoBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ShortVideoBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
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

    public void getOneList(int id) {
        addSubscription(apiStores.getTournamentVideo(TimeZone.getDefault().getID(),id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<OneVideoBean.FirstCategoryBean> tList = null;
                        List<OneVideoBean.SecondCategoryBean> othersList = null;
                        List<VideoShowBean> showList = null;
                        OneVideoBean.BannerBean banner = null;
                        if(!TextUtils.isEmpty(data)){
                            if(!TextUtils.isEmpty(JSONObject.parseObject(data).getString("list"))){
                                tList = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), OneVideoBean.FirstCategoryBean.class);
                            }

                            if(!TextUtils.isEmpty(JSONObject.parseObject(data).getString("others"))){
                                othersList = JSONObject.parseArray(JSONObject.parseObject(data).getString("others"), OneVideoBean.SecondCategoryBean.class);
                            }

                            if(!TextUtils.isEmpty(JSONObject.parseObject(data).getString("list"))){
                                showList = JSONObject.parseArray(JSONObject.parseObject(data).getString("shows"), VideoShowBean.class);
                            }

                            if(!TextUtils.isEmpty(JSONObject.parseObject(data).getString("banner"))){
                                banner = JSONObject.parseObject(JSONObject.parseObject(data).getString("banner"), OneVideoBean.BannerBean.class);
                            }
                        }
                        mvpView.getInnerDataSuccess(tList,othersList,showList,banner);

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

    public void getCategory() {
        addSubscription(apiStores.getCategory(TimeZone.getDefault().getID()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if(!TextUtils.isEmpty(data) && !data.equals("[]")){
                            List<VideoCategoryBean> list = JSONObject.parseArray(data, VideoCategoryBean.class);
                            mvpView.getCategorySuccess(list);
                        }else{
                            mvpView.getDataFail(msg);
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
