package com.onecric.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.CommonCode;
import com.onecric.live.R;
import com.onecric.live.adapter.PhonePrefixAdapter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.ToastUtil;
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
public class ChangePhoneActivity extends BaseActivity {
    public static void forwardForResult(Activity activity, String phoneNumber) {
        Intent intent = new Intent(activity, ChangePhoneActivity.class);
        intent.putExtra("phone", phoneNumber);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_CHANGE_PHONE);
    }

    private TextView tv_phone;
    private TextView tv_phone_prefix;
    private EditText et_input;
    private EditText et_code;
    private TextView tv_get_code;

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
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initView() {
        getCodeString = WordUtil.getString(this, R.string.get_verify_code);
        getCodeAgainString = WordUtil.getString(this, R.string.get_code_again);
        setTitleText(getString(R.string.title_change_phone));
        String phoneNumber = getIntent().getStringExtra("phone");

        tv_phone = findViewById(R.id.tv_phone);
        tv_phone_prefix = findViewById(R.id.tv_phone_prefix);
        et_input = findViewById(R.id.et_input);
        et_code = findViewById(R.id.et_code);
        tv_get_code = findViewById(R.id.tv_get_code);

        tv_phone_prefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhonePrefix();
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_phone_number));
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    ToastUtil.show(getString(R.string.please_input_verify_code));
                    return;
                }
                String prefix= tv_phone_prefix.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("phone", prefix+ "-" + et_input.getText().toString());
                intent.putExtra("code", et_code.getText().toString());
                setResult(CommonCode.RESULT_CODE_CHANGE_PHONE, intent);
                finish();
            }
        });
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFastDoubleClick()) {
                    if (TextUtils.isEmpty(et_input.getText().toString())) {
                        ToastUtil.show(getString(R.string.please_input_phone_number));
                        return;
                    }
                    tv_get_code.setEnabled(false);
                    String prefix= tv_phone_prefix.getText().toString();
                    getCode(prefix + "-" + et_input.getText().toString());
                }
            }
        });

        if (!TextUtils.isEmpty(phoneNumber)) {
            tv_phone.setText(phoneNumber);
        }
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

    private void getCode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", 9);
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
                    }

                    @Override
                    public void onError(String msg) {
                        tv_get_code.setEnabled(true);
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

    //选择手机前缀
    private void choosePhonePrefix() {
        if (CommonAppConfig.getInstance().getConfig() != null && CommonAppConfig.getInstance().getConfig().getCountryCode() != null) {
            View v = LayoutInflater.from(this).inflate(R.layout.view_phone_prefix_pop, null);
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
