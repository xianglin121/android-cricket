package com.onecric.live.activity;

import static com.onecric.live.util.UiUtils.getJsonData;
import static com.onecric.live.util.UiUtils.hideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.AreasModel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.RegisterPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.RegisterView;

import java.util.ArrayList;

public class OneSignUpActivity extends MvpActivity<RegisterPresenter> implements RegisterView, View.OnClickListener{

    public static void forward(Context context) {
        Intent intent = new Intent(context, OneSignUpActivity.class);
        context.startActivity(intent);
    }

    private final int FROM_SIGN_UP = 2202;
    private TextView tvAgreement;
    private CheckBox cbAgreement;
    private EditText etArea;
    private EditText etPhone;
    private CountryCodePicker ccp;
    private ArrayList<AreasModel.CountryModel> countryList;
    private boolean isSame;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sign_in:
                //注册
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
                    ToastUtil.show("Mobile Number");
                    return;
                }

                hideKeyboard(etPhone);
                //发验证码
                showLoadingDialog();
                mvpPresenter.getCode(area + "-" + phone);
                break;
            case R.id.tv_sign_facebook:
                // 脸书

                break;
            case R.id.tv_sign_google:
                // 谷歌邮箱

                break;
            default:;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView() {
        tvAgreement = findViewById(R.id.tv_agreement);
        cbAgreement = findViewById(R.id.cb_agreement);
        ccp = findViewById(R.id.ccp);
        etArea = findViewById(R.id.et_area);
        etPhone = findViewById(R.id.et_phone);
        findViewById(R.id.tv_sign_in).setOnClickListener(this);
        findViewById(R.id.tv_sign_google).setOnClickListener(this);
        findViewById(R.id.tv_sign_facebook).setOnClickListener(this);
        setAgreementSpannable();
        etPhone.requestFocus();
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
                    WebViewNewActivity.forward(OneSignUpActivity.this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
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
                    WebViewNewActivity.forward(OneSignUpActivity.this, getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
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
        dismissLoadingDialog();
        OneVerificationActivity.forward(OneSignUpActivity.this,etArea.getText().toString().trim() + "-" + etPhone.getText().toString().trim(),FROM_SIGN_UP);
    }

    @Override
    public void getDataFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.show(msg);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess(String msg) {

    }

    @Override
    public void registerFail(String msg) {

    }

    @Override
    public void showCountryList() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            ccp.setCustomMasterCountries(CommonAppConfig.getInstance().getConfig().getCountryListAbbr());
        }
    }

}
