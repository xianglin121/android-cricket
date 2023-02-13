package com.onecric.live.fragment.dialog;

import static com.onecric.live.util.UiUtils.getJsonData;
import static com.onecric.live.util.UiUtils.hideKeyboard;
import static com.tencent.imsdk.base.ThreadUtils.runOnUiThread;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.engagelab.privates.core.api.MTCorePrivatesApi;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.activity.WebViewNewActivity;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.AreasModel;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginDialog extends Dialog {
    private BaseActivity mContext;
    private Button btn_login;
    private TextView tvAgreement;
    private TextView tvAuthCode;
    private EditText etVerification;
    private EditText etPhone;
    private CheckBox cbAgreement;
    private EditText etArea;
    private CountryCodePicker ccp;
    private ArrayList<AreasModel.CountryModel> countryList;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    //    private WebView webview;
//    private WebSettings webSettings;
    private boolean isSendCode = false;
    private boolean isSame;
    public OnWebViewListener mWebViewListener;

    public LoginDialog(@NonNull BaseActivity context, int themeResId, OnWebViewListener listener) {
        super(context, themeResId);
        mContext = context;
        mWebViewListener = listener;
        initView();
        initData();
    }

    private void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                count--;
                if (count > 0) {
                    tvAuthCode.setText(count + "s");
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                } else {
                    tvAuthCode.setText(getCodeString);
                    count = TOTAL;
                    if (tvAuthCode != null) {
                        tvAuthCode.setEnabled(true);
                    }
                }
            }
        };
    }

    private void initView() {
        setContentView(R.layout.dialog_login);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        getCodeString = WordUtil.getString(mContext, R.string.get_verify_code);
        tvAgreement = findViewById(R.id.tv_agreement);
        tvAuthCode = findViewById(R.id.tv_auth_code);
        cbAgreement = findViewById(R.id.cb_agreement);
        btn_login = findViewById(R.id.btn_log_in);
        etVerification = findViewById(R.id.et_verification);
        etPhone = findViewById(R.id.et_phone);
        cbAgreement = findViewById(R.id.cb_agreement);
        ccp = findViewById(R.id.ccp);
        etArea = findViewById(R.id.et_area);
        etPhone.requestFocus();
        setAgreementSpannable();
        initListener();
    }


    private void initListener() {
        tvAuthCode.setOnClickListener(v -> {
            String area = etArea.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(area)) {
                ToastUtil.show(mContext.getString(R.string.country));
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.show(mContext.getString(R.string.phone));
                return;
            }
            if (!mContext.isFastDoubleClick()) {
                mWebViewListener.onShow();
            }
        });

        btn_login.setOnClickListener(v -> {
            if (!cbAgreement.isChecked()) {
                ToastUtil.show(mContext.getString(R.string.login_agree_protocol_tip));
                return;
            }

            if (TextUtils.isEmpty(etArea.getText().toString().trim())) {
                ToastUtil.show(mContext.getString(R.string.country));
                return;
            }

            if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                ToastUtil.show(mContext.getString(R.string.phone));
                return;
            }

            if (!isSendCode) {
                ToastUtil.show(mContext.getString(R.string.send_verification_tip));
                return;
            }

            if (TextUtils.isEmpty(etVerification.getText().toString().trim())) {
                ToastUtil.show(mContext.getString(R.string.verification_code));
                return;
            }

            hideKeyboard(etVerification);
            requestLogin();
        });

    }

    private void setAgreementSpannable() {
        String tips = mContext.getString(R.string.login_agreement_info);
        SpannableString spannableString = new SpannableString(tips);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getUser_agreement())) {
                    WebViewNewActivity.forward(mContext, mContext.getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(mContext.getResources().getColor(R.color.c_DC3C23));
                ds.setUnderlineText(false);
            }
        }, 17, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(mContext, mContext.getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(mContext.getResources().getColor(R.color.c_DC3C23));
                ds.setUnderlineText(false);
            }
        }, tips.length() - 14, tips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT);
        tvAgreement.setText(spannableString);
        String json = getJsonData(mContext, "area.json");
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
            requestConfiguration();
        }
    }

    public void showCountryList() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            ccp.setCustomMasterCountries(CommonAppConfig.getInstance().getConfig().getCountryListAbbr());
        }
    }

    @SuppressLint("CheckResult")
    public void requestConfiguration() {
        ApiClient.retrofit().create(ApiStores.class)
                .getDefaultConfiguration(ToolUtil.getCurrentVersionCode(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        CommonAppConfig.getInstance().saveConfig(JSONObject.parseObject(data, ConfigurationBean.class));
                        showCountryList();
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

    private void requestCode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", 3);
        ApiClient.retrofit().create(ApiStores.class)
                .getCode(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        isSendCode = true;
//        if (dialog != null) {dialog.dismiss();}
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFailure(String msg) {
                        tvAuthCode.setEnabled(true);
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        tvAuthCode.setEnabled(true);
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void requestLogin() {
        String prefix = etArea.getText().toString().trim();
        btn_login.setEnabled(false);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", prefix + "-" + etPhone.getText().toString().trim());
        jsonObject.put("code", etVerification.getText().toString().trim());
        jsonObject.put("device_type", "android");
        ApiClient.retrofit().create(ApiStores.class)
                .loginByPwd(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(JSON.parseObject(data).getString("id")) && !TextUtils.isEmpty(JSON.parseObject(data).getString("token"))) {
                            CommonAppConfig.getInstance().saveLoginInfo(JSON.parseObject(data).getString("id"), JSON.parseObject(data).getString("token"), JSONObject.parseObject(data, UserBean.class).getUserSig(), data);
                        }
                        btn_login.setEnabled(true);
//                        updateJgId(JPushInterface.getRegistrationID(mContext));
//                        updateJgId(MTCorePrivatesApi.getRegistrationId(mContext));
                        ToastUtil.show(mContext.getString(R.string.login_success));
                        dismiss();
                        EventBus.getDefault().post(new UpdateLoginTokenEvent());
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                        btn_login.setEnabled(true);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                        btn_login.setEnabled(true);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void updateJgId(String id) {
        ApiClient.retrofit().create(ApiStores.class)
                .updateJgId(CommonAppConfig.getInstance().getToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
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

    public RequestBody getRequestBody(JSONObject jsonObject) {
        MediaType CONTENT_TYPE = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE, jsonObject.toString());
        return requestBody;
    }

    public interface OnWebViewListener {
        void onShow();
    }

    public void passWebView() {
        String area = etArea.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        tvAuthCode.setEnabled(false);
        if (!TextUtils.isEmpty(area) && !TextUtils.isEmpty(area)) {
            requestCode(area + "-" + phone);
        }
    }
}
