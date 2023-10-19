package com.onecric.live;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.multidex.MultiDexApplication;

import com.appsflyer.AppsFlyerLib;
import com.engagelab.privates.common.global.MTGlobal;
import com.engagelab.privates.core.api.MTCorePrivatesApi;
import com.engagelab.privates.push.api.MTPushPrivatesApi;
import com.hjq.language.MultiLanguages;
import com.onecric.live.util.LogUtil;
import com.onecric.live.util.SpUtil;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.common.SystemUtil;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.rtmp.TXLiveBase;

import java.util.Locale;


//import cn.jpush.android.api.JPushInterface;
//import pro.piwik.sdk.Piwik;
//import pro.piwik.sdk.Tracker;
//import pro.piwik.sdk.TrackerConfig;

public class AppManager extends MultiDexApplication {
    public static AppManager mContext;
    private UploadManager uploadManager;


//    private Tracker tracker;
//
//    public synchronized Tracker getTracker() {
//        if (tracker == null)
//            tracker = Piwik.getInstance(this).newTracker(new TrackerConfig("https://onecric.piwik.pro", "fe5a55b8-bec8-4022-ab0c-b327e32cdf82", "Default Tracker"));
//        return tracker;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

//        setLan();
        // 初始化语种切换框架
        MultiLanguages.init(this);

        //fixme 调试模式，生产环境注释！
//        AppsFlyerLib.getInstance().setDebugLog(true);
//        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);

        // 初始化AppsFlyer分析 (，转化数据，)
        AppsFlyerLib.getInstance().init("wcDvrMBc3NTb7S64gfWTUF", null, this);
        AppsFlyerLib.getInstance().waitForCustomerUserId(true);

        initTX();
        /**
         * TUIKit的初始化函数
         *
         * @param context  应用的上下文，一般为对应应用的ApplicationContext
         * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
         * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
         */
        TUIKit.init(this, 50000071, null, new V2TIMSDKListener() {
            @Override
            public void onConnecting() {
            }

            @Override
            public void onConnectSuccess() {
                LogUtil.v("TIMlogin:");
            }

            @Override
            public void onConnectFailed(int code, String error) {
                LogUtil.v("TIMlogin:" + code + "  " + error);
            }

            @Override
            public void onKickedOffline() {
            }

            @Override
            public void onUserSigExpired() {
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
            }
        });
        Configuration config = new Configuration.Builder()
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zoneAs0)        // 设置区域，不指定会自动选择。指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        //配置3个线程数并发上传；不配置默认为3，只针对file.size>4M生效。线程数建议不超过5，上传速度主要取决于上行带宽，带宽很小的情况单线程和多线程没有区别
        uploadManager = new UploadManager(config);

//        initJiGuang();
        CrashReport.initCrashReport(getApplicationContext(), "8b6829edfc", false);
        if (!TextUtils.isEmpty(SystemUtil.getDeviceType())) {
            CrashReport.setDeviceModel(this, SystemUtil.getDeviceType());
        }
    }

    private void setLan(){
        if(SpUtil.getInstance().getIntValue(SpUtil.APP_LANGUAGE) == 0){
            return;
        }
        Resources resources = getResources();
        // 获得屏幕参数,主要是用来下面的切换
        DisplayMetrics metrics = resources.getDisplayMetrics();
        // 获得配置对象
        android.content.res.Configuration config = resources.getConfiguration();
        switch (SpUtil.getInstance().getIntValue(SpUtil.APP_LANGUAGE)) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.ENGLISH);
                } else {
                    config.locale = Locale.ENGLISH;
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(new Locale("hi","rIN"));
                } else {
                    config.locale = new Locale("hi","rIN");
                }
                break;
        }
        resources.updateConfiguration(config, metrics);
    }

/*    @Override
    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLan();
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base));
    }

    public UploadManager getUpLoadManager() {
        return uploadManager;
    }

    private void initTX() {
        //直播腾讯云鉴权url
        String liveLicenceUrl = "https://license.vod-control.com/license/v2/1309782813_1/v_cube.license";
        //直播腾讯云鉴权key
        String liveKey = "15848904747cca037d08b4c0f6ed785b";
        TXLiveBase.getInstance().setLicence(this, liveLicenceUrl, liveKey);
    }

    private void initJiGuang() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        // 必须在application.onCreate中配置，不要判断进程，sdk内部有判断
        MTCorePrivatesApi.configDebugMode(this, true);
        MTGlobal.setCountryCode("US");
        // 初始化推送，需要单独配置后台环境，否则会无法使用推送功能，不需要则删除
        MTPushPrivatesApi.init(this);
    }
}
