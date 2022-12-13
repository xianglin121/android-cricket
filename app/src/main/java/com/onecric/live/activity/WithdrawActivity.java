package com.onecric.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.event.BindAccountSuccessEvent;
import com.onecric.live.model.AccountBean;
import com.onecric.live.presenter.user.WithdrawPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.WithdrawView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

public class WithdrawActivity extends MvpActivity<WithdrawPresenter> implements WithdrawView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_diamond_count;
    private TextView tv_account;
    private TextView tv_operate;
    private TextView tv_withdraw_amount;
    private TextView tv_pwd;
    private EditText et_code;
    private TextView tv_get_code;
    private TextView tv_radio;
    private TextView tv_amount;
    private TextView tv_actual_amount;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    private AccountBean mAccountBean;
    private int mIsPayPwd;//是否有设置支付密码
    private String mWithdrawBalance;//可提现余额
    private double mWithdrawalRadio;//提现比例

    @Override
    protected WithdrawPresenter createPresenter() {
        return new WithdrawPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);

        EventBus.getDefault().register(this);
        setTitleText(getString(R.string.user_withdraw));

        TextView right_title = findViewById(R.id.right_title);
        right_title.setText(getString(R.string.charge_detail));
        right_title.setTextSize(17);
        right_title.setTextColor(getResources().getColor(R.color.c_E3AC72));

        TextView tv_tip = findViewById(R.id.tv_tip);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(tv_tip.getText().toString());
        stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_5D70E3)), 7, 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_tip.setText(stringBuilder);

        tv_diamond_count = findViewById(R.id.tv_diamond_count);
        tv_account = findViewById(R.id.tv_account);
        tv_operate = findViewById(R.id.tv_operate);
        tv_withdraw_amount = findViewById(R.id.tv_withdraw_amount);
        tv_pwd = findViewById(R.id.tv_pwd);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        tv_radio = findViewById(R.id.tv_radio);
        tv_amount = findViewById(R.id.tv_amount);
        tv_actual_amount = findViewById(R.id.tv_actual_amount);

        tv_withdraw_amount.setOnClickListener(this);
        tv_pwd.setOnClickListener(this);
        tv_operate.setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
        right_title.setOnClickListener(this);
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
        mvpPresenter.getBankInfo();
        mvpPresenter.getBalanceInfo();
    }

    @Override
    public void getDataSuccess(AccountBean bean) {
        if (bean != null) {
            mAccountBean = bean;
            if (!TextUtils.isEmpty(bean.getCard_number())) {
                tv_account.setVisibility(View.VISIBLE);
                tv_operate.setText(getString(R.string.change_bank_account));
                tv_account.setText(ToolUtil.changAccountNumber(bean.getCard_number()));
            }else {
                tv_account.setVisibility(View.GONE);
                tv_operate.setText(getString(R.string.bind_bank_account));
            }
        }
    }

    @Override
    public void getCodeSuccess() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getBalanceSuccess(int isPayPwd, String balance, double withdrawalRadio) {
        mIsPayPwd = isPayPwd;
        if (!TextUtils.isEmpty(balance)) {
            mWithdrawBalance = balance;
            tv_diamond_count.setText(balance);
        }
        mWithdrawalRadio = withdrawalRadio;
        int radio = new BigDecimal(withdrawalRadio).multiply(new BigDecimal(100)).intValue();
        tv_radio.setText(radio + "%");
    }

    @Override
    public void applyWithdrawSuccess() {
        ToastUtil.show(getString(R.string.tip_apply_withdraw_success));
        finish();
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_title:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    MyWalletActivity.forward(this, 3);
                }
                break;
            case R.id.tv_operate:
                if (mAccountBean != null) {
                    if (TextUtils.isEmpty(mAccountBean.getCard_number())) {
                        BindAccountActivity.forward(this, BindAccountActivity.TYPE_BIND, mAccountBean);
                    }else {
                        AccountInfoActivity.forward(this, mAccountBean);
                    }
                }
                break;
            case R.id.tv_get_code:
                mvpPresenter.getCode(CommonAppConfig.getInstance().getUserBean().getMobile());
                break;
            case R.id.tv_withdraw_amount:
                DialogUtil.showSimpleInputDialog(this, getString(R.string.withdraw_amount), "0123456789", new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(content)) {
                            BigDecimal balance = new BigDecimal(content);
                            BigDecimal withdrawBalance = new BigDecimal(mWithdrawBalance);
                            int compare = balance.compareTo(withdrawBalance);
                            if (compare < 1) {
                                tv_withdraw_amount.setText(String.valueOf(balance.intValue()));
                                tv_amount.setText(String.valueOf(balance.intValue()));
                                if (mWithdrawalRadio > 0) {
                                    tv_actual_amount.setText(String.valueOf(balance.subtract(balance.multiply(new BigDecimal(mWithdrawalRadio))).intValue()));
                                }else {
                                    tv_actual_amount.setText(String.valueOf(balance.intValue()));
                                }
                            }else {
                                ToastUtil.show(getString(R.string.tip_input_withdraw_amount_fail));
                            }
                        }
                    }
                });
                break;
            case R.id.tv_pwd:
                DialogUtil.showSimpleInputDialog(this, getString(R.string.payment_password), null, DialogUtil.INPUT_TYPE_NUMBER_PASSWORD, 0, "0123456789", new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(content)) {
                            tv_pwd.setText(content);
                        }
                    }
                });
                break;
            case R.id.tv_confirm:
                if (mIsPayPwd == 0) {
                    ToastUtil.show(getString(R.string.tip_please_setting_pay_pwd));
                    return;
                }
                if (TextUtils.isEmpty(tv_withdraw_amount.getText().toString())) {
                    ToastUtil.show(getString(R.string.payment_amount_input_hint));
                    return;
                }
                if (TextUtils.isEmpty(tv_pwd.getText().toString())) {
                    ToastUtil.show(getString(R.string.payment_password_input_hint));
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_verify_code));
                    return;
                }
                mvpPresenter.applyWithdrawal(Integer.valueOf(tv_withdraw_amount.getText().toString()), et_code.getText().toString(), tv_pwd.getText().toString());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBindAccountSuccessEvent(BindAccountSuccessEvent event) {
        if (event != null) {
            mvpPresenter.getBankInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
