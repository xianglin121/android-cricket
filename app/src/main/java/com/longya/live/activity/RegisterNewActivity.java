package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;

import com.longya.live.R;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.login.LoginPresenter;
import com.longya.live.presenter.login.RegisterPresenter;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.login.LoginView;
import com.longya.live.view.login.RegisterView;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/1
 */
public class RegisterNewActivity extends MvpActivity<RegisterPresenter> implements RegisterView {
    public static void forward(Context context) {
        Intent intent = new Intent(context, RegisterNewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_new;
    }

    @Override
    protected void initView() {

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

    @Override
    public void registerSuccess(String msg) {

    }

    @Override
    public void registerFail(String msg) {

    }
}
