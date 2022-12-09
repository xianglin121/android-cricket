package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.user.PayPwdSettingPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.PayPwdSettingView;

public class PayPwdSettingActivity extends MvpActivity<PayPwdSettingPresenter> implements PayPwdSettingView, View.OnClickListener {
    public static final int ENTER_BY_SETTING = 0;
    public static final int ENTER_BY_WITHDRAW = 1;

    public static void forward(Context context, int enterType) {
        Intent intent = new Intent(context, PayPwdSettingActivity.class);
        intent.putExtra("enterType", enterType);
        context.startActivity(intent);
    }

    private int mEnterType;

    private EditText et_pwd;
    private EditText et_pwd_two;
    private TextView tv_phone_prefix;
    private TextView tv_phone;
    private EditText et_code;
    private TextView tv_get_code;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    @Override
    protected PayPwdSettingPresenter createPresenter() {
        return new PayPwdSettingPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_pwd_setting;
    }

    @Override
    protected void initView() {
        mEnterType = getIntent().getIntExtra("enterType", ENTER_BY_SETTING);
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        setTitleText(getString(R.string.title_pay_pwd_setting));

        et_pwd = findViewById(R.id.et_pwd);
        et_pwd_two = findViewById(R.id.et_pwd_two);
        tv_phone_prefix = findViewById(R.id.tv_phone_prefix);
        tv_phone = findViewById(R.id.tv_phone);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);

        tv_get_code.setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
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

        if (CommonAppConfig.getInstance().getUserBean() != null) {
            String mobile = CommonAppConfig.getInstance().getUserBean().getMobile();
            if (!TextUtils.isEmpty(mobile)) {
                if (mobile.contains("-")) {
                    String[] split = mobile.split("-");
                    tv_phone_prefix.setText(split[0]);
                    tv_phone.setText(ToolUtil.changPhoneNumber(split[1]));
                }
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code:
                mvpPresenter.getCode(CommonAppConfig.getInstance().getUserBean().getMobile());
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(et_pwd.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_pwd_hint));
                    return;
                }
                if (TextUtils.isEmpty(et_pwd_two.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_pay_pwd_again));
                    return;
                }
                if (!et_pwd.getText().toString().equals(et_pwd_two.getText().toString())) {
                    ToastUtil.show(getString(R.string.tip_input_pay_pwd_error));
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_verify_code));
                    return;
                }
                mvpPresenter.setPayPwd(et_pwd.getText().toString(), et_code.getText().toString());
                break;
        }
    }

    @Override
    public void setPayPwdSuccess() {
        UserBean userBean = CommonAppConfig.getInstance().getUserBean();
        if (userBean.getIs_defray_pass() == 0) {
            userBean.setIs_defray_pass(1);
            CommonAppConfig.getInstance().saveUserInfo(JSONObject.toJSONString(userBean));
        }
        ToastUtil.show(getString(R.string.tip_set_pay_pwd_success));
        finish();
        if (mEnterType == ENTER_BY_WITHDRAW) {
            WithdrawActivity.forward(this);
        }
    }
}
