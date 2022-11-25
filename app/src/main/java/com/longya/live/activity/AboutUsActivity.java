package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.view.BaseActivity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/4
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    public static void forward(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_title;
    private TextView tv_version_name;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.about_us));

        tv_title = findViewById(R.id.tv_title);
        tv_version_name = findViewById(R.id.tv_version_name);

        findViewById(R.id.tv_protocol).setOnClickListener(this);
        findViewById(R.id.tv_privacy).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        PackageManager mPackageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = mPackageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo != null) {
            String versionName = packageInfo.versionName;
            tv_title.setText(getString(R.string.app_name) + "V" + versionName);
            tv_version_name.setText(versionName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_protocol:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getUser_agreement())) {
                    WebViewNewActivity.forward(this, getString(R.string.user_protocol), CommonAppConfig.getInstance().getConfig().getUser_agreement());
                }
                break;
            case R.id.tv_privacy:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getPrivacy_policy())) {
                    WebViewNewActivity.forward(this, getString(R.string.privacy_policy), CommonAppConfig.getInstance().getConfig().getPrivacy_policy());
                }
                break;
        }
    }
}
