package com.onecric.live.activity;

import static com.onecric.live.AppManager.mContext;
import static com.onecric.live.util.SpUtil.REGISTRATION_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.engagelab.privates.core.api.MTCorePrivatesApi;
import com.example.gjylibrary.GjySerialnumberLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onecric.live.R;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.VerificationPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.VerificationView;

import org.greenrobot.eventbus.EventBus;

/**
 * 验证码
 */
public class OneVerificationActivity extends MvpActivity<VerificationPresenter> implements VerificationView {
    private final int FROM_FORGET_PASSWORD = 2201;
    private final int FROM_SIGN_UP = 2202;
    private final int FROM_SET_PASSWORD = 2203;
    private final int FROM_LOGIN_IN = 2204;

    public static void forward(Context context,String account,int fromType) {
        Intent intent = new Intent(context, OneVerificationActivity.class);
        if(!TextUtils.isEmpty(account)){
            intent.putExtra("account",account);
        }
        intent.putExtra("fromType",fromType);
        context.startActivity(intent);
    }

    private TextView tv_account,tv_info,tv_countdown,tv_verify,tv_title,tv_send_code;
//    private EditText et_verification;
    private GjySerialnumberLayout verification_code;
    private LinearLayout ll_title;
    private Handler handler;
    private final int TOTAL = 120;
    private int count = TOTAL;
    private String vCode,account;
    private int fromType;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    protected void initView() {
        account = getIntent().getStringExtra("account");
        boolean isEmail = (TextUtils.isEmpty(account) || (account.indexOf('@') == -1) ? false : true) ;
        fromType = getIntent().getIntExtra("fromType",0);
        if(TextUtils.isEmpty(account)){
            finish();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tv_account = findViewById(R.id.tv_account);
        tv_info = findViewById(R.id.tv_info);
        tv_countdown = findViewById(R.id.tv_countdown);
        tv_send_code = findViewById(R.id.tv_send_code);
        tv_verify = findViewById(R.id.tv_verify);
        verification_code = findViewById(R.id.verification_code);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        tv_account.setText(getString(R.string.send_to_otp) + account);
        if(isEmail){
            tv_info.setText(getString(R.string.verify_into));
        }else{
            tv_info.setText(getString(R.string.verify_into2));
        }

        if(fromType == FROM_FORGET_PASSWORD){
            ll_title.setVisibility(View.VISIBLE);
            tv_title.setText(getString(R.string.forget_password));
        }

        tv_send_code.setEnabled(false);
        tv_send_code.setOnClickListener(v -> {
            tv_send_code.setEnabled(false);
            showLoadingDialog();
            switch (fromType){
                case FROM_FORGET_PASSWORD:
                    mvpPresenter.getCode(account,2);
                    break;
                case FROM_SIGN_UP:
                case FROM_SET_PASSWORD:
                    mvpPresenter.getCode(account,1);//注册
                    break;
                case FROM_LOGIN_IN:
                    mvpPresenter.getCode(account,3);
                    break;
            }
        });

        verification_code.setOnInputListener(new GjySerialnumberLayout.OnInputListener() {
            @Override
            public void onSucess(String code) {
                vCode = code;
            }
        });
//        verification_code.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.shape_border_bottom));
//        verification_code.getChildAt(0).setOnFocusChangeListener(null);
        tv_verify.setOnClickListener(v -> {
            if(TextUtils.isEmpty(vCode) || vCode.length()<6){
                ToastUtil.show(getString(R.string.verify));
                return;
            }
            showLoadingDialog();
            switch (fromType){
                case FROM_FORGET_PASSWORD:
                    mvpPresenter.getCode(account,2);
                    mvpPresenter.verifyCode(account,vCode,2);
                    break;
                case FROM_SIGN_UP:
                case FROM_SET_PASSWORD:
                    mvpPresenter.verifyCode(account,vCode,1);
                    break;
                case FROM_LOGIN_IN:
                    mvpPresenter.oneLoginByPwd(account,SpUtil.getInstance().getStringValue(REGISTRATION_TOKEN),vCode);
                    break;
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initData() {
        //倒计时
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                count--;
                if(tv_countdown == null || handler == null){
                    return;
                }
                if(count > 0){
                    if(tv_countdown.getVisibility() == View.GONE){
                        tv_countdown.setVisibility(View.VISIBLE);
                    }
                    tv_countdown.setText(getString(R.string.resend_otp_in)+(count > 60 ? ("01:"+(count-60)) :(count+"s")));
                    handler.sendEmptyMessageDelayed(0, 1000);
                }else{
                    tv_countdown.setVisibility(View.GONE);
                    count = TOTAL;
                    tv_send_code.setVisibility(View.VISIBLE);
                    tv_send_code.setEnabled(true);
                }
            }
        };
        handler.sendEmptyMessage(0);
    }

    @Override
    protected VerificationPresenter createPresenter() {
        return new VerificationPresenter(this);
    }


    @Override
    public void getDataSuccess(JsonBean model) {
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void sendSuccess() {
        dismissLoadingDialog();
        tv_send_code.setVisibility(View.GONE);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void sendFail(String msg) {
        dismissLoadingDialog();
        tv_send_code.setEnabled(true);
    }

    @Override
    public void verifySuccess() {
        tv_send_code.setEnabled(true);
        dismissLoadingDialog();
        switch (fromType){
            case FROM_FORGET_PASSWORD:
                setResult(1002);
                finish();
                break;
            case FROM_SIGN_UP:
            case FROM_SET_PASSWORD:
//                OneSetPwdActivity.forward(this,account);
                ToastUtil.show(getResources().getString(R.string.success));
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "sign_up");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
                LoginAccessActivity.forward(this);
                finish();
                break;
            case FROM_LOGIN_IN:
                EventBus.getDefault().post(new UpdateLoginTokenEvent());
                Bundle bundle2 = new Bundle();
                bundle2.putString(FirebaseAnalytics.Param.METHOD, "login");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle2);
                mvpPresenter.updateJgId(MTCorePrivatesApi.getRegistrationId(mContext));
                ToastUtil.show(mContext.getString(R.string.login_success));
                finish();
                break;
            default:;
        }
    }

    @Override
    public void verifyFail(String msg) {
        tv_send_code.setEnabled(true);
        dismissLoadingDialog();
        ToastUtil.show(msg);
    }
}
