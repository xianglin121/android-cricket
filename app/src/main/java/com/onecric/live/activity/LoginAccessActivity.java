package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;

import com.onecric.live.R;
import com.onecric.live.view.BaseActivity;

public class LoginAccessActivity extends BaseActivity{
    public static void forward(Context context) {
        Intent intent = new Intent(context, LoginAccessActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_access;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_sign_up).setOnClickListener(v -> {
            OneSignUpActivity.forward(this);
        });

        findViewById(R.id.btn_log_in).setOnClickListener(v -> {
            OneLogInActivity.forward(this);
        });

        findViewById(R.id.tv_later).setOnClickListener(v -> {
            MainActivity.forward(this);
        });
    }

    @Override
    protected void initData() {

    }

}