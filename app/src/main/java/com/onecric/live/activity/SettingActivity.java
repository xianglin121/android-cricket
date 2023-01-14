package com.onecric.live.activity;

import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.onecric.live.BuildConfig;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.user.SettingPresenter;
import com.onecric.live.util.DataCleanManager;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.SettingView;

import java.lang.reflect.Method;

public class SettingActivity extends MvpActivity<SettingPresenter> implements SettingView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    private ConstraintLayout ll_tip;
    private Switch btn_switch;
    private TextView tv_cache_size;

    private Handler mHandler;

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.setting));

        ll_tip = findViewById(R.id.ll_tip);
        btn_switch = findViewById(R.id.btn_switch);
        tv_cache_size = findViewById(R.id.tv_cache_size);

        findViewById(R.id.ll_tip).setOnClickListener(this);
        findViewById(R.id.cl_update).setOnClickListener(this);
        findViewById(R.id.cl_about_us).setOnClickListener(this);
        findViewById(R.id.cl_clear_cache).setOnClickListener(this);
        findViewById(R.id.ll_pay_pwd).setOnClickListener(this);
        findViewById(R.id.cl_change_pwd).setOnClickListener(this);
        findViewById(R.id.cl_logout).setOnClickListener(this);
//        findViewById(R.id.tv_sign_out).setOnClickListener(this);
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.FLOATING_PLAY, btn_switch.isChecked());
            }
        });
    }

    @Override
    protected void initData() {
        btn_switch.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.FLOATING_PLAY));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0动态申请悬浮窗权限
            if (!Settings.canDrawOverlays(this)) {
                ll_tip.setVisibility(View.VISIBLE);
            }
        } else {
            if (!checkOp(this, 24)) {
                ll_tip.setVisibility(View.VISIBLE);
            }
        }
//        tv_cache_size.setText(ToolUtil.getCacheSize());
        tv_cache_size.setText(DataCleanManager.getCacheSize(this));
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0动态申请悬浮窗权限
            if (Settings.canDrawOverlays(this)) {
                ll_tip.setVisibility(View.GONE);
            }
        } else {
            if (checkOp(this, 24)) {
                ll_tip.setVisibility(View.GONE);
            }
        }
    }

    String googlePlay = "com.android.vending";

    void transferToGooglePlay() {
        try {
            Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(googlePlay);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tip:
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                break;
            case R.id.cl_update:
                if (CommonAppConfig.getInstance().getConfig() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber())) {
                    DialogUtil.showVersionUpdateDialog(this, CommonAppConfig.getInstance().getConfig().getAndroidMandatoryUpdateSandbox() == 1 ? true : false,
                            CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber(),
                            CommonAppConfig.getInstance().getConfig().getAndroidDownloadText(),
                            CommonAppConfig.getInstance().getConfig().getAndroidDownloadUrl(), CommonAppConfig.getInstance().getConfig().getDomain_pc_name(), CommonAppConfig.getInstance().getConfig().getAndroid_mandatory_update_type()
                    );
                }
                break;
            case R.id.cl_about_us:
                AboutUsActivity.forward(this);
                break;
            case R.id.cl_clear_cache:
                DialogUtil.showSimpleDialog(this, getString(R.string.clear_cache), getString(R.string.clear_cache_tip), true, new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
//                        clearCache();
                        DataCleanManager.cleanInternalCache(SettingActivity.this);
                        if (mHandler == null) {
                            mHandler = new Handler();
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                tv_cache_size.setText("0.00MB");
                                ToastUtil.show(WordUtil.getString(mActivity, R.string.setting_clear_cache_success));
                            }
                        }, 3000);
                    }
                });
                break;
            case R.id.ll_pay_pwd:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                PayPwdSettingActivity.forward(this, PayPwdSettingActivity.ENTER_BY_SETTING);
                break;
            case R.id.cl_change_pwd:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                ChangePwdActivity.forward(this);
                break;
            case R.id.cl_logout:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                LogoutActivity.forward(this);
                break;
            case R.id.tv_sign_out:
                DialogUtil.showSimpleDialog(this, getString(R.string.tips), WordUtil.getString(this, R.string.confirm_sign_out_tip), true, new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        mvpPresenter.signOut(mActivity);
                    }
                });
                break;
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        final Dialog dialog = DialogUtil.loadingDialog(mActivity, getString(R.string.setting_clear_cache_ing));
        dialog.show();
        ToolUtil.deleteFolderFile(CommonAppConfig.IMAGE_PATH, false);
        ToolUtil.deleteFolderFile(CommonAppConfig.VIDEO_PATH, false);
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                tv_cache_size.setText(ToolUtil.getCacheSize());
                ToastUtil.show(WordUtil.getString(mActivity, R.string.setting_clear_cache_success));
            }
        }, 3000);
    }

    /**
     * 检查悬浮窗权限
     * <p>
     * API <18，默认有悬浮窗权限，不需要处理。无法接收无法接收触摸和按键事件，不需要权限和无法接受触摸事件的源码分析
     * API >= 19 ，可以接收触摸和按键事件
     * API >=23，需要在manifest中申请权限，并在每次需要用到权限的时候检查是否已有该权限，因为用户随时可以取消掉。
     * API >25，TYPE_TOAST 已经被谷歌制裁了，会出现自动消失的情况
     */
    private boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = AppOpsManager.class.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
//                TXCLog.e(TAG, Log.getStackTraceString(e));
            }
        }
        return true;
    }
}
