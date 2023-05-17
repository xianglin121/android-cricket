package com.onecric.live.activity;


import static com.onecric.live.util.SpUtil.REGISTRATION_TOKEN;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onecric.live.BuildConfig;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.common.TRANSTYPE;
import com.onecric.live.custom.HomeTabLayout;
import com.onecric.live.custom.NoScrollViewPager;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.event.UpdateUserInfoEvent;
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.fragment.MoreFragment;
import com.onecric.live.fragment.OneLiveFragment;
import com.onecric.live.fragment.OneVideoFragment;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.login.MainPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.MPermissionUtils;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.MainView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tuikit.tuichat.util.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    public static void forward(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void loginForward(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private ImageView iv_avatar_nav;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mViewList;
    private HomeTabLayout mTabLayout;
    public int mPosition = 0;
    private long exit_time;

//    public LoginDialog loginDialog;
//    private WebView webview;
//    private WebSettings webSettings;
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
//    private TextView tv_name_nav;
//    private TextView tv_sign_out;
//    private LinearLayout llNav;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        //设置2倍的cpu数实现图片加载的提速
        GlideBuilder builder = new GlideBuilder();
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor(
                GlideExecutor.calculateBestThreadCount() * 2, "newsImg", GlideExecutor.UncaughtThrowableStrategy.DEFAULT));

        mViewList = new ArrayList<>();

        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabLayout);
        iv_avatar_nav = findViewById(R.id.iv_avatar);

        iv_avatar_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    OneLogInActivity.forward(mActivity);
//                    loginDialog.show();
                } else {
//                UserInfoActivity.forward(mActivity);
                    if (!isFastDoubleClick())
                        PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
                }
//                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

//        initNavigationView();
        initFragment();
        getFCMToken();
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar_nav);
        } else {
            iv_avatar_nav.setImageResource(R.mipmap.bg_avatar_default);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取默认配置
        mvpPresenter.getConfiguration(ToolUtil.getCurrentVersionCode(this));
    }

    @Override
    protected void initData() {
        checkNotifySetting();
//        MPermissionUtils.requestPermissionsResult(this, 300, new String[]{
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_PHONE_STATE}
//                , new MPermissionUtils.OnPermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                    }
//
//                    @Override
//                    public void onPermissionDenied() {
//                        MPermissionUtils.showTipsDialog(mActivity);
//                    }
//                });
        mTabLayout.setOnSwitchTabListener(new HomeTabLayout.OnSwitchTabListener() {
            @Override
            public void onSwitch(TRANSTYPE type) {
                transFragment(type);
            }
        });
        //登录IM
        loginIM();
//        //获取默认配置
//        mvpPresenter.getConfiguration(ToolUtil.getCurrentVersionCode(this));
//        //检查是否有版本更新
//        if (CommonAppConfig.getInstance().getConfig() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber())) {
////            DialogUtil.showVersionUpdateDialog(this, CommonAppConfig.getInstance().getConfig().getAndroidMandatoryUpdateSandbox() == 1 ? true : false,
////                    CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber(),
////                    CommonAppConfig.getInstance().getConfig().getAndroidDownloadText(),
////                    CommonAppConfig.getInstance().getConfig().getAndroidDownloadUrl());
//            if (DialogUtil.checkUpdateInfo(this, CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber())) {
//                transferToGooglePlay();
//            }
//        }
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

    private void loginIM() {
        if (CommonAppConfig.getInstance().getUserBean() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getUserSign())) {
            TUIKit.login(CommonAppConfig.getInstance().getUid(), CommonAppConfig.getInstance().getUserSign(), new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    //更新个人信息
                    V2TIMUserFullInfo v2TIMUserFullInfo = new V2TIMUserFullInfo();
                    v2TIMUserFullInfo.setNickname(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
                    if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getAvatar())) {
                        v2TIMUserFullInfo.setFaceUrl(CommonAppConfig.getInstance().getUserBean().getAvatar());
                    }
                    V2TIMManager.getInstance().setSelfInfo(v2TIMUserFullInfo, new V2TIMCallback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(int i, String s) {
                        }
                    });
                }

                @Override
                public void onError(int code, String error) {
                }
            });
        } else {
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getVisitorUserId()) && !TextUtils.isEmpty(CommonAppConfig.getInstance().getVisitorUserSign())) {
                TUIKit.login(CommonAppConfig.getInstance().getVisitorUserId(), CommonAppConfig.getInstance().getVisitorUserSign(), new V2TIMCallback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(int code, String error) {
                    }
                });
            } else {
                mvpPresenter.getVisitorUserSig();
            }
        }
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getVisitorUserSigSuccess(String userId, String userSig) {
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(userSig)) {
            TUIKit.login(userId, userSig, new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    CommonAppConfig.getInstance().setVisitorUserId(userId);
                    CommonAppConfig.getInstance().setVisitorUserSign(userSig);
                }

                @Override
                public void onError(int code, String error) {
                }
            });
        }
    }

    @Override
    public void getDataSuccess(UserBean userBean) {
        if (userBean != null) {
            CommonAppConfig.getInstance().saveUserInfo(JSONObject.toJSONString(userBean));
            GlideUtil.loadUserImageDefault(this, userBean.getAvatar(), iv_avatar_nav);

//            ((ThemeFragment) mViewList.get(0)).updateUserInfo();
//            ((OneLiveFragment) mViewList.get(0)).updateUserInfo();
            ((MoreFragment) mViewList.get(3)).updateUserInfo();
        }
    }

    @Override
    public void getConfigSuccess(ConfigurationBean bean) {
        CommonAppConfig.getInstance().saveConfig(bean);
        //检查是否有版本更新
        if (CommonAppConfig.getInstance().getConfig() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber())) {
            DialogUtil.showVersionUpdateDialog(this, CommonAppConfig.getInstance().getConfig().getAndroidMandatoryUpdateSandbox() == 1 ? true : false,
                    CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber(),
                    CommonAppConfig.getInstance().getConfig().getAndroidDownloadText(),
                    CommonAppConfig.getInstance().getConfig().getAndroidDownloadUrl(), CommonAppConfig.getInstance().getConfig().getDomain_pc_name(), CommonAppConfig.getInstance().getConfig().getAndroid_mandatory_update_type()
            );
//            if (DialogUtil.checkUpdateInfo(this, CommonAppConfig.getInstance().getConfig().getAndroidVersionMumber())) {
//                transferToGooglePlay();
//            }
        }

        //获取登录倒计时时长
        if(!TextUtils.isEmpty(bean.getLogin_remind()) && !"0".equals(bean.getLogin_remind())){
            try{
                int time = Integer.parseInt(bean.getLogin_remind());
                SpUtil.getInstance().setIntValue(SpUtil.LOGIN_REMIND,time);
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        SpUtil.getInstance().setIntValue(SpUtil.LOGIN_REMIND,0);
    }

    @Override
    public void getDataFail(String msg) {

    }

    private void initFragment() {
        //给有登录需求的页面加loginDialog
//        ThemeFragment themeFragment = new ThemeFragment();
        OneLiveFragment liveFragment = new OneLiveFragment();
        OneVideoFragment videoFragment = new OneVideoFragment();
//        themeFragment.setLoginDialog(loginDialog);
//        liveFragment.setLoginDialog(loginDialog);
//        videoFragment.setLoginDialog(loginDialog);

        mViewList.add(liveFragment);
        mViewList.add(videoFragment);
        mViewList.add(new CricketNewFragment());
        mViewList.add(new MoreFragment());

        mViewPager.setScroll(false);
        mViewPager.setOffscreenPageLimit(mViewList.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mViewList.get(i);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }


    private void transFragment(TRANSTYPE type) {
        switch (type) {
            case THEME:
                mViewPager.setCurrentItem(0, false);
                break;
            case MATCH:
                mViewPager.setCurrentItem(1, false);
                break;
            case LIVE:
                mViewPager.setCurrentItem(2, false);
                break;
            case VIDEO:
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewList.get(0).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfoEvent(UpdateUserInfoEvent event) {
        if (event != null) {
//            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
//                ((UserFragment)mViewList.get(4)).updateUserInfo();
//            }
            mvpPresenter.getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            loginIM();
//            updateNavigationInfo();
            if (CommonAppConfig.getInstance().getUserBean() != null) {
                GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar_nav);
            } else {
                iv_avatar_nav.setImageResource(R.mipmap.bg_avatar_default);
            }

//            ((ThemeFragment) mViewList.get(0)).updateUserInfo();
//            ((OneLiveFragment) mViewList.get(0)).updateUserInfo();
            ((MoreFragment) mViewList.get(3)).updateUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToggleTabEvent(ToggleTabEvent event) {
        if (event == null) {
            return;
        }
        if (mTabLayout != null && mViewPager != null) {
            if(event.position == 21){
                mTabLayout.toggleBtn(2);
                mViewPager.setCurrentItem(2);
                ((CricketNewFragment) mViewList.get(2)).filtrateData(1);
            }else{
                mTabLayout.toggleBtn(event.position);
                mViewPager.setCurrentItem(event.position);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        GSYVideoManager.instance().clearAllDefaultCache(this);
    }

    @Override
    public void onBackPressed() {
/*        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }*/
        long l = System.currentTimeMillis();
        if (exit_time == 0 || l - exit_time > 3000) {
            ToastUtil.show(getString(R.string.tip_exit_app));
            exit_time = l;
        } else {
            System.exit(0);
        }
    }

//    private void getFCMToken() {
//        int googlePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if (googlePlayServicesAvailable == ConnectionResult.SUCCESS) {
//            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                @Override
//                public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                    if (!task.isSuccessful()) {
//                        Log.e(TAG, "getInstanceId failed" + task.getException());
//                        return;
//                    }
//                    String token = task.getResult() != null ? task.getResult().getToken() : "Token is null";
//                    Toast.makeText(getAppContext(), token, Toast.LENGTH_SHORT).show();
//                    LogUtil.e("token::::" + token);
//                }
//            });
//        }
//    }



    private void checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();

        if (isOpened) {
        } else {
//            ToastUtil.show("The application does not open the notification permission to authorize the open notification permission");
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//            //这种方案适用于 API 26, 即8.0(含8.0)以上可以用
//            intent.putExtra(EXTRA_APP_PACKAGE, mContext.getPackageName());
//            intent.putExtra(EXTRA_CHANNEL_ID, mContext.getApplicationInfo().uid);
//            mContext.startActivity(intent);
            PermissionUtils.showNotifiPermissionDialog(this);
        }
    }

    private static final String TAG = "MainActivity";

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        SpUtil.getInstance().setStringValue(REGISTRATION_TOKEN, token);
                        Log.e(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNotification(intent); // app处于后台 谷歌推送跳转逻辑
    }

    private void handleNotification(Intent pintent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (pintent.getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = pintent.getExtras();
            String isLive = bundle.getString("isLive");
//        String anchorId = bundle.getString("anchorId");
//        String type = bundle.getString("type");
//        String matchId = bundle.getString("matchId");
            try {
                if (bundle != null && "1".equals(isLive)) {//比赛开始   进入视频直播界面
                    intent.setClass(this, LiveDetailActivity.class);
                    intent.putExtra("anchorId", Integer.parseInt(bundle.getString("anchorId")));
                    intent.putExtra("type", Integer.parseInt(bundle.getString("type")));
                    intent.putExtra("matchId", Integer.parseInt(bundle.getString("matchId")));
                    intent.putExtra("isLive", true);
                    intent.putExtra("mLiveId", Integer.parseInt(bundle.getString("mLiveId")));
                } else {//比赛已经结束 或者是延迟进入比赛详情界面
                    intent.setClass(this, CricketDetailActivity.class);
                    intent.putExtra("matchId", Integer.parseInt(bundle.getString("matchId")));
                }
                this.startActivity(intent);
            } catch (Exception e) {

            }

        }
    }

/*    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        webview = (WebView) findViewById(R.id.webview);
        webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 禁用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDefaultTextEncodingName("utf-8");
        webview.setBackgroundColor(0); // 设置背景色
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 开启js支持
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(this, "jsBridge");
        webview.loadUrl("file:///android_asset/index.html");
    }

    @JavascriptInterface
    public void getData(String data) {
        webview.postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    if (jsonObject.getIntValue("ret") == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                dialog.show();
                                OneLogInActivity.forward(mActivity);
//                                loginDialog.show();
//                                loginDialog.passWebView();
                            }
                        });
                    }
                }
            }
        }, 500);
    }

    public void newLoginDialog() {
        if (loginDialog == null) {
            loginDialog = new LoginDialog(this, R.style.dialog, true, () -> {
                loginDialog.dismiss();
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl("javascript:ab()");
            });
        }
        ((ThemeFragment) mViewList.get(0)).setLoginDialog(loginDialog);
        ((LiveFragment) mViewList.get(2)).setLoginDialog(loginDialog);
        ((VideoFragment) mViewList.get(3)).setLoginDialog(loginDialog);
//        loginDialog.show();
        OneLogInActivity.forward(mActivity);
    }

    private void initNavigationView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        llNav = findViewById(R.id.ll_nav);
        initWebView();
        loginDialog = new LoginDialog(this, R.style.dialog, true, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止侧边滑动
        navigationView.setItemIconTintList(null);
        iv_avatar_nav = navigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
        tv_name_nav = navigationView.getHeaderView(0).findViewById(R.id.tv_name_nav);

        tv_sign_out = findViewById(R.id.tv_sign_out);
        iv_avatar_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    OneLogInActivity.forward(mActivity);
//                    loginDialog.show();
                } else {
//                UserInfoActivity.forward(mActivity);
                    if (!isFastDoubleClick())
                        PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        updateNavigationInfo();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_system_settings:
                        SettingActivity.forward(mActivity);
                        break;
                    case R.id.menu_system_share:
                        ShareUtil.shareText(mActivity, "Share OneCric.tv", SHARE_LIVE_URL);
                        break;
                    case R.id.menu_my_concerns:
                        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                            ToastUtil.show(getString(R.string.please_login));
                            OneLogInActivity.forward(mActivity);
//                            loginDialog.show();
                        } else {
                            MyFollowActivity.forward(mActivity);
                        }
                        break;
                    case R.id.menu_my_message:
                        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                            ToastUtil.show(getString(R.string.please_login));
                            OneLogInActivity.forward(mActivity);
//                            loginDialog.show();
                        } else {
                            MyMessageActivity.forward(mActivity);
                        }
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        tv_sign_out.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                //退出登录
                DialogUtil.showSimpleDialog(mActivity, getString(R.string.tips), WordUtil.getString(mActivity, R.string.confirm_sign_out_tip), true, new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        mvpPresenter.signOut(mActivity);
                    }
                });
            } else {
                //登录
                OneLogInActivity.forward(mActivity);
//                loginDialog.show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            tv_sign_out.setText(getString(R.string.setting_sign_out));
        } else {
            tv_sign_out.setText(getString(R.string.setting_sign_in));
        }

    }

    public void updateNavigationInfo() {
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar_nav);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getUser_nickname())) {
                tv_name_nav.setText(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            }
            tv_sign_out.setText(getString(R.string.setting_sign_out));
        } else {
            iv_avatar_nav.setImageResource(R.mipmap.bg_avatar_default);
            tv_name_nav.setText("");
            tv_sign_out.setText(getString(R.string.setting_sign_in));
        }
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }*/
}