package com.onecric.live.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.navigation.NavigationView;
import com.onecric.live.BuildConfig;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.common.TRANSTYPE;
import com.onecric.live.custom.HomeTabLayout;
import com.onecric.live.custom.NoScrollViewPager;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.event.UpdateUserInfoEvent;
import com.onecric.live.fragment.CricketFragment;
import com.onecric.live.fragment.LiveFragment;
import com.onecric.live.fragment.ThemeFragment;
import com.onecric.live.fragment.VideoFragment;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.login.MainPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.MPermissionUtils;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.login.MainView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.qcloud.tim.uikit.TUIKit;

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

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iv_avatar_nav;
    private TextView tv_name_nav;
    private TextView tv_sign_out;
    private LinearLayout llNav;

    private NoScrollViewPager mViewPager;
    private List<Fragment> mViewList;

    private HomeTabLayout mTabLayout;

    public int mPosition = 1;

    private long exit_time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mViewList = new ArrayList<>();

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        llNav = findViewById(R.id.ll_nav);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabLayout);

        initNavigationView();
        initFragment();
    }

    private void initNavigationView() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止侧边滑动
        navigationView.setItemIconTintList(null);
        iv_avatar_nav = navigationView.getHeaderView(0).findViewById(R.id.iv_avatar_nav);
        tv_name_nav = navigationView.getHeaderView(0).findViewById(R.id.tv_name_nav);

        tv_sign_out = findViewById(R.id.tv_sign_out);
        iv_avatar_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    LoginActivity.forward(mActivity);
                    return;
                }
//                UserInfoActivity.forward(mActivity);
                PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        updateNavigationInfo();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_system_settings) {
                    SettingActivity.forward(mActivity);
                } else {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        ToastUtil.show(getString(R.string.please_login));
                        LoginActivity.forward(mActivity);
                    } else {
                        if (id == R.id.menu_my_concerns) {
                            MyFollowActivity.forward(mActivity);
                        } else if (id == R.id.menu_my_message) {
                            MyMessageActivity.forward(mActivity);
                        }
                    }
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
                LoginActivity.forward(mActivity);
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
        } else {
            iv_avatar_nav.setImageResource(R.mipmap.bg_avatar_default);
            tv_name_nav.setText("");
        }
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void initData() {
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
        //获取默认配置
        mvpPresenter.getConfiguration(ToolUtil.getCurrentVersionCode(this));
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
            if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                tv_name_nav.setText(userBean.getUser_nickname());
            } else {
                tv_name_nav.setText("");
            }
            ((ThemeFragment) mViewList.get(0)).updateUserInfo();
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
    }

    @Override
    public void getDataFail(String msg) {

    }

    private void initFragment() {
        mViewList.add(new ThemeFragment());
        mViewList.add(new CricketFragment());
        mViewList.add(new LiveFragment());
        mViewList.add(new VideoFragment());
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
        mViewPager.setCurrentItem(1);
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
            updateNavigationInfo();
            ((ThemeFragment) mViewList.get(0)).updateUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToggleTabEvent(ToggleTabEvent event) {
        if (event != null) {
            if (mTabLayout != null && mViewPager != null) {
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        long l = System.currentTimeMillis();
        if (exit_time == 0 || l - exit_time > 3000) {
            ToastUtil.show(getString(R.string.tip_exit_app));
            exit_time = l;
        } else {
            System.exit(0);
        }
    }

}