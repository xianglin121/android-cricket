package com.onecric.live.presenter.video;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.VideoDetailBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.video.VideoDetailView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class VideoDetailPresenter extends BasePresenter<VideoDetailView> {
    public VideoDetailPresenter(VideoDetailView view) {
        attachView(view);
    }
    public void getDetail(String tournament,int id){
        addSubscription(apiStores.getTournamentVideoInfo(TimeZone.getDefault().getID(),tournament,id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            List<ViewMoreBean> list = null;
                            VideoDetailBean bean = null;
                            if (!TextUtils.isEmpty(JSONObject.parseObject(data).getString("list"))) {
                                list = JSONObject.parseArray(JSONObject.parseObject(JSONObject.parseObject(data).getString("list")).getString("data"), ViewMoreBean.class);
                            }

                            if (!TextUtils.isEmpty(JSONObject.parseObject(data).getString("info"))) {
                                bean = JSONObject.parseObject(JSONObject.parseObject(data).getString("info"),VideoDetailBean.class);
                            }
                            mvpView.getDataSuccess(list,bean);
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

    public void getMoreVideoList(String name, int page) {
        addSubscription(apiStores.getTournamentVideoList(TimeZone.getDefault().getID(),name,page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
                            List<ViewMoreBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ViewMoreBean.class);
                            mvpView.getVideoSuccess(list);
                        }else {
                            mvpView.getVideoSuccess(new ArrayList<>());
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
