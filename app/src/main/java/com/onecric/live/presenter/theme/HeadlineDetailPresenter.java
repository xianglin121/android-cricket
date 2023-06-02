package com.onecric.live.presenter.theme;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.theme.HeadlineDetailView;

import java.util.List;
import java.util.TimeZone;


public class HeadlineDetailPresenter extends BasePresenter<HeadlineDetailView> {
    public HeadlineDetailPresenter(HeadlineDetailView view) {
        attachView(view);
    }

    public void getInfo(boolean isRefresh, int page, int order, int id) {
        addSubscription(apiStores.getHeadlineInfo(TimeZone.getDefault().getID(),CommonAppConfig.getInstance().getToken(), page, order, id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        HeadlineBean info = JSONObject.parseObject(JSONObject.parseObject(data).getString("info"), HeadlineBean.class);
                        List<HeadlineBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("recommend"), HeadlineBean.class);
                        List<MovingBean> commentList = JSONObject.parseArray(JSONObject.parseObject(JSONObject.parseObject(data).getString("commentList")).getString("data"), MovingBean.class);
                        mvpView.getDataSuccess(info, list);
                        mvpView.getList(isRefresh, commentList);
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

    public void getToken() {
        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            return;
        }
        addSubscription(apiStores.getQiniuToken(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        String token = JSONObject.parseObject(data).getString("token");
                        mvpView.getTokenSuccess(token);
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
                        mvpView.doFollowSuccess();
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

    public void doComment(int id, String content, Integer cid, List<String> file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("content", content);
        if (cid != null) {
            jsonObject.put("cid", cid);
        }
        if (file != null && file.size() > 0) {
            jsonObject.put("flie", JSONObject.toJSONString(file));
        }
        addSubscription(apiStores.doHeadlineComment(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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

    public void doHeadlineLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doHeadlineLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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

    public void doHeadlineCollect(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doHeadlineCollect(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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

    public void doLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        addSubscription(apiStores.doHeadlineCommentLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
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
