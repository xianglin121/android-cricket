package com.onecric.live.presenter.video;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.model.ReportBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.video.VideoPagerView;

import java.util.List;

public class VideoPagerPresenter extends BasePresenter<VideoPagerView> {
    public VideoPagerPresenter(VideoPagerView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, int page) {
        ((VideoPagerActivity)mvpView).isRequesting = true;
        addSubscription(apiStores.getVideoList(CommonAppConfig.getInstance().getToken(), page),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                        List<ShortVideoBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ShortVideoBean.class);
                        mvpView.getDataSuccess(isRefresh, list);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                    }

                    @Override
                    public void onError(String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getReportList() {
        addSubscription(apiStores.getReportList(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<ReportBean> list = JSONObject.parseArray(data, ReportBean.class);
                        mvpView.getReportListSuccess(list);
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

    public void doReport(int id, int videoId) {
        addSubscription(apiStores.doReport(CommonAppConfig.getInstance().getToken(), id, videoId, 0, 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doReportSuccess();
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

    public void doComment(int id, String content, Integer cid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("content", content);
        if (cid != null) {
            jsonObject.put("cid", cid);
        }
        addSubscription(apiStores.doVideoComment(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doCommentSuccess(cid);
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

    public void doFollow(int id) {
        addSubscription(apiStores.doFollow(CommonAppConfig.getInstance().getToken(), id),
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

    public void doWatch(int id) {
        addSubscription(apiStores.doWatch(CommonAppConfig.getInstance().getToken(), id),
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

    public void doVideoLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doVideoLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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

    public void doVideoCollect(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doVideoCollect(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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

    //fixme 对接口：搜索video
    public void getKeywordList(String key) {
        ((VideoPagerActivity)mvpView).isRequesting = true;
        addSubscription(apiStores.getVideoList(CommonAppConfig.getInstance().getToken(), 1),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                        List<ShortVideoBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), ShortVideoBean.class);
                        mvpView.getDataSuccess(false, list);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                    }

                    @Override
                    public void onError(String msg) {
                        ((VideoPagerActivity)mvpView).isRequesting = false;
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
