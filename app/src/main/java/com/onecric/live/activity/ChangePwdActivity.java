package com.onecric.live.activity;

import android.app.Activity;
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
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/4
 */
public class ChangePwdActivity extends BaseActivity {
    public static void forward(Activity activity) {
        Intent intent = new Intent(activity, ChangePwdActivity.class);
        activity.startActivity(intent);
    }

    private TextView tv_phone;
    private EditText et_code;
    private TextView tv_get_code;
    private EditText et_pwd;

    private Handler handler;
    private static final int TOTAL = 60;
    private int count = TOTAL;
    private String getCodeString;
    private String getCodeAgainString;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        setTitleText(getString(R.string.update_password));

        tv_phone = findViewById(R.id.tv_phone);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        et_pwd = findViewById(R.id.et_pwd);

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_verify_code));
                    return;
                }
                if (TextUtils.isEmpty(et_pwd.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_pwd_hint));
                    return;
                }
                changePwd(CommonAppConfig.getInstance().getUserBean().getMobile(), et_code.getText().toString(), et_pwd.getText().toString());
            }
        });
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFastDoubleClick()) {
                    tv_get_code.setEnabled(false);
                    getCode(CommonAppConfig.getInstance().getUserBean().getMobile());
                }
            }
        });
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
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getMobile())) {
                String mobile = CommonAppConfig.getInstance().getUserBean().getMobile();
                if (mobile.contains("-")) {
                    mobile = mobile.substring(mobile.indexOf("-")+1);
                }
                tv_phone.setText(String.format(getString(R.string.change_pwd_text), ToolUtil.changPhoneNumber(mobile)));
            }
        }
    }

    private void getCode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", 2);
        ApiClient.retrofit().create(ApiStores.class)
                .getCode(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFailure(String msg) {
                        tv_get_code.setEnabled(true);
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        tv_get_code.setEnabled(true);
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void changePwd(String phone, String code, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("code", code);
        jsonObject.put("password", password);
        ApiClient.retrofit().create(ApiStores.class)
                .changePwd(getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ToastUtil.show(getString(R.string.tip_change_pwd_success));
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private RequestBody getRequestBody(JSONObject jsonObject) {
        MediaType CONTENT_TYPE = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE, jsonObject.toString());
        return requestBody;
    }
}
