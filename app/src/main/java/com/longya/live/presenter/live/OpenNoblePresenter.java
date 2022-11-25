package com.longya.live.presenter.live;

import com.longya.live.CommonAppConfig;
import com.longya.live.presenter.BasePresenter;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.view.live.LiveAnchorView;
import com.longya.live.view.live.OpenNobleView;


public class OpenNoblePresenter extends BasePresenter<OpenNobleView> {
    public OpenNoblePresenter(OpenNobleView view) {
        attachView(view);
    }

    public void buyNoble(int id, int anchorId) {
        addSubscription(apiStores.buyNoble(CommonAppConfig.getInstance().getToken(), id, anchorId),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(null);
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
