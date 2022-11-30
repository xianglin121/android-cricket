package com.longya.live.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.model.ConfigurationBean;
import com.longya.live.retrofit.ApiCallback;
import com.longya.live.retrofit.ApiClient;
import com.longya.live.retrofit.ApiStores;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.view.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!SpUtil.getInstance().getBooleanValue(SpUtil.HIDE_USAGE)) {
                UsageViewActivity.forward(mActivity);
            }else {
                MainActivity.forward(mActivity);
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
        mHandler.sendEmptyMessageDelayed(0, 1500);
    }

    private void getConfiguration() {
        ApiClient.retrofit().create(ApiStores.class)
                .getDefaultConfiguration()
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
}
