package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.longya.live.R;
import com.longya.live.adapter.PlayerStatesAdapter;
import com.longya.live.adapter.PlayerStatesLeftAdapter;
import com.longya.live.custom.ObservableScrollView;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.cricket.ExpertAnalysisPresenter;
import com.longya.live.presenter.cricket.PlayerStatsPresenter;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.cricket.ExpertAnalysisView;
import com.longya.live.view.cricket.PlayerStatsView;

import java.util.Arrays;

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
