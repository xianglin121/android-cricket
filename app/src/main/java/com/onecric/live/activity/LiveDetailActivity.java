package com.onecric.live.activity;

import static com.tencent.imsdk.base.ThreadUtils.runOnUiThread;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.custom.AnchorMovingReplyDialog;
import com.onecric.live.custom.gift.AnimMessage;
import com.onecric.live.custom.gift.LPAnimationManager;
import com.onecric.live.custom.noble.LPNobleView;
import com.onecric.live.event.UpdateAnchorFollowEvent;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.fragment.LiveDetailMainFragment;
import com.onecric.live.fragment.ThemeFragment;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.BasketballDetailBean;
import com.onecric.live.model.BroadcastMsgBean;
import com.onecric.live.model.ColorMsgBean;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.GiftBean;
import com.onecric.live.model.LiveRoomBean;
import com.onecric.live.model.NormalMsgBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.live.LiveDetailPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ScreenUtils;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveDetailView;
//import com.opensource.svgaplayer.SVGADrawable;
//import com.opensource.svgaplayer.SVGAImageView;
//import com.opensource.svgaplayer.SVGAParser;
//import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.liteav.demo.superplayer.LivePlayerView;
import com.tencent.liteav.demo.superplayer.SuperPlayerDef;
import com.tencent.liteav.demo.superplayer.model.event.OpenNobleSuccessEvent;
import com.tencent.liteav.demo.superplayer.model.event.SendDanmuEvent;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveDetailActivity extends MvpActivity<LiveDetailPresenter> implements LiveDetailView, View.OnClickListener {

    public static void forward(Context context, int anchorId, int type, int matchId) {
        Intent intent = new Intent(context, LiveDetailActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("type", type);
        intent.putExtra("matchId", matchId);
        context.startActivity(intent);
    }

    private String mGroupId;
    private int mAnchorId;
    private int mType;
    private int mMatchId;
    private int mWindowsWidth;
    private View statusBar;
    public LivePlayerView playerView;
    private FrameLayout fl_main;
    private LiveDetailMainFragment liveDetailMainFragment;
    private FrameLayout fl_menu;
    //    private LiveDetailFootballFragment liveDetailFootballFragment;
//    private LiveDetailBasketballFragment liveDetailBasketballFragment;
    private ImageView iv_data;
    //    private SVGAImageView svga_gift;
    private LinearLayout ll_gift_container;
    private LinearLayout ll_noble_container;

    private boolean mIsFullScreen;
    public LiveRoomBean mLiveRoomBean;
    private FirebaseAnalytics mFirebaseAnalytics;

    private LoginDialog loginDialog;
    private WebView webview;
    private WebSettings webSettings;

    private TextView tv_title;
    private ImageView iv_back;
    private CircleImageView person_head_pic;

    //????????????????????????????????????????????????
    private CountDownTimer mCountDownTimer = new CountDownTimer(180000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            ToastUtil.show(getString(R.string.tip_login_to_live));
//            finish();
//            LoginActivity.forward(mActivity);
            loginDialog.show();
        }
    };

    @Override
    protected LiveDetailPresenter createPresenter() {
        return new LiveDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void initView() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle params = new Bundle();
        params.putInt("watch_live", 0);
        mFirebaseAnalytics.logEvent("watch_live", params);
        EventBus.getDefault().register(this);
        //??????????????????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mAnchorId = getIntent().getIntExtra("anchorId", 0);
        mType = getIntent().getIntExtra("type", 0);
        mMatchId = getIntent().getIntExtra("matchId", 0);
        mGroupId = String.valueOf(mAnchorId);
        mvpPresenter.setGroupId(mGroupId);
        //??????????????????
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mWindowsWidth = metric.widthPixels;

        statusBar = findViewById(R.id.statusBar);
        playerView = findViewById(R.id.playerView);
        fl_main = findViewById(R.id.fl_main);
        fl_menu = findViewById(R.id.fl_menu);
        iv_data = findViewById(R.id.iv_data);
//        svga_gift = findViewById(R.id.svga_gift);
        ll_gift_container = findViewById(R.id.ll_gift_container);
        ll_noble_container = findViewById(R.id.ll_noble_container);
        view_broadcast = findViewById(R.id.view_broadcast);
        fl_float_view = findViewById(R.id.fl_float_view);
        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        person_head_pic = findViewById(R.id.person_head_pic);

        iv_data.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        //?????????????????????????????????????????????
        playerView.setInitId(mAnchorId, mType, mMatchId);

        //???????????????????????????
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        params.leftMargin = mWindowsWidth;
//        fl_menu.setLayoutParams(params);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fl_menu, "translationX", 0, mWindowsWidth);
//        objectAnimator.setDuration(0);
//        objectAnimator.start();

        initWebView();
        loginDialog = new LoginDialog(this, R.style.dialog, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });

        //?????????fragment
        liveDetailMainFragment = LiveDetailMainFragment.newInstance(mGroupId, mAnchorId);
        liveDetailMainFragment.setLoginDialog(loginDialog);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, liveDetailMainFragment).commitAllowingStateLoss();
        if (mType == 0) {
//            liveDetailFootballFragment = LiveDetailFootballFragment.newInstance(mMatchId);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu, liveDetailFootballFragment).commitAllowingStateLoss();
        } else if (mType == 1) {
//            liveDetailBasketballFragment = LiveDetailBasketballFragment.newInstance(mMatchId);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu, liveDetailBasketballFragment).commitAllowingStateLoss();
        } else {
            iv_data.setVisibility(View.GONE);
        }

        //???????????????
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            liveDetailMainFragment.updateFollowData();
        }
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        webview = (WebView) findViewById(R.id.webview);
        webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // ????????????
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDefaultTextEncodingName("utf-8");
        webview.setBackgroundColor(0); // ???????????????
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // ??????js??????
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
                                loginDialog.show();
                                loginDialog.passWebView();
                            }
                        });
                    }
                }
            }
        }, 500);
    }

    @Override
    protected void initData() {
        //?????????????????????
//        LinearLayout.LayoutParams statusBarParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.getStatusBarHeight(this));
//        statusBar.setLayoutParams(statusBarParams);
        mvpPresenter.getInfo(mAnchorId);
        if (mType == 0) {
//            mvpPresenter.getFootballDetail(mMatchId);
        } else if (mType == 1) {
//            mvpPresenter.getBasketballDetail(mMatchId);
        }
        playerView.setPlayerViewCallback(new LivePlayerView.OnSuperPlayerViewCallback() {
            @Override
            public void onStartFullScreenPlay() {
                mIsFullScreen = true;
                statusBar.setVisibility(View.GONE);
            }

            @Override
            public void onStopFullScreenPlay() {
                mIsFullScreen = false;
                statusBar.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    if (mIsBlack) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(0);
                }
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            @Override
            public void onClickFloatCloseBtn() {
                // ??????????????????????????????????????????????????????
                playerView.resetPlayer();
                finish();
            }

            @Override
            public void onClickSmallReturnBtn() {
                backAction();
            }

            @Override
            public void onStartFloatWindowPlay() {
                // ??????????????????????????????????????????????????????????????????
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onRefreshClick() {
                if (mLiveRoomBean != null) {
                    if (mLiveRoomBean.getInfo() != null) {
                        if (!TextUtils.isEmpty(mLiveRoomBean.getInfo().getPull())) {
                            playerView.play(mLiveRoomBean.getInfo().getPull());
                        }
                    }
                }
            }

            @Override
            public void onQualityChange(int type) {
                if (mLiveRoomBean != null) {
                    if (mLiveRoomBean.getInfo() != null && mLiveRoomBean.getInfo().getClarity() != null) {
                        if (type == 0) {
                            playerView.play(mLiveRoomBean.getInfo().getPull());
                        } else if (type == 1) {
                            playerView.play(mLiveRoomBean.getInfo().getClarity().getHd());
                        } else if (type == 2) {
                            playerView.play(mLiveRoomBean.getInfo().getClarity().getSd());
                        } else if (type == 3) {
                            playerView.play(mLiveRoomBean.getInfo().getClarity().getSmooth());
                        }
                        playerView.updateQuality(type);
                    }
                }
            }

            @Override
            public void onClickRedEnvelope() {
                liveDetailMainFragment.showRedEnvelopeDialog();
            }
        });

        playerView.hideBackKey();

        //??????????????????
        LPAnimationManager.init(this);
        LPAnimationManager.addGiftContainer(ll_gift_container);

        if (CommonAppConfig.getInstance().getUserBean() != null && CommonAppConfig.getInstance().getUserBean().getGuard() != null) {
            showNobleAnim(CommonAppConfig.getInstance().getUserBean().getUser_nickname(), CommonAppConfig.getInstance().getUserBean().getGuard().getSwf_name(), CommonAppConfig.getInstance().getUserBean().getGuard().getSwf());
            //??????????????????????????????
            long endtime = CommonAppConfig.getInstance().getUserBean().getGuard().getEndtime();
            if ((endtime * 1000) - System.currentTimeMillis() > 0) {
                if (((endtime * 1000) - System.currentTimeMillis()) < 7 * 24 * 60 * 60 * 1000) {
                    DialogUtil.showSimpleDialog(mActivity, getString(R.string.title_noble_expiration_reminder), getString(R.string.text_noble_expiration_reminder), false, new DialogUtil.SimpleCallback() {
                        @Override
                        public void onConfirmClick(Dialog dialog, String content) {

                        }
                    });
                }
            }
        }

        //??????????????????????????????????????????
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (String.valueOf(mAnchorId).equals(CommonAppConfig.getInstance().getUid())) {
                playerView.setPeopleCountVisibility(View.VISIBLE);
            } else {
                playerView.setPeopleCountVisibility(View.INVISIBLE);
            }
        } else {
            playerView.setPeopleCountVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mCountDownTimer.start();
        }
    }

    public void refreshUserInfo() {
        mvpPresenter.getUserInfo();
    }

    public void doFollow() {
        mvpPresenter.doFollow(mAnchorId);
    }

    //???????????????
    @Override
    public void getDataSuccess(LiveRoomBean bean) {
        if (bean != null) {
            mLiveRoomBean = bean;
            //??????????????????????????????
            if (mLiveRoomBean.getUserData() != null) {
                boolean isShow = false;
//                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
//                    if (CommonAppConfig.getInstance().getUid().equals(String.valueOf(mAnchorId))) {
//                        isShow = false;
//                    } else {
//                        isShow = true;
//                    }
//                } else {
//                    isShow = true;
//                }
                if (isShow) {
                    if (mLiveRoomBean.getUserData().getIs_attention() == 0) {
                        Dialog dialog = DialogUtil.showAnchorFollowDialog(mActivity, mLiveRoomBean.getUserData(), new DialogUtil.SimpleCallback() {
                            @Override
                            public void onConfirmClick(Dialog dialog, String content) {
                                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                                    if (mLiveRoomBean.getUserData().getIs_attention() == 0) {
                                        doFollow();
                                    }
                                } else {
                                    loginDialog.show();
                                }
                            }
                        });
                        tv_content.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 6000);
                    }
                }
            }
            if (bean.getInfo() != null) {
                if (!TextUtils.isEmpty(bean.getInfo().getPull())) {
                    //????????????????????????
                    playerView.play(bean.getInfo().getPull());
                }
            }
            if (bean.getUserData() != null && !TextUtils.isEmpty(bean.getUserData().getTitle())) {
//                playerView.updateTitle(bean.getUserData().getTitle());
                tv_title.setText(bean.getUserData().getTitle());
            }
            liveDetailMainFragment.updateFollowData();
            GlideUtil.loadUserImageDefault(mActivity,bean.getUserData().getAvatar(),person_head_pic);
        }
    }

    //??????????????????????????????
    @Override
    public void getDataSuccess(FootballDetailBean bean) {
//        if (liveDetailFootballFragment != null) {
//            liveDetailFootballFragment.setData(bean);
//        }
    }

    //??????????????????????????????
    @Override
    public void getDataSuccess(BasketballDetailBean bean) {
//        if (liveDetailBasketballFragment != null) {
//            liveDetailBasketballFragment.setData(bean);
//        }
    }

    @Override
    public void doFollowSuccess() {
        if (mLiveRoomBean != null && mLiveRoomBean.getUserData() != null) {
            int attention = mLiveRoomBean.getUserData().getAttention();
            if (mLiveRoomBean.getUserData().getIs_attention() == 0) {
                mLiveRoomBean.getUserData().setIs_attention(1);
                attention++;
            } else {
                mLiveRoomBean.getUserData().setIs_attention(0);
                attention--;
            }
            mLiveRoomBean.getUserData().setAttention(attention);
            liveDetailMainFragment.updateFollowData();
        }
    }

    @Override
    public void getDataSuccess(UserBean bean) {
        if (bean != null) {
            CommonAppConfig.getInstance().saveUserInfo(JSONObject.toJSONString(bean));
        }
    }

    @Override
    public void sendBgDanmuSuccess(int id, int anchorId, int level, String text, String msg) {
        NormalMsgBean msgBean = new NormalMsgBean();
        msgBean.setIsXCBarrage(1);
        msgBean.setXcBarrageType(level);
        msgBean.setText(text);
        if (anchorId == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
            msgBean.setIs_room(1);
        } else {
            msgBean.setIs_room(0);
        }
        msgBean.setIs_guard(CommonAppConfig.getInstance().getUserBean().getIs_guard());
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getExp_icon())) {
            msgBean.setExp_icon(CommonAppConfig.getInstance().getUserBean().getExp_icon());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon())) {
            msgBean.setGuard_icon(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon());
        }
        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_BG_DANMU);
        customMsgBean.setNormal(msgBean);
        MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
        messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
        messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
        messageInfo.setSelf(true);
        messageInfo.setRead(true);
        V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (playerView != null) {
                            playerView.addDanmu(msgBean.getText(), msgBean.getXcBarrageType(), messageInfo.isSelf());
                        }
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(msgBean.getGuard_icon(), msgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
    }

    @Override
    public void sendBroadcastResponse(boolean isSuccess, String msg) {
        if (isSuccess) {
            ToastUtil.show(getString(R.string.tip_send_broadcast_success));
        } else {
            ToastUtil.show(msg);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_data:
                toggleMenu();
                break;
            case R.id.iv_back:
                if (mIsFullScreen) {
                    playerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
                } else {
                    backAction();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerView.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING
                || playerView.getPlayerState() == SuperPlayerDef.PlayerState.PAUSE) {
            if (playerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FLOAT) {
                playerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
            }
        }
        if (playerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) {
            //?????????????????????????????????
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            //???????????????
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                decorView.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
        if (playerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            playerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            playerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (playerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            playerView.resetPlayer();
        }
//        if (svga_gift != null) {
//            svga_gift.pauseAnimation();
//            svga_gift = null;
//        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //????????????????????????
        LPAnimationManager.release();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendDanmuEvent(SendDanmuEvent event) {
        if (event != null) {
            if (!TextUtils.isEmpty(event.text)) {
                sendMessage(event.text);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenNobleSuccessEvent(OpenNobleSuccessEvent event) {
        if (event != null) {
            mvpPresenter.getUserInfo();
            liveDetailMainFragment.updateData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAnchorFollowEvent(UpdateAnchorFollowEvent event) {
        if (event != null) {
            if (mLiveRoomBean != null && mLiveRoomBean.getUserData() != null) {
                int attention = mLiveRoomBean.getUserData().getAttention();
                if (mLiveRoomBean.getUserData().getIs_attention() == 0) {
                    mLiveRoomBean.getUserData().setIs_attention(1);
                    attention++;
                }
                mLiveRoomBean.getUserData().setAttention(attention);
                liveDetailMainFragment.updateFollowData();
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mIsFullScreen) {
                playerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
            } else {
                backAction();
            }
            return true;
        }
        //????????????????????????????????????
        return super.onKeyUp(keyCode, event);
    }

    private void backAction() {
        if (SpUtil.getInstance().getBooleanValue(SpUtil.FLOATING_PLAY)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0???????????????????????????
                if (Settings.canDrawOverlays(this)) {
                    playerView.switchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
                } else {
                    finish();
                }
            } else {
                if (playerView.checkOp(this, 24)) {
                    playerView.switchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
                } else {
                    finish();
                }
            }
        } else {
            finish();
        }
    }

    private void toggleMenu() {
        iv_data.setSelected(!iv_data.isSelected());
        if (iv_data.isSelected()) {
            slideRightToLeft();
        } else {
            slideLeftToRight();
        }
    }

    private void slideRightToLeft() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fl_menu, "translationX", mWindowsWidth, 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();
//        ValueAnimator animator = ValueAnimator.ofFloat(mWindowsWidth, 0);
//        animator.setDuration(500);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int width = (int) animation.getAnimatedValue();
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                params.leftMargin = width;
//                fl_menu.setLayoutParams(params);
//            }
//        });
//        animator.start();
    }

    private void slideLeftToRight() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fl_menu, "translationX", 0, mWindowsWidth);
        objectAnimator.setDuration(500);
        objectAnimator.start();
//        ValueAnimator animator = ValueAnimator.ofFloat(0, mWindowsWidth);
//        animator.setDuration(500);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int width = (int) animation.getAnimatedValue();
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                params.leftMargin = width;
//                fl_menu.setLayoutParams(params);
//            }
//        });
//        animator.start();
    }

    public void addDanmu(MessageInfo messageInfo) {
        if (messageInfo != null) {
            if (messageInfo.getExtra() != null) {
                CustomMsgBean customMsgBean = JSONObject.parseObject(messageInfo.getExtra().toString(), CustomMsgBean.class);
                if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_COLOR_DANMU) {
                    if (playerView != null) {
                        playerView.addDanmu(customMsgBean.getColor().getText(), customMsgBean.getColor().getColor(), messageInfo.isSelf());
                    }
                } else if (messageInfo.getMsgType() == MessageInfo.MSG_TYPE_BG_DANMU) {
                    if (playerView != null) {
                        if (customMsgBean.getNormal().getIsXCBarrage() == 1) {
                            playerView.addDanmu(customMsgBean.getNormal().getText(), customMsgBean.getNormal().getXcBarrageType(), messageInfo.isSelf());
                        } else {
                            playerView.addDanmu(customMsgBean.getNormal().getText(), "", messageInfo.isSelf());
                        }
                    }
                } else {
                    if (playerView != null) {
                        playerView.addDanmu(String.valueOf(messageInfo.getExtra()), "", messageInfo.isSelf());
                    }
                }
            }
        }
    }

    //??????????????????
    public void sendMessage(String content) {
        NormalMsgBean msgBean = new NormalMsgBean();
        msgBean.setIsXCBarrage(0);
        msgBean.setText(content);
        if (mAnchorId == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
            msgBean.setIs_room(1);
        } else {
            msgBean.setIs_room(0);
        }
        msgBean.setIs_guard(CommonAppConfig.getInstance().getUserBean().getIs_guard());
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getExp_icon())) {
            msgBean.setExp_icon(CommonAppConfig.getInstance().getUserBean().getExp_icon());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon())) {
            msgBean.setGuard_icon(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon());
        }
        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_BG_DANMU);
        customMsgBean.setNormal(msgBean);
        MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
        messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
        messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
        messageInfo.setSelf(true);
        messageInfo.setRead(true);
//        V2TIMManager.getInstance().sendGroupTextMessage(content, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, new V2TIMSendCallback<V2TIMMessage>() {
//            @Override
//            public void onProgress(int i) {
//
//            }
//
//            @Override
//            public void onSuccess(V2TIMMessage v2TIMMessage) {
//                if (playerView != null) {
//                    playerView.addDanmu(String.valueOf(messageInfo.getExtra()), messageInfo.isSelf());
//                }
//                if (liveDetailMainFragment != null) {
//                    liveDetailMainFragment.sendMessage(messageInfo);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
        V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (playerView != null) {
                            playerView.addDanmu(content, "", messageInfo.isSelf());
                        }
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(msgBean.getGuard_icon(), msgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
    }

    //??????????????????
    public void sendGiftMessage(GiftBean giftBean) {
        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_GIFT);
        GiftBean giftMsgBean = new GiftBean();
        giftMsgBean.setGiftname(giftBean.getGiftname());
        giftMsgBean.setGifticon(giftBean.getGifticon());
        giftMsgBean.setSwf(giftBean.getSwf());
        giftMsgBean.setType(giftBean.getType());
        if (mAnchorId == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
            giftMsgBean.setIs_room(1);
        } else {
            giftMsgBean.setIs_room(0);
        }
        giftMsgBean.setIs_guard(CommonAppConfig.getInstance().getUserBean().getIs_guard());
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getExp_icon())) {
            giftMsgBean.setExp_icon(CommonAppConfig.getInstance().getUserBean().getExp_icon());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon())) {
            giftMsgBean.setGuard_icon(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon());
        }
        customMsgBean.setGift(giftMsgBean);
        MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
        messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
        messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
        messageInfo.setSelf(true);
        messageInfo.setRead(true);
        V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(giftMsgBean.getGuard_icon(), giftMsgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
    }

    //????????????????????????
    public void sendColorMessage(ColorMsgBean msgBean) {
        if (mAnchorId == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
            msgBean.setIs_room(1);
        } else {
            msgBean.setIs_room(0);
        }
        msgBean.setIs_guard(CommonAppConfig.getInstance().getUserBean().getIs_guard());
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getExp_icon())) {
            msgBean.setExp_icon(CommonAppConfig.getInstance().getUserBean().getExp_icon());
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon())) {
            msgBean.setGuard_icon(CommonAppConfig.getInstance().getUserBean().getGuard().getIcon());
        }
        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_COLOR_DANMU);
        customMsgBean.setColor(msgBean);
        MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
        messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
        messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
        messageInfo.setSelf(true);
        messageInfo.setRead(true);
        V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (playerView != null) {
                            playerView.addDanmu(msgBean.getText(), msgBean.getColor(), messageInfo.isSelf());
                        }
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(msgBean.getGuard_icon(), msgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
    }

    //????????????????????????
    public void sendBgMessage(int id, int anchorId, int level, String text) {
        //??????????????????????????????????????????
        mvpPresenter.sendBgDanmu(id, anchorId, level, text);
    }

    public void sendBroadcast(String content) {
        mvpPresenter.sendBroadcast(content);
    }

    /**
     * ??????GIF??????
     */
//    public void startGif(GiftBean giftBean, String nickname, String avatar) {
//        //?????????????????????????????????
//        if (giftBean.getType() == 1 && !TextUtils.isEmpty(giftBean.getSwf())) {
//            if (CommonAppConfig.getInstance().getBlockFunctionInfo() != null) {
//                if (!CommonAppConfig.getInstance().getBlockFunctionInfo().isBlockGift()) {
//                    SVGAParser parser = new SVGAParser(this);
//                    try {
//                        URL url = new URL(giftBean.getSwf());
//                        parser.parse(url, new SVGAParser.ParseCompletion() {
//                            @Override
//                            public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
//                                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
//                                svga_gift.setImageDrawable(drawable);
//                                svga_gift.startAnimation();
//                            }
//
//                            @Override
//                            public void onError() {
//                            }
//                        });
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        //????????????????????????
//        showGiftAnim(giftBean, nickname, avatar);
//    }

    /**
     * ????????????????????????
     */
    private void showGiftAnim(GiftBean giftBean, String nickname, String avatar) {
        AnimMessage animMessage = new AnimMessage();
        animMessage.userName = nickname;
        animMessage.headUrl = avatar;
        animMessage.giftImgUrl = giftBean.getGifticon();
        animMessage.giftSvgaUrl = giftBean.getSwf();
        animMessage.giftNum = 1;
        animMessage.giftName = giftBean.getGiftname();
        animMessage.giftType = "1";
        LPAnimationManager.addAnimalMessage(animMessage);
    }

    /**
     * ????????????????????????
     */
    public void showNobleAnim(String nickname, String mountName, String mountUrl) {
        if (CommonAppConfig.getInstance().getBlockFunctionInfo() != null) {
            if (!CommonAppConfig.getInstance().getBlockFunctionInfo().isBlockNoble()) {
                AnimMessage animMessage = new AnimMessage();
                animMessage.userName = nickname;
                animMessage.giftSvgaUrl = mountUrl;
                animMessage.giftName = mountName;
                LPNobleView lpNobleView = new LPNobleView(this, animMessage);
                int childCount = ll_noble_container.getChildCount();
                if (childCount >= 2) {
                    ll_noble_container.removeViewAt(0);
                }
                ll_noble_container.addView(lpNobleView);
            }
        }
    }

    public void removeNobleAnim(LPNobleView view) {
        ll_noble_container.removeView(view);
    }

    public void setPeopleCount() {
        V2TIMManager.getGroupManager().getGroupOnlineMemberCount(mGroupId, new V2TIMValueCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                playerView.setPeopleCount(String.valueOf(count));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /***********************????????????start***********************/
    private ViewGroup fl_float_view;
    private ViewGroup view_broadcast;
    private TextView tv_content;
    //????????????
    private List<BroadcastMsgBean> broadcastMsgBeans = new ArrayList<>();
    /**
     * ?????????????????????
     */
    private boolean isAnimating = false;

    public void receiveBroadcast(BroadcastMsgBean msgBean) {
        if (msgBean != null) {
            broadcastMsgBeans.add(msgBean);
            if (!isAnimating) {
                startAnim(msgBean);
            }
        }
    }

    private void startAnim(BroadcastMsgBean msgBean) {
        fl_float_view.setVisibility(View.VISIBLE);
        isAnimating = true;
        if (!TextUtils.isEmpty(msgBean.getContent())) {
            tv_content.setText(msgBean.getContent());
        } else {
            tv_content.setText("");
        }
        //????????????
        int screenWidth = ScreenUtils.getScreenWidth(this);

        ObjectAnimator enter = ObjectAnimator.ofFloat(view_broadcast, "translationX", screenWidth, 0);
        enter.setDuration(500);

        ObjectAnimator stay = ObjectAnimator.ofFloat(view_broadcast, "translationX", 0, 0);
        stay.setDuration(2000);

        ObjectAnimator exit = ObjectAnimator.ofFloat(view_broadcast, "translationX", 0, -screenWidth);
        exit.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(enter, stay, exit);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fl_float_view.setVisibility(View.GONE);
                broadcastMsgBeans.remove(msgBean);
                isAnimating = false;
                if (broadcastMsgBeans.size() > 0) {
                    startAnim(broadcastMsgBeans.get(0));
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
    /***********************????????????end***********************/
}
