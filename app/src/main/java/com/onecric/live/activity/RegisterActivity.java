package com.onecric.live.activity;

import static com.onecric.live.util.DialogUtil.loadingDialog;
import static com.onecric.live.util.UiUtils.getJsonData;
import static com.onecric.live.util.UiUtils.hideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.AreasModel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.RegisterPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.RegisterView;

import java.util.ArrayList;

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private ImageView ivEyePassword;
    private TextView tvAgreement;
    private TextView tvAuthCode;
    private EditText etArea;
    private EditText etPassword;
    private EditText etVerification;
    private EditText etPhone;
    private Button btnRegister;
    private CheckBox cbAgreement;
    private boolean isPwVisitable = false;
    private CountryCodePicker ccp;
    private ArrayList<AreasModel.CountryModel> countryList;
    private boolean isSame;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;

    private WebView webview;
    private WebSettings webSettings;
    private Dialog dialog;
    private boolean isSendCode = false;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_new_new;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        dialog = loadingDialog(RegisterActivity.this);
        tvAgreement = findViewById(R.id.tv_agreement);
        tvAuthCode = findViewById(R.id.tv_auth_code);
        etArea = findViewById(R.id.et_area);
        btnRegister = findViewById(R.id.btn_sign_up);
        ivEyePassword = findViewById(R.id.iv_eye_password);
        cbAgreement = findViewById(R.id.cb_agreement);
        etPassword = findViewById(R.id.et_password);
        etVerification = findViewById(R.id.et_verification);
        etPhone = findViewById(R.id.et_phone);
        ccp = findViewById(R.id.ccp);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_log_in).setOnClickListener(this);
        tvAuthCode.setOnClickListener(this);
        ivEyePassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        etPhone.requestFocus();
        setAgreementSpannable();
        initWebView();
    }

    @Override
    protected void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                count--;
                if (count > 0) {
                    tvAuthCode.setText(count + "s");
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                }else {
                    tvAuthCode.setText(getCodeString);
                    count = TOTAL;
                    if (tvAuthCode != null) {
                        tvAuthCode.setEnabled(true);
                    }
                }
            }
        };
    }

    private void initWebView() {
        webview = (WebView) findViewById(R.id.webview);
        webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 禁用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDefaultTextEncodingName("utf-8") ;
        webview.setBackgroundColor(0); // 设置背景色
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 开启js支持
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(this, "jsBridge");
        webview.loadUrl("file:///android_asset/index.html");
    }

    @JavascriptInterface
    public void getData(String data) {
        webview.postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.setVisibility(View.GONE);
                if(!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    if (jsonObject.getIntValue("ret") == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                String area = etArea.getText().toString().trim();
                                String phone = etPhone.getText().toString().trim();
                                tvAuthCode.setEnabled(false);
                                if (!TextUtils.isEmpty(area) && !TextUtils.isEmpty(area)) {
                                    mvpPresenter.getCode(area + "-" + phone);
                                }
                            }
                        });
                    }
                }
            }
        }, 500);
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        isSendCode = true;
        if (dialog != null) {dialog.dismiss();}
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getDataFail(String msg) {
        if (dialog != null) {dialog.dismiss();}
        tvAuthCode.setEnabled(true);
        ToastUtil.show(msg);
    }

    @Override
    public void registerSuccess(String msg) {
        btnRegister.setEnabled(true);
        ToastUtil.show(msg);
        finish();
    }

    @Override
    public void registerFail(String msg) {
        btnRegister.setEnabled(true);
        ToastUtil.show(msg);
    }

    @Override
    public void showCountryList() {

    }

    @Override
    public void loginIsSuccess(boolean isSuccess) {

    }

    @Override
    public void onClick(View v) {
        String area = etArea.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_log_in:
//                LoginActivity.forward(this);
                break;
            case R.id.iv_eye_password:
                if (isPwVisitable) {
                    isPwVisitable = false;
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivEyePassword.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    isPwVisitable = true;
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivEyePassword.setImageResource(R.mipmap.ic_eye_open);
                }
                etPassword.setSelection(etPassword.getText().toString().length());
                break;
            case R.id.tv_auth_code:
                if (TextUtils.isEmpty(area)) {
                    ToastUtil.show(getString(R.string.country));
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show(getString(R.string.phone));
                    return;
                }
                if (!isFastDoubleClick()) {
                    webview.setVisibility(View.VISIBLE);
                    webview.loadUrl("javascript:ab()");
                }
                break;
            case R.id.btn_sign_up:
                if(!cbAgreement.isChecked()){
                    ToastUtil.show(WordUtil.getString(this, R.string.login_agree_protocol_tip));
                    return;
                }

                if(TextUtils.isEmpty(etArea.getText().toString().trim())){
                    ToastUtil.show(getString(R.string.country));
                    return;
                }

                if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
                    ToastUtil.show(getString(R.string.phone));
                    return;
                }

                if(!isSendCode){
                    ToastUtil.show(getString(R.string.send_verification_tip));
                    return;
                }

                if(TextUtils.isEmpty(etVerification.getText().toString().trim())){
                    ToastUtil.show(getString(R.string.verification_code));
                    return;
                }

                if(TextUtils.isEmpty(etPassword.getText().toString().trim())){
                    ToastUtil.show(getString(R.string.login_password));
                    return;
                }

                btnRegister.setEnabled(false);
                mvpPresenter.register(area + "-" + phone, etVerification.getText().toString().trim(), etPassword.getText().toString().trim());
                break;
        }
    }

    private void setAgreementSpannable(){
        String tips = getString(R.string.login_agreement_info);
        SpannableString spannableString = new SpannableString(tips);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getUser_agreement())) {
                    WebViewNewActivity.forward(RegisterActivity.this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.c_DC3C23));
                ds.setUnderlineText(false);
            }
        },17, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(RegisterActivity.this, getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.c_DC3C23));
                ds.setUnderlineText(false);
            }
        },tips.length() - 14, tips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT);
        tvAgreement.setText(spannableString);
        //选择国家
        ccp.setOnCountryChangeListener(() ->{
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
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            ccp.setCustomMasterCountries(CommonAppConfig.getInstance().getConfig().getCountryListAbbr());
        }else{
            ccp.setCustomMasterCountries("IN");
        }

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
                if(!TextUtils.isEmpty(editable.toString().trim()) && !isSame){
                    String code = editable.toString().trim();
                    for(AreasModel.CountryModel model:countryList){
                        if(model.getTel().equals(code)){
                            ccp.setCountryForNameCode(model.getShortName());
                            return;
                        }
                    }
                    etArea.setSelection(code.length());
                }
            }
        });
    }
}
