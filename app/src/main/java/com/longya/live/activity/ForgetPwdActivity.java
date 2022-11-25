package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.adapter.PhonePrefixAdapter;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.login.ForgetPwdPresenter;
import com.longya.live.presenter.login.RegisterPresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.login.ForgetPwdView;
import com.longya.live.view.login.RegisterView;

public class ForgetPwdActivity extends MvpActivity<ForgetPwdPresenter> implements ForgetPwdView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_phone_prefix;
    private EditText et_phone;
    private EditText et_code;
    private TextView tv_get_code;
    private EditText et_pwd_one;
    private EditText et_pwd_two;
    private ImageView iv_toggle_one;
    private ImageView iv_toggle_two;
    private TextView btn_commit;

    private LayoutInflater mInflater;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    private WebView webview;
    private WebSettings webSettings;

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        mInflater = LayoutInflater.from(this);
        tv_phone_prefix = findViewById(R.id.tv_phone_prefix);
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        et_pwd_one = findViewById(R.id.et_pwd_one);
        et_pwd_two = findViewById(R.id.et_pwd_two);
        iv_toggle_one = findViewById(R.id.iv_toggle_one);
        iv_toggle_two = findViewById(R.id.iv_toggle_two);
        btn_commit = findViewById(R.id.btn_commit);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.ll_phone_prefix).setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
        iv_toggle_one.setOnClickListener(this);
        iv_toggle_two.setOnClickListener(this);
        btn_commit.setOnClickListener(this);

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
                                String phone = et_phone.getText().toString();
                                String prefix= tv_phone_prefix.getText().toString();
                                btn_commit.setEnabled(false);
                                mvpPresenter.changePwd(prefix + "-" + phone, et_code.getText().toString(), et_pwd_one.getText().toString());
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
    public void forgetPwdSuccess(String msg) {
        btn_commit.setEnabled(true);
        ToastUtil.show(getString(R.string.tip_change_pwd_success));
        finish();
    }

    @Override
    public void forgetPwdFail(String msg) {
        btn_commit.setEnabled(true);
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        String prefix= tv_phone_prefix.getText().toString();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_phone_prefix:
                choosePhonePrefix();
                break;
            case R.id.iv_toggle_one:
                iv_toggle_one.setSelected(!iv_toggle_one.isSelected());
                if (iv_toggle_one.isSelected()) {
                    et_pwd_one.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_pwd_one.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pwd_one.setSelection(et_pwd_one.getText().toString().length());
                //解决英文时hint字体会发现变化
                et_pwd_one.setTypeface(Typeface.DEFAULT);
                break;
            case R.id.iv_toggle_two:
                iv_toggle_two.setSelected(!iv_toggle_two.isSelected());
                if (iv_toggle_two.isSelected()) {
                    et_pwd_two.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_pwd_two.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pwd_two.setSelection(et_pwd_two.getText().toString().length());
                //解决英文时hint字体会发现变化
                et_pwd_two.setTypeface(Typeface.DEFAULT);
                break;
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_phone_number));
                    return;
                }
                if (!isFastDoubleClick()) {
                    tv_get_code.setEnabled(false);
                    mvpPresenter.getCode(prefix + "-" + phone);
                }
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_phone_number));
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_verify_code));
                    return;
                }
                if (TextUtils.isEmpty(et_pwd_one.getText().toString())) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_pwd_hint));
                    return;
                }
                if (TextUtils.isEmpty(et_pwd_two.getText().toString())) {
                    ToastUtil.show(WordUtil.getString(this, R.string.please_input_confirm_pwd_hint));
                    return;
                }
                if (!et_pwd_one.getText().toString().equals(et_pwd_two.getText().toString())) {
                    ToastUtil.show(WordUtil.getString(this, R.string.register_pwd_error));
                    return;
                }
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl("javascript:ab()");
                break;
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
}
