package com.onecric.live.presenter.match;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.match.MatchView;

public class MatchPresenter extends BasePresenter<MatchView> {
    public MatchPresenter(MatchView view) {
        attachView(view);
    }

    public void doTask() {
        addSubscription(apiStores.doTask(CommonAppConfig.getInstance().getToken(), 8, Integer.parseInt(CommonAppConfig.getInstance().getUid())),
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
