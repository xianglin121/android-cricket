package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;

import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.ForgetPwdPresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.ForgetPwdView;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/1
 */
public class ForgetPwdNewActivity extends MvpActivity<ForgetPwdPresenter> implements ForgetPwdView {
    public static void forward(Context context) {
        Intent intent = new Intent(context, ForgetPwdNewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pwd_new;
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
    public void forgetPwdSuccess(String msg) {

    }

    @Override
    public void forgetPwdFail(String msg) {

    }

    @Override
    public void showCountryList() {

    }
}
