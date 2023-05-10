package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gjylibrary.GjySerialnumberLayout;
import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.VerificationPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.VerificationView;

/**
 * 验证码
 */
public class OneVerificationActivity extends MvpActivity<VerificationPresenter> implements VerificationView {
    private final int FROM_FORGET_PASSWORD = 2201;
    private final int FROM_SIGN_UP = 2202;
    private final int FROM_SET_PASSWORD = 2203;
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

        tv_account = findViewById(R.id.tv_account);
        tv_info = findViewById(R.id.tv_info);
        tv_countdown = findViewById(R.id.tv_countdown);
        tv_send_code = findViewById(R.id.tv_send_code);
        tv_verify = findViewById(R.id.tv_verify);
        verification_code = findViewById(R.id.verification_code);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        tv_account.setText("OTP Sent To " + account);
        if(isEmail){
            tv_info.setText("In Case You Don't Find It, Check Your Spam Folder.");
        }else{
            tv_info.setText("In Case You Don't Find It, Check Your Junk Information.");
        }

        if(fromType == FROM_FORGET_PASSWORD){
            ll_title.setVisibility(View.VISIBLE);
            tv_title.setText("Forget Password?");
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
        //fixme 修改验证码框样式
        tv_verify.setOnClickListener(v -> {
            if(TextUtils.isEmpty(vCode) || vCode.length()<6){
                ToastUtil.show("Verify");
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
                    tv_countdown.setText("Resend OTP IN  "+(count > 60 ? ("01:"+(count-60)) :(count+"s")));
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
                OneSetPwdActivity.forward(this,account);
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
