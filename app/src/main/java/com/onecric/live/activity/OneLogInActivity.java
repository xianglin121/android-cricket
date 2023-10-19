package com.onecric.live.activity;

import static com.onecric.live.AppManager.mContext;
import static com.onecric.live.util.UiUtils.getJsonData;
import static com.onecric.live.util.UiUtils.hideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.engagelab.privates.core.api.MTCorePrivatesApi;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.AreasModel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.LoginPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.LoginView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class OneLogInActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnClickListener{
    public static final String LOG_TAG = "OneLogInActivity";
    public static void forward(Context context) {
        Intent intent = new Intent(context, OneLogInActivity.class);
        context.startActivity(intent);
    }

//    private EditText et_account;
    private EditText et_password;
    private EditText etArea;
    private EditText etPhone;
    private CheckBox cbAgreement;
    private TextView tvAgreement,tv_login,tv_sign_up;
    private ImageView ivEyePassword;
    private boolean isPwVisitable = false;
    private CountryCodePicker ccp;
    private ArrayList<AreasModel.CountryModel> countryList;
    private boolean isSame;
    private FirebaseAnalytics mFirebaseAnalytics;

    //facebook
    private static final int RC_FACEBOOK_SIGN_IN = 1101;
//    private CallbackManager callbackManager;

    // Google
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_GOOGLE_SIGN_IN = 1102;
    private final int FROM_LOGIN_IN = 2204;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (!cbAgreement.isChecked()) {
                    ToastUtil.show(getString(R.string.login_agree_protocol_tip));
                    return;
                }

                String area = etArea.getText().toString().trim();
                if (TextUtils.isEmpty(area)) {
                    ToastUtil.show(getString(R.string.country));
                    return;
                }
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show(getString(R.string.mobile_number));
                    return;
                }

                /*if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                    ToastUtil.show(getString(R.string.password));
                    return;
                }*/

                hideKeyboard(etPhone);
                tv_login.setEnabled(false);
//                mvpPresenter.oneLoginByPwd(area + "-" + phone,et_password.getText().toString().trim(), SpUtil.getInstance().getStringValue(REGISTRATION_TOKEN));
                mvpPresenter.getCode(area + "-" + phone);
                finish();
                OneVerificationActivity.forward(OneLogInActivity.this,area + "-" + phone,FROM_LOGIN_IN);
                break;
            case R.id.tv_sign_up:
                OneSignUpActivity.forward(OneLogInActivity.this);
                break;
            case R.id.tv_forget_pwd:
                OneForgetPwdActivity.forward(this,etArea.getText().toString().trim() + "-" + etPhone.getText().toString().trim());
                break;
            case R.id.iv_eye_password:
                if (isPwVisitable) {
                    isPwVisitable = false;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivEyePassword.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    isPwVisitable = true;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivEyePassword.setImageResource(R.mipmap.ic_eye_open);
                }
                break;
            case R.id.tv_sign_facebook:
//                signFacebook();
                break;
            case R.id.tv_sign_google:
                tv_login.setEnabled(false);
                signGoogle();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void initView() {
        etArea = findViewById(R.id.et_area);
        etPhone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        cbAgreement = findViewById(R.id.cb_agreement);
        tvAgreement = findViewById(R.id.tv_agreement);
        tv_login = findViewById(R.id.tv_login);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        ivEyePassword = findViewById(R.id.iv_eye_password);
        ccp = findViewById(R.id.ccp);
        findViewById(R.id.tv_sign_google).setOnClickListener(this);
        findViewById(R.id.tv_sign_facebook).setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        ivEyePassword.setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setAgreementSpannable();
        initOther();
    }

    private void initOther(){
/*        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().setAuthType(AUTH_TYPE);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        //登录成功，LoginResult 参数获得新的 AccessTokenn给服务端，服务端调Facebook的token验证接口验证token是否有效
                        Log.e("facebook","loginResult:-> "+loginResult.toString());

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestIdToken(mContext.getString(R.string.server_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void signOutGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getApplicationContext(), "signOut Complete!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signFacebook(){
        //loading
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }
    @Override
    protected void initData() {
    }

    private void setAgreementSpannable() {
        String tips = getString(R.string.login_agreement_info);
        SpannableString spannableString = new SpannableString(tips);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getUser_agreement())) {
                    WebViewNewActivity.forward(OneLogInActivity.this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.c_4E4E4E));
                ds.setUnderlineText(true);
            }
        }, 17, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(OneLogInActivity.this, getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.c_4E4E4E));
                ds.setUnderlineText(true);
            }
        }, 34, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT);
        tvAgreement.setText(spannableString);

        String json = getJsonData(this, "area.json");
        AreasModel areasModel = new Gson().fromJson(json, AreasModel.class);
        countryList = (ArrayList<AreasModel.CountryModel>) areasModel.getData();
        //国家与输入框动态响应
        etArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString().trim()) && !isSame) {
                    String code = editable.toString().trim();
                    for (AreasModel.CountryModel model : countryList) {
                        if (model.getTel().equals(code)) {
                            ccp.setCountryForNameCode(model.getShortName());
                            return;
                        }
                    }
                    etArea.setSelection(code.length());
                }
            }
        });
        //选择国家
        ccp.setOnCountryChangeListener(() -> {
            isSame = true;
            etArea.setText(ccp.getSelectedCountryCode());
            etArea.setSelection(ccp.getSelectedCountryCode().length());
            isSame = false;
        });
        ccp.setDialogEventsListener(new CountryCodePicker.DialogEventsListener() {
            @Override
            public void onCcpDialogOpen(Dialog dialog) {

            }

            @Override
            public void onCcpDialogDismiss(DialogInterface dialogInterface) {
                hideKeyboard(etArea);
            }

            @Override
            public void onCcpDialogCancel(DialogInterface dialogInterface) {

            }
        });
        ccp.setCustomMasterCountries("IN");
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            showCountryList();
        } else {
            mvpPresenter.getConfiguration(ToolUtil.getCurrentVersionCode(this));
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginIsSuccess(boolean isSuccess) {
        tv_login.setEnabled(true);
        dismissLoadingDialog();
        if(isSuccess){
            //Appsflyer事件：登录成功
//            Map<String, Object> eventValues = new HashMap<String, Object>();
//            eventValues.put(AFInAppEventParameterName.CONTENT_ID, <ITEM_SKU>);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.LOGIN,
                    null,
                    new AppsFlyerRequestListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(LOG_TAG, "Event sent successfully");
                        }
                        @Override
                        public void onError(int i, @NonNull String s) {
                            Log.d(LOG_TAG, "Event failed to be sent:\n" +
                                    "Error code: " + i + "\n"
                                    + "Error description: " + s);
                        }
                    });


            EventBus.getDefault().post(new UpdateLoginTokenEvent());
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.METHOD, "login");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
            mvpPresenter.updateJgId(MTCorePrivatesApi.getRegistrationId(mContext));
            ToastUtil.show(mContext.getString(R.string.login_success));
            finish();
        }
    }

    @Override
    public void showCountryList() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            ccp.setCustomMasterCountries(CommonAppConfig.getInstance().getConfig().getCountryListAbbr());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_FACEBOOK_SIGN_IN){
            //将登录结果传递到 LoginManager
//            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == RC_GOOGLE_SIGN_IN) {
            showLoadingDialog();
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> completedTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                mvpPresenter.oneLoginGmail(account.getId(),account.getDisplayName(),account.getPhotoUrl().toString(),account.getIdToken(),account.getEmail());
            } catch (ApiException e) {
                dismissLoadingDialog();
                tv_login.setEnabled(true);
                if(e.getStatusCode() == 12500){
                    ToastUtil.show(getString(R.string.please_install_google));
                }else{
                    ToastUtil.show(GoogleSignInStatusCodes.getStatusCodeString(e.getStatusCode()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
