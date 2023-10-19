package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.RegisterPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.RegisterView;

public class OneSetPwdActivity extends MvpActivity<RegisterPresenter> implements RegisterView, View.OnClickListener {
    private final int FROM_SIGN_UP = 2202;

    public static void forward(Context context, String account) {
        Intent intent = new Intent(context, OneSetPwdActivity.class);
        if(!TextUtils.isEmpty(account)){
            intent.putExtra("account",account);
        }
        context.startActivity(intent);
    }

    private EditText et_password;
    private CheckBox cb_agreement,cb_show_password;
    private TextView tv_account,tvAgreement,tv_sign_up,tv_login,tv_title;
    private LinearLayout ll_title;
    private String account;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initView() {
        et_password = findViewById(R.id.et_password);
        cb_agreement = findViewById(R.id.cb_agreement);
        cb_show_password = findViewById(R.id.cb_show_password);
        tv_account = findViewById(R.id.tv_account);
        tvAgreement = findViewById(R.id.tv_agreement);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_login = findViewById(R.id.tv_login);
        tv_title = findViewById(R.id.tv_title);
        ll_title = findViewById(R.id.ll_title);
        tv_sign_up.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        account = getIntent().getStringExtra("account");
        if(TextUtils.isEmpty(account)){
            finish();
        }
        tv_account.setText(account);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ll_title.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.sign_up));

        setAgreementSpannable();
        cb_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    private void setAgreementSpannable() {
        String tip ="I have read and agree to the teams of use";
        SpannableString spannableString = new SpannableString(tip);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(OneSetPwdActivity.this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.c_DC3C23));
                ds.setUnderlineText(false);
            }
        }, tip.length()-12, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT);
        tvAgreement.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sign_up:
                if (!cb_agreement.isChecked()) {
                    ToastUtil.show(getString(R.string.login_agree_protocol_tip));
                    return;
                }
                String pwd = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.show(getString(R.string.password));
                    return;
                }

                //正则 6-12位数字和小写字母组合
                String regexST = "^(?![0-9]+$)(?![a-z]+$)[0-9a-z]{6,12}$";
                if(!pwd.matches(regexST)){
                    ToastUtil.show(getString(R.string.password_invalid));
                    return;
                }
                tv_sign_up.setEnabled(false);
                showLoadingDialog();
                mvpPresenter.oneRegister(account,et_password.getText().toString().trim());
                break;
            case R.id.tv_login:
                OneLogInActivity.forward(this);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess(String msg) {
        tv_sign_up.setEnabled(true);
        dismissLoadingDialog();
        ToastUtil.show(msg);
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.COMPLETE_REGISTRATION, null,null);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "sign_up");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
        LoginAccessActivity.forward(this);
    }

    @Override
    public void registerFail(String msg) {
        tv_sign_up.setEnabled(true);
        dismissLoadingDialog();
        ToastUtil.show(msg);
    }

    @Override
    public void showCountryList() {

    }

    @Override
    public void loginIsSuccess(boolean isSuccess) {

    }
}
