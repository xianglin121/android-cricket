package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;

import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.ExpertAnalysisPresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.ExpertAnalysisView;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/6
 */
public class ExpertAnalysisActivity extends MvpActivity<ExpertAnalysisPresenter> implements ExpertAnalysisView {
    public static void forward(Context context) {
        Intent intent = new Intent(context, ExpertAnalysisActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ExpertAnalysisPresenter createPresenter() {
        return new ExpertAnalysisPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expert_analysis;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.expert_analysis));
    }

    @Override
    protected void initData() {
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
