package com.onecric.live.activity;

import static com.onecric.live.util.SpUtil.REGISTRATION_TOKEN;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.appsflyer.AppsFlyerLib;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.BaseActivity;

import java.math.BigInteger;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!SpUtil.getInstance().getBooleanValue(SpUtil.HIDE_USAGE)) {
                UsageViewActivity.forward(mActivity);
            } else {
                if (getIntent().getExtras() == null) {
                    MainActivity.forward(mActivity);
                } else {
                    Intent intent = getIntent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            finish();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        //获取默认配置
//        getConfiguration();
        setLanguage();
        mHandler.sendEmptyMessageDelayed(0, 1500);
    }

    private void getConfiguration() {
        ApiClient.retrofit().create(ApiStores.class)
                .getDefaultConfiguration(ToolUtil.getCurrentVersionCode(this),SpUtil.getInstance().getStringValue(REGISTRATION_TOKEN))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        ConfigurationBean configurationBean = JSONObject.parseObject(data, ConfigurationBean.class);
                        CommonAppConfig.getInstance().saveConfig(configurationBean);
//                        String value = SpUtil.getInstance().getStringValue(SpUtil.APP_PRIVACY_POLICY);
//                        if (TextUtils.isEmpty(value)) {
//                            DialogUtil.showPrivacyPolicyDialog(SplashActivity.this, configurationBean.getPrivacy_policy(), configurationBean.getUser_agreement(), new DialogUtil.SimpleCallback3() {
//                                @Override
//                                public void onClick(boolean isConfirm) {
//                                    if (isConfirm) {
//                                        SpUtil.getInstance().setStringValue(SpUtil.APP_PRIVACY_POLICY, "privacyPolicy");
//                                        mHandler.sendEmptyMessageDelayed(0, 1500);
//                                    }else {
//                                        System.exit(0);
//                                    }
//                                }
//                            });
//                        }else {
                        mHandler.sendEmptyMessageDelayed(0, 1500);
//                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String msg) {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    private void setLanguage() {
        String lan = "english";
        switch (SpUtil.getInstance().getIntValue(SpUtil.APP_LANGUAGE)) {
            case 1:
                lan = "english";
                break;
            case 2:
                lan = "hindi";
                break;
            case 0:
                String localLan = getResources().getConfiguration().locale.getLanguage();
                if(localLan.equals("hi")){
                    lan = "hindi";
                }else{
                    lan = "english";
                }
        }

        String myUuid = SpUtil.getInstance().getStringValue(SpUtil.MY_UUID);
        if(TextUtils.isEmpty(myUuid)){
            String[] uuidSplit = UUID.randomUUID().toString().split("-");
            myUuid = new BigInteger(uuidSplit[uuidSplit.length-1],16).toString();
            SpUtil.getInstance().setStringValue(SpUtil.MY_UUID,myUuid);
        }

        //启动AppsFlyer 1
        AppsFlyerLib.getInstance().start(this);
        //启动AppsFlyer 2 设置响应监听器
/*        AppsFlyerLib.getInstance().start(getApplicationContext(), "wcDvrMBc3NTb7S64gfWTUF", new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.i("LTT","确认AppsFlyer成功");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.i("LTT", "Launch failed to be sent:\n" +
                        "Error code: " + i + "\n"
                        + "Error description: " + s);
            }
        });*/

        //设置用户id
//        AppsFlyerLib.getInstance().setCustomerUserId(myUuid);
        AppsFlyerLib.getInstance().setCustomerIdAndLogSession(myUuid, this);

        ApiClient.retrofit().create(ApiStores.class)
                .setLanguage(lan,myUuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }
}
