package com.onecric.live.presenter.match;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.activity.RangqiuIndexDetailActivity;
import com.onecric.live.fragment.BasketballMatchIndexInnerFragment;
import com.onecric.live.model.BasketballIndexListBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.RangqiuIndexDetailView;

import java.util.List;

public class RangqiuIndexDetailPresenter extends BasePresenter<RangqiuIndexDetailView> {
    public RangqiuIndexDetailPresenter(RangqiuIndexDetailView view) {
        attachView(view);
    }

    public void getList(int enterType, int id, int type) {
        String mType = "";
        if (type == BasketballMatchIndexInnerFragment.TYPE_RANGQIU) {
            mType = "asia";
        }else if (type == BasketballMatchIndexInnerFragment.TYPE_OUPEI) {
            mType = "eu";
        }else {
            mType = "bs";
        }
        if (enterType == RangqiuIndexDetailActivity.TYPE_FOOTBALL) {
            addSubscription(apiStores.getFootballIndexList(id, mType),
                    new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            List<BasketballIndexListBean> list = JSONObject.parseArray(data, BasketballIndexListBean.class);
                            mvpView.getDataSuccess(list);
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
        }else {
            addSubscription(apiStores.getBasketballIndexList(CommonAppConfig.getInstance().getToken(), id, mType),
                    new ApiCallback() {
                        @Override
                        public void onSuccess(String data, String msg) {
                            List<BasketballIndexListBean> list = JSONObject.parseArray(data, BasketballIndexListBean.class);
                            mvpView.getDataSuccess(list);
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
}
