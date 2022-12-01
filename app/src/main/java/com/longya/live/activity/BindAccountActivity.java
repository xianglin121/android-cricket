package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.event.BindAccountSuccessEvent;
import com.longya.live.model.AccountBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.user.BindAccountPresenter;
import com.longya.live.presenter.user.PayPwdSettingPresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.ToolUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.user.BindAccountView;
import com.longya.live.view.user.PayPwdSettingView;

import org.greenrobot.eventbus.EventBus;

public class BindAccountActivity extends MvpActivity<BindAccountPresenter> implements BindAccountView, View.OnClickListener {
    public static final int TYPE_BIND = 0;
    public static final int TYPE_CHANGE = 1;

    public static void forward(Context context, int type, AccountBean bean) {
        Intent intent = new Intent(context, BindAccountActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("data", bean);
        context.startActivity(intent);
    }

    private int mType;
    private AccountBean mAccountBean;

    private EditText et_bank;
    private EditText et_account;
    private EditText et_name;
    private EditText et_idcard;
    private TextView tv_phone;
    private EditText et_code;
    private TextView tv_get_code;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    @Override
    protected BindAccountPresenter createPresenter() {
        return new BindAccountPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_account;
    }

    @Override
    protected void initView() {
        mType = getIntent().getIntExtra("type", TYPE_BIND);
        if (getIntent().getSerializableExtra("data") != null) {
            mAccountBean = (AccountBean) getIntent().getSerializableExtra("data");
        }
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        if (mType == TYPE_BIND) {
            setTitleText(getString(R.string.title_bind_account));
        }else {
            setTitleText(getString(R.string.title_change_account));
        }

        et_bank = findViewById(R.id.et_bank);
        et_account = findViewById(R.id.et_account);
        et_name = findViewById(R.id.et_name);
        et_idcard = findViewById(R.id.et_idcard);
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
                    tv_phone.setText(ToolUtil.changPhoneNumber(split[1]));
                }
            }
        }
        if (mAccountBean != null) {
            if (!TextUtils.isEmpty(mAccountBean.getBank_name())) {
                et_bank.setText(mAccountBean.getBank_name());
            }
            if (!TextUtils.isEmpty(mAccountBean.getCard_number())) {
                et_account.setText(mAccountBean.getCard_number());
            }
            if (!TextUtils.isEmpty(mAccountBean.getName())) {
                et_name.setText(mAccountBean.getName());
            }
            if (!TextUtils.isEmpty(mAccountBean.getId_card())) {
                et_idcard.setText(mAccountBean.getId_card());
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void bindAccountSuccess() {
        ToastUtil.show(getString(R.string.binding_succeeded));
        EventBus.getDefault().post(new BindAccountSuccessEvent());
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code:
                mvpPresenter.getCode(CommonAppConfig.getInstance().getUserBean().getMobile());
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(et_bank.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_bank_name));
                    return;
                }
                if (TextUtils.isEmpty(et_account.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_bank_card_number));
                    return;
                }
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_actual_name));
                    return;
                }
                if (TextUtils.isEmpty(et_idcard.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_id_card));
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_verify_code));
                    return;
                }
                mvpPresenter.bindAccount(et_bank.getText().toString(), et_account.getText().toString(), et_name.getText().toString(), et_idcard.getText().toString(), et_code.getText().toString());
                break;
        }
    }
}
