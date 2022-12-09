package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.SearchComplexView;

import java.util.List;


public class SearchComplexPresenter extends BasePresenter<SearchComplexView> {
    public SearchComplexPresenter(SearchComplexView view) {
        attachView(view);
    }

    public void getList(boolean isRefresh, String content) {
        addSubscription(apiStores.searchAll(CommonAppConfig.getInstance().getToken(), content),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<UserBean> anchorList = JSONObject.parseArray(JSONObject.parseObject(data).getString("anchorList"), UserBean.class);
                        List<LiveBean> liveList = JSONObject.parseArray(JSONObject.parseObject(data).getString("liveList"), LiveBean.class);
                        List<MatchListBean> matchList = JSONObject.parseArray(JSONObject.parseObject(data).getString("HotMatchList"), MatchListBean.class);
                        List<HeadlineBean> headlineList = JSONObject.parseArray(JSONObject.parseObject(data).getString("HeadlinesList"), HeadlineBean.class);
                        List<CommunityBean> communityList = JSONObject.parseArray(JSONObject.parseObject(data).getString("circleList"), CommunityBean.class);
                        mvpView.getDataSuccess(isRefresh, anchorList, liveList, matchList, headlineList, communityList);
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
                        mvpView.doFollowSuccess(id);
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

    public void doReserve(int position, int matchId, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_id", matchId);
        jsonObject.put("type", type);
        addSubscription(apiStores.reserveMatchTwo(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.doReserveSuccess(position);
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
