package com.onecric.live.activity;

import static com.onecric.live.util.UiUtils.getJsonData;
import static com.onecric.live.util.UiUtils.hideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.AreasModel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.login.ForgetPwdPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.ForgetPwdView;

import java.util.ArrayList;

public class OneForgetPwdActivity extends MvpActivity<ForgetPwdPresenter> implements ForgetPwdView,View.OnClickListener {
    public static void forward(Context context, String account) {
        Intent intent = new Intent(context, OneForgetPwdActivity.class);
        if(!TextUtils.isEmpty(account)){
            intent.putExtra("account",account);
        }
        context.startActivity(intent);
    }
    private final int FROM_FORGET_PASSWORD = 2201;
    private EditText et_password,et_password2,etArea,etPhone;
    private ImageView iv_eye_password,iv_eye_password2;
    private LinearLayout ll_account,ll_pwd,ll_pwd2,ll_title;
    private TextView tv_send,tv_title;
    private View view_line2;
    private boolean isPwVisitable = false;
    private boolean isPwConfirmVisitable = false;
    private boolean isVisitablePass = false;
    private CountryCodePicker ccp;
    private ArrayList<AreasModel.CountryModel> countryList;
    private boolean isSame;
    private String account;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);
        iv_eye_password = findViewById(R.id.iv_eye_password);
        iv_eye_password2 = findViewById(R.id.iv_eye_password2);
        ll_account = findViewById(R.id.ll_account);
        ll_pwd = findViewById(R.id.ll_pwd);
        ll_pwd2 = findViewById(R.id.ll_pwd2);
        tv_send = findViewById(R.id.tv_send);
        tv_title = findViewById(R.id.tv_title);
        ll_title = findViewById(R.id.ll_title);
        etArea = findViewById(R.id.et_area);
        etPhone = findViewById(R.id.et_phone);
        ccp = findViewById(R.id.ccp);
        view_line2 = findViewById(R.id.view_line2);
        iv_eye_password.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_eye_password.setOnClickListener(this);
        iv_eye_password2.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        ll_title.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.log_in));

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

        account = getIntent().getStringExtra("account");
        if(!TextUtils.isEmpty(account) && account.indexOf('-')!=-1){
            etArea.setText(account.split("-")[0]);
            if(account.split("-").length>1){
                etPhone.setText(account.split("-")[1]);
            }
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_eye_password:
                if (isPwVisitable) {
                    isPwVisitable = false;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_eye_password.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    isPwVisitable = true;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_eye_password.setImageResource(R.mipmap.ic_eye_open);
                }
                break;
            case R.id.iv_eye_password2:
                if (isPwConfirmVisitable) {
                    isPwConfirmVisitable = false;
                    et_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_eye_password2.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    isPwConfirmVisitable = true;
                    et_password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_eye_password2.setImageResource(R.mipmap.ic_eye_open);
                }
                break;
            case R.id.tv_send:
                if(isVisitablePass){

                    String pwd = et_password.getText().toString().trim();
                    String pwd2 = et_password2.getText().toString().trim();

                    if(TextUtils.isEmpty(pwd)){
                        ToastUtil.show(getString(R.string.login_password));
                        return;
                    }

                    if(TextUtils.isEmpty(pwd2)){
                        ToastUtil.show(getString(R.string.confirm_password));
                        return;
                    }

                    //正则 6-12位数字和小写字母组合
                    String regexST = "^(?![0-9]+$)(?![a-z]+$)[0-9a-z]{6,12}$";
                    if(!pwd.matches(regexST)){
                        ToastUtil.show(getString(R.string.pwd_into));
                        return;
                    }

                    if(!pwd.equals(pwd2)){
                        ToastUtil.show(WordUtil.getString(this, R.string.register_pwd_error));
                        return;
                    }
                    tv_send.setEnabled(false);
                    showLoadingDialog();
                    mvpPresenter.oneChangePwd(account, pwd);
                }else{
                    String area = etArea.getText().toString().trim();
                    if (TextUtils.isEmpty(area)) {
                        ToastUtil.show(getString(R.string.country));
                        return;
                    }
                    String phone = etPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtil.show(getString(R.string.mobile_number));
                        return;
                    }
                    tv_send.setEnabled(false);
                    account = area + "-" + phone;
                    showLoadingDialog();
                    mvpPresenter.getCode(account);
                }
                break;
            case R.id.iv_back:
                finish();
                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode == 1002){
            isVisitablePass = true;
            ll_account.setVisibility(View.GONE);
            ll_pwd.setVisibility(View.VISIBLE);
            ll_pwd2.setVisibility(View.VISIBLE);
            view_line2.setVisibility(View.VISIBLE);
            tv_send.setText(getString(R.string.update));
        }
    }

    @Override
    public void showCountryList() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            ccp.setCustomMasterCountries(CommonAppConfig.getInstance().getConfig().getCountryListAbbr());
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        tv_send.setEnabled(true);
        dismissLoadingDialog();
        Intent intent = new Intent(this, OneVerificationActivity.class);
        intent.putExtra("account",etArea.getText().toString().trim() + "-" + etPhone.getText().toString().trim());
        intent.putExtra("fromType",FROM_FORGET_PASSWORD);
        startActivityForResult(intent,1001);
    }

    @Override
    public void getDataFail(String msg) {
        tv_send.setEnabled(true);
        dismissLoadingDialog();
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter(this);
    }

    @Override
    public void forgetPwdSuccess(String msg) {
        tv_send.setEnabled(true);
        dismissLoadingDialog();
        ToastUtil.show(getString(R.string.tip_change_pwd_success));
        finish();
    }

    @Override
    public void forgetPwdFail(String msg) {
        tv_send.setEnabled(true);
        dismissLoadingDialog();
        ToastUtil.show(msg);
    }
}
