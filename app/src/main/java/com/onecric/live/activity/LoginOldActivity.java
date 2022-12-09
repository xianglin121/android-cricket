package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.PhonePrefixAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.LoginPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.LoginView;

import cn.jpush.android.api.JPushInterface;

public class LoginOldActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    public static void forward(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void forward2(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private TextView tv_phone;
    private View line_one;
    private TextView tv_pwd;
    private View line_two;
    private EditText et_phone;
    private ConstraintLayout cl_code;
    private EditText et_code;
    private TextView tv_get_code;
    private ConstraintLayout cl_pwd;
    private EditText et_pwd;
    private ImageView iv_toggle;
    private TextView tv_phone_prefix;
    private TextView btn_login;
    private ImageView iv_agree;

    private LayoutInflater mInflater;
    private boolean mIsPwdLogin;//是否密码登录

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    private WebView webview;
    private WebSettings webSettings;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        mInflater = LayoutInflater.from(this);
        tv_phone = findViewById(R.id.tv_phone);
        line_one = findViewById(R.id.line_one);
        tv_pwd = findViewById(R.id.tv_pwd);
        line_two = findViewById(R.id.line_two);
        et_phone = findViewById(R.id.et_phone);
        cl_code = findViewById(R.id.cl_code);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        cl_pwd = findViewById(R.id.cl_pwd);
        et_pwd = findViewById(R.id.et_pwd);
        iv_toggle = findViewById(R.id.iv_toggle);
        tv_phone_prefix = findViewById(R.id.tv_phone_prefix);
        btn_login = findViewById(R.id.btn_login);
        iv_agree = findViewById(R.id.iv_agree);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.ll_phone).setOnClickListener(this);
        findViewById(R.id.ll_pwd).setOnClickListener(this);
        findViewById(R.id.tv_get_code).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        findViewById(R.id.tv_protocol).setOnClickListener(this);
        findViewById(R.id.tv_privacy).setOnClickListener(this);
        iv_toggle.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        findViewById(R.id.ll_phone_prefix).setOnClickListener(this);
        iv_agree.setOnClickListener(this);

        iv_agree.setSelected(true);

        initWebView();
    }

    @Override
    protected void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                count--;
                if (count > 0) {
                    tv_get_code.setText(getCodeAgainString + "(" + count + "s)");
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                }else {
                    tv_get_code.setText(getCodeString);
                    count = TOTAL;
                    if (tv_get_code != null) {
                        tv_get_code.setEnabled(true);
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
                                login();
                            }
                        });
                    }
                }
            }
        }, 500);
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getDataFail(String msg) {
        tv_get_code.setEnabled(true);
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_phone:
                if (!mIsPwdLogin) {
                    return;
                }
                mIsPwdLogin = false;
                tv_phone.setTextColor(getResources().getColor(R.color.c_DC3C23));
                line_one.setVisibility(View.VISIBLE);
                tv_pwd.setTextColor(getResources().getColor(R.color.c_b3ffffff));
                line_two.setVisibility(View.INVISIBLE);
                cl_code.setVisibility(View.VISIBLE);
                cl_pwd.setVisibility(View.GONE);
                break;
            case R.id.ll_pwd:
                if (mIsPwdLogin) {
                    return;
                }
                mIsPwdLogin = true;
                tv_phone.setTextColor(getResources().getColor(R.color.c_b3ffffff));
                line_one.setVisibility(View.INVISIBLE);
                tv_pwd.setTextColor(getResources().getColor(R.color.c_DC3C23));
                line_two.setVisibility(View.VISIBLE);
                cl_code.setVisibility(View.GONE);
                cl_pwd.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_toggle:
                iv_toggle.setSelected(!iv_toggle.isSelected());
                if (iv_toggle.isSelected()) {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pwd.setSelection(et_pwd.getText().toString().length());
                //解决英文时hint字体会发现变化
                et_pwd.setTypeface(Typeface.DEFAULT);
                break;
            case R.id.ll_phone_prefix:
                choosePhonePrefix();
                break;
            case R.id.tv_register:
                RegisterActivity.forward(this);
                break;
            case R.id.tv_forget_pwd:
                ForgetPwdActivity.forward(this);
                break;
            case R.id.iv_agree:
                iv_agree.setSelected(!iv_agree.isSelected());
                break;
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_phone_number));
                    return;
                }
                tv_get_code.setEnabled(false);
                mvpPresenter.getCode(tv_phone_prefix.getText().toString() + "-" + et_phone.getText().toString());
                break;
            case R.id.btn_login:
                if (mIsPwdLogin) {
                    if (!iv_agree.isSelected()) {
                        ToastUtil.show(WordUtil.getString(this, R.string.login_agree_protocol_tip));
                        return;
                    }
                    if (TextUtils.isEmpty(et_phone.getText().toString())) {
                        ToastUtil.show(WordUtil.getString(this, R.string.please_input_phone_number));
                        return;
                    }
                    if (TextUtils.isEmpty(et_pwd.getText().toString())) {
                        ToastUtil.show(WordUtil.getString(this, R.string.please_input_pwd_hint));
                        return;
                    }
                    hideKeyboard(et_pwd);
                }else {
                    if (!iv_agree.isSelected()) {
                        ToastUtil.show(WordUtil.getString(this, R.string.login_agree_protocol_tip));
                        return;
                    }
                    if (TextUtils.isEmpty(et_phone.getText().toString())) {
                        ToastUtil.show(WordUtil.getString(this, R.string.please_input_phone_number));
                        return;
                    }
                    if (TextUtils.isEmpty(et_code.getText().toString())) {
                        ToastUtil.show(WordUtil.getString(this, R.string.please_input_verify_code));
                        return;
                    }
                    hideKeyboard(et_code);
                }
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl("javascript:ab()");
                break;
            case R.id.tv_protocol:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getUser_agreement())) {
                    WebViewNewActivity.forward(this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
                break;
            case R.id.tv_privacy:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(this, getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
                }
                break;
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void login() {
        String prefix= tv_phone_prefix.getText().toString();
        if (mIsPwdLogin) {
            btn_login.setEnabled(false);
            mvpPresenter.loginByPwd(prefix + "-" + et_phone.getText().toString(), et_pwd.getText().toString());
        }else {
            btn_login.setEnabled(false);
            mvpPresenter.loginByCode(prefix + "-" + et_phone.getText().toString(), et_code.getText().toString());
        }
    }

    //选择手机前缀
    private void choosePhonePrefix() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            View v = mInflater.inflate(R.layout.view_phone_prefix_pop, null);
            PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_pop_phone_prefix));
            RecyclerView rv_prefix = v.findViewById(R.id.rv_prefix);
            PhonePrefixAdapter prefixAdapter = new PhonePrefixAdapter(R.layout.item_phone_prefix_pop, CommonAppConfig.getInstance().getConfig().getCountryCode());
            prefixAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    tv_phone_prefix.setText(prefixAdapter.getItem(position).getCode());
                    popupWindow.dismiss();
                }
            });
            rv_prefix.setLayoutManager(new LinearLayoutManager(this));
            rv_prefix.setAdapter(prefixAdapter);
            popupWindow.showAsDropDown(tv_phone_prefix);
        }
    }

    @Override
    public void loginIsSuccess(boolean isSuccess) {
        btn_login.setEnabled(true);
        if (isSuccess) {
            mvpPresenter.updateJgId(JPushInterface.getRegistrationID(this));
            ToastUtil.show(WordUtil.getString(this, R.string.login_success));
            MainActivity.loginForward(this);
        }
    }

    @Override
    public void showCountryList() {

    }
}
