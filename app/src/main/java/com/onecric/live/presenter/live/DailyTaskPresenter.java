package com.onecric.live.presenter.live;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.TaskBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.view.live.DailyTaskView;

import java.util.List;


public class DailyTaskPresenter extends BasePresenter<DailyTaskView> {
    public DailyTaskPresenter(DailyTaskView view) {
        attachView(view);
    }

    public void getList() {
        addSubscription(apiStores.getTaskList(CommonAppConfig.getInstance().getToken(), 1),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<TaskBean> list = JSONObject.parseArray(data, TaskBean.class);
                        mvpView.getDataSuccess(list);
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
