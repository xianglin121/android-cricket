package com.onecric.live.activity;

import static com.onecric.live.HttpConstant.SHARE_LIVE_URL;
import static com.onecric.live.util.DateUtil.getRelativeLocalDate;
import static com.onecric.live.util.UiUtils.collapseView;
import static com.onecric.live.util.UiUtils.convertViewToBitmap;
import static com.onecric.live.util.UiUtils.createQrCode;
import static com.onecric.live.util.UiUtils.expandView;
import static com.onecric.live.util.UiUtils.saveBitmapFile;
import static com.onecric.live.util.UiUtils.sharePictureFile;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.coorchice.library.SuperTextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.custom.gift.AnimMessage;
import com.onecric.live.custom.gift.LPAnimationManager;
import com.onecric.live.custom.noble.LPNobleView;
import com.onecric.live.event.UpdateAnchorFollowEvent;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.fragment.LiveDetailMainFragment;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.BasketballDetailBean;
import com.onecric.live.model.BroadcastMsgBean;
import com.onecric.live.model.ColorMsgBean;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.GiftBean;
import com.onecric.live.model.LiveRoomBean;
import com.onecric.live.model.NormalMsgBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.live.LiveDetailPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.DownloadUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ScreenUtils;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveDetailView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
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

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

/**
 * LIVE直播详情
 */
public class LiveNotStartDetailActivity extends MvpActivity<LiveDetailPresenter> implements LiveDetailView, View.OnClickListener {

    public static void forward(Context context, int anchorId, int matchId,int liveId) {
        Intent intent = new Intent(context, LiveNotStartDetailActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("matchId", matchId);
        intent.putExtra("liveId", liveId);
        context.startActivity(intent);
    }

    private String mGroupId;
    private int mAnchorId,mLiveId;
    private int mMatchId;
    private LiveDetailMainFragment liveDetailMainFragment;
    private LinearLayout ll_noble_container;

    public LiveRoomBean mLiveRoomBean;

    private LoginDialog loginDialog,constraintLoginDialog;
    private WebView webview;
    private WebSettings webSettings;

    private TextView tv_title;
    private ImageView iv_back;
    private CircleImageView person_head_pic;

    private ConstraintLayout cl_avatar;
    private CircleImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_desc;
    private ImageView iv_star;
    private TextView tv_tool_eyes;
    private ImageView iv_tool_heart;
    private ImageView iv_tool_share;
    private TextView tv_tool_heart;

    private boolean isOpenAvatar = false;
    private int clAvatarHeight;

    private Drawable drawableArrUp, drawableArrDown;
    private ImageView iv_cover;
    private TextView tv_time;
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    private LinearLayout ll_main;
    @Override
    protected LiveDetailPresenter createPresenter() {
        return new LiveDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_detail_not_start;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //scheme
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String aId = uri.getQueryParameter("anchorId");
                String mid = uri.getQueryParameter("matchId");
                String lid = uri.getQueryParameter("liveId");
                mAnchorId = Integer.parseInt(aId);
                mMatchId = Integer.parseInt(mid);
                mLiveId = Integer.parseInt(lid);
            }
        }else{
            mAnchorId = getIntent().getIntExtra("anchorId", 0);
            mMatchId = getIntent().getIntExtra("matchId", 0);
            mLiveId = getIntent().getIntExtra("liveId", 0);
        }

        mGroupId = String.valueOf(mAnchorId);
        mvpPresenter.setGroupId(mGroupId);

        //获取屏幕宽度
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        mWindowsWidth = metric.widthPixels;

        init();

        drawableArrUp = getResources().getDrawable(R.mipmap.icon_arrow_up_up);
        drawableArrUp.setTint(getResources().getColor(R.color.c_959697));
        drawableArrUp.setBounds(0, 0, drawableArrUp.getMinimumWidth(),drawableArrUp.getMinimumHeight());
        drawableArrDown = getResources().getDrawable(R.mipmap.icon_arrow_down);
        drawableArrDown.setTint(getResources().getColor(R.color.c_959697));
        drawableArrDown.setBounds(0, 0, drawableArrDown.getMinimumWidth(),drawableArrDown.getMinimumHeight());

        //视频尺寸
//        int width = UIUtil.getScreenWidth(this);
//        ViewGroup.LayoutParams pp = playerView.getLayoutParams();
//        pp.height = (int)(width * 0.5625);

        initWebView();
        loginDialog = new LoginDialog(this, R.style.dialog,true, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });

        constraintLoginDialog = new LoginDialog(this, R.style.dialog,false, () -> {
            constraintLoginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });

        //初始化fragment
        liveDetailMainFragment = LiveDetailMainFragment.newInstance(mGroupId, mAnchorId,mMatchId);
        liveDetailMainFragment.isNotStart = true;
        liveDetailMainFragment.setLoginDialog(loginDialog);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, liveDetailMainFragment).commitAllowingStateLoss();

        clAvatarHeight = UIUtil.dip2px(this,70);

    }

    private void init(){
        ll_noble_container = findViewById(R.id.ll_noble_container);
        view_broadcast = findViewById(R.id.view_broadcast);
        fl_float_view = findViewById(R.id.fl_float_view);
        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        person_head_pic = findViewById(R.id.person_head_pic);
        cl_avatar = findViewById(R.id.cl_avatar);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_desc = findViewById(R.id.tv_desc);
        iv_star = findViewById(R.id.iv_star);
        tv_tool_eyes = findViewById(R.id.tv_tool_eyes);
        iv_tool_heart = findViewById(R.id.iv_tool_heart);
        iv_tool_share = findViewById(R.id.iv_tool_share);
        tv_tool_heart = findViewById(R.id.tv_tool_heart);
        iv_cover = findViewById(R.id.iv_cover);
        tv_time = findViewById(R.id.tv_time);
        ll_main = findViewById(R.id.ll_main);
        findViewById(R.id.ll_eyes).setOnClickListener(this);
        findViewById(R.id.ll_heart).setOnClickListener(this);
        findViewById(R.id.ll_title).setOnClickListener(this);
        iv_back.setOnClickListener(this);
        person_head_pic.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
        iv_star.setOnClickListener(this);
        iv_tool_heart.setOnClickListener(this);
        iv_tool_share.setOnClickListener(this);
    }


    //登录成功，更新信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            mvpPresenter.getInfo(true,mLiveId);
        }
    }

    @SuppressLint("JavascriptInterface")
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
        mvpPresenter.getInfo(false,mLiveId);
    }

    public void doFollow() {
        mvpPresenter.doFollow(mAnchorId);
    }

    //直播间详情
    @Override
    public void getDataSuccess(LiveRoomBean bean) {
        if (bean != null) {
            mLiveRoomBean = bean;
            initShareScreen();
            mMatchId = bean.getInfo().getMatch_id();
            tv_time.setText("Watch live at "+getRelativeLocalDate(sfdate2,bean.getInfo().getStarttime()));
            GlideUtil.loadLiveImageDefault(mActivity, mLiveRoomBean.getInfo().getThumb(), iv_cover);
            if (bean.getUserData() != null && !TextUtils.isEmpty(bean.getUserData().getTitle())) {
                tv_title.setText(bean.getUserData().getTitle());
                iv_star.setSelected(bean.getUserData().getIs_attention() == 0 ? false : true);
                tv_name.setText(bean.getUserData().getUser_nickname());
                tv_desc.setText("Fans: " +bean.getUserData().getAttention());
                int heatNum = bean.getUserData().getHeat();

                tv_tool_eyes.setText(heatNum>1000 ? String.format("%.1f",(float)heatNum/1000) + "K" :heatNum+"");
                if (bean.getInfo().getIs_like() == 1) {
                    iv_tool_heart.setSelected(true);
                } else {
                    iv_tool_heart.setSelected(false);
                }
                int likeNum = bean.getInfo().getLike_num();
                tv_tool_heart.setText(likeNum>1000 ? String.format("%.1f",(float)likeNum/1000) + "K" :likeNum+"");
            }
            liveDetailMainFragment.updateFollowData(bean);
            GlideUtil.loadUserImageDefault(mActivity, bean.getUserData().getAvatar(), person_head_pic);
            GlideUtil.loadUserImageDefault(mActivity, bean.getUserData().getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid()) && CommonAppConfig.getInstance().getUid().equals(String.valueOf(mAnchorId))) {
                iv_star.setVisibility(View.GONE);
            }
        } else {
            finish();
        }
    }

    @Override
    public void doFollowSuccess() {
        if (mLiveRoomBean != null && mLiveRoomBean.getUserData() != null) {
            int attention = mLiveRoomBean.getUserData().getAttention();
            if (mLiveRoomBean.getUserData().getIs_attention() == 0) {
                mLiveRoomBean.getUserData().setIs_attention(1);
                attention++;
                iv_star.setSelected(true);
            } else {
                mLiveRoomBean.getUserData().setIs_attention(0);
                attention--;
                iv_star.setSelected(false);
            }
            mLiveRoomBean.getUserData().setAttention(attention);
            tv_desc.setText("Fans: "+attention);
            liveDetailMainFragment.updateFollowData(mLiveRoomBean);
        }
    }

    @Override
    public void getDataSuccess(UserBean bean) {
        if (bean != null) {
            CommonAppConfig.getInstance().saveUserInfo(JSONObject.toJSONString(bean));
        }
    }

    @Override
    public void getUpdateUserData(LiveRoomBean bean) {
        if (bean != null) {
            mLiveRoomBean = bean;
            if (bean.getUserData() != null && !TextUtils.isEmpty(bean.getUserData().getTitle())) {
                tv_title.setText(bean.getUserData().getTitle());
                iv_star.setSelected(bean.getUserData().getIs_attention() == 0 ? false : true);
                tv_name.setText(bean.getUserData().getUser_nickname());
                tv_desc.setText("Fans: " +bean.getUserData().getAttention());
                int heatNum = bean.getUserData().getHeat();

                tv_tool_eyes.setText(heatNum>1000 ? String.format("%.1f",(float)heatNum/1000) + "K" :heatNum+"");
                if (bean.getInfo().getIs_like() == 1) {
                    iv_tool_heart.setSelected(true);
                } else {
                    iv_tool_heart.setSelected(false);
                }
                int likeNum = bean.getInfo().getLike_num();
                tv_tool_heart.setText(likeNum>1000 ? String.format("%.1f",(float)likeNum/1000) + "K" :likeNum+"");
            }
            liveDetailMainFragment.updateFollowData(mLiveRoomBean);
            GlideUtil.loadUserImageDefault(mActivity, bean.getUserData().getAvatar(), person_head_pic);
            GlideUtil.loadUserImageDefault(mActivity, bean.getUserData().getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid()) && CommonAppConfig.getInstance().getUid().equals(String.valueOf(mAnchorId))) {
                iv_star.setVisibility(View.GONE);
            }
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
    public void getMatchDataSuccess(CricketMatchBean model) {
        if (model != null) {
            liveDetailMainFragment.setMatchData(model);
        }
    }

    @Override
    public void getUpdatesDataSuccess(List<UpdatesBean> list) {
//        liveDetailMainFragment.setUpdateData(list);
    }

    @Override
    public void showLikeSuccess() {
        int likeNum = mLiveRoomBean.getInfo().getLike_num();
        //1喜欢 0取消
        if(mLiveRoomBean.getInfo().getIs_like() == 1){
            mLiveRoomBean.getInfo().setIs_like(0);
            iv_tool_heart.setSelected(false);
            --likeNum;
        }else{
            mLiveRoomBean.getInfo().setIs_like(1);
            iv_tool_heart.setSelected(true);
            ++likeNum;
        }
        tv_tool_heart.setText(likeNum>1000 ? String.format("%.1f",(float)likeNum/1000) + "K" :likeNum+"");
        mLiveRoomBean.getInfo().setLike_num(likeNum);
    }

    @Override
    public void getDataFail(String msg) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.person_head_pic:
            case R.id.iv_avatar:
                if (mLiveRoomBean != null)
//                    PersonalHomepageActivity.forward(LiveDetailActivity.this, mLiveRoomBean.getUserData().getUid() + "");
                break;
            case R.id.ll_title:
                //展开、折叠
                if(cl_avatar.getVisibility() == View.GONE){
                    cl_avatar.setVisibility(View.VISIBLE);
                }
                if(isOpenAvatar){
                    tv_title.setCompoundDrawables(null, null, drawableArrUp,null);
                    expandView(cl_avatar,clAvatarHeight,0);
                }else{
                    tv_title.setCompoundDrawables(null, null, drawableArrDown,null);
                    collapseView(cl_avatar,0,clAvatarHeight);
                }
                isOpenAvatar = !isOpenAvatar;
                break;
            case R.id.iv_star:
                //关注作者
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    if (mLiveRoomBean.getUserData() != null && mLiveRoomBean.getUserData().getIs_attention() == 0) {
                        doFollow();
                    }
                }else if(loginDialog!=null){
                    loginDialog.show();
                }else{
                    ToastUtil.show(getString(R.string.please_login));
                }
                break;
            case R.id.iv_tool_heart:
            case R.id.ll_heart:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    mvpPresenter.goLike(mLiveRoomBean.getInfo().getId(),mLiveRoomBean.getInfo().getIs_like()==1?0:1);
                }else if(loginDialog!=null){
                    loginDialog.show();
                }else{
                    ToastUtil.show(getString(R.string.please_login));
                }
                break;
            case R.id.iv_tool_share:
                shareScreen();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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
                liveDetailMainFragment.updateFollowData(mLiveRoomBean);
            }
        }
    }

    //发送普通消息 弹幕
    public void sendMessage(String content) {
        NormalMsgBean msgBean = new NormalMsgBean();
        msgBean.setIsXCBarrage(0);
        msgBean.setText(content);
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
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
        }


        CustomMsgBean customMsgBean = new CustomMsgBean();
        customMsgBean.setType(MessageInfo.MSG_TYPE_BG_DANMU);
        customMsgBean.setNormal(msgBean);
        MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
        String touristId= CommonAppConfig.getInstance().getVisitorUserId()+"";
        messageInfo.setNickName(!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) ? CommonAppConfig.getInstance().getUserBean().getUser_nickname() : touristId);
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
                        Log.d("发送弹幕","onProgress i="+i);
                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(msgBean.getGuard_icon(), msgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d("发送弹幕","onError i="+i+"----- s="+s);
                    }
                });
    }

    //发送彩色弹幕消息
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
                        if (liveDetailMainFragment != null) {
                            liveDetailMainFragment.sendMessage(msgBean.getGuard_icon(), msgBean.getExp_icon(), messageInfo);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
    }

    //发送炫彩弹幕消息
    public void sendBgMessage(int id, int anchorId, int level, String text) {
        //调接口判断是否能发送炫彩弹幕
        mvpPresenter.sendBgDanmu(id, anchorId, level, text);
    }

    public void sendBroadcast(String content) {
        mvpPresenter.sendBroadcast(content);
    }

    /**
     * 开始GIF动画
     */
//    public void startGif(GiftBean giftBean, String nickname, String avatar) {
//        //展示全屏类型的礼物动画
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
//        //展示礼物进场动画
//        showGiftAnim(giftBean, nickname, avatar);
//    }

    /**
     * 显示礼物进场动画
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
     * 显示贵族进场动画
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


    /***********************喇叭消息start***********************/
    private ViewGroup fl_float_view;
    private ViewGroup view_broadcast;
    private TextView tv_content;
    //喇叭消息
    private List<BroadcastMsgBean> broadcastMsgBeans = new ArrayList<>();
    /**
     * 是否动画进行中
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
        //启动动画
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
    /***********************喇叭消息end***********************/

    //直播间右侧足球数据页
    @Override
    public void getDataSuccess(FootballDetailBean bean) {

    }

    //直播间右侧篮球数据页
    @Override
    public void getDataSuccess(BasketballDetailBean bean) {

    }

    public void getMatchDetail(){
        mvpPresenter.getMatchDetail(mMatchId);
    }


    private Bitmap shareQRCodeBitmap;
    private AlertDialog shareDialog;
    private Bitmap picBitmap;
    private LinearLayout ll_pic;
    private View view1;
    private ImageView ivScreen;
    private void initShareScreen(){
        view1 = mActivity.getLayoutInflater().inflate(R.layout.dialog_share_live,null);
        ImageView ivCode = view1.findViewById(R.id.iv_code);
        ivScreen = view1.findViewById(R.id.iv_screen);
        ll_pic = view1.findViewById(R.id.ll_pic);
        ImageView iv_c = view1.findViewById(R.id.iv_c);
        RelativeLayout sBar = view1.findViewById(R.id.statusBar);
        CircleImageView head_pic = view1.findViewById(R.id.person_head_pic);

        //赋值封面
        android.view.ViewGroup.LayoutParams ppiv_cover = iv_c.getLayoutParams();
        int width = UIUtil.getScreenWidth(mActivity);
        ppiv_cover.height = (int)(width * 0.5625 * 0.8);
        iv_c.setLayoutParams(ppiv_cover);
        GlideUtil.loadLiveImageDefault(mActivity, mLiveRoomBean.getInfo().getThumb(), iv_c);
        //跳过内存缓存 否则得到的是失败图片
        Glide.with(mActivity).load(mLiveRoomBean.getInfo().getThumb()).skipMemoryCache(true).placeholder(R.mipmap.ball_live_bg).error(R.mipmap.ball_live_bg).into(iv_c);
        GlideUtil.loadUserImageDefault(mActivity, mLiveRoomBean.getUserData().getAvatar(), head_pic);

        //生成二维码
        if(shareQRCodeBitmap == null){
            shareQRCodeBitmap = createQrCode(SHARE_LIVE_URL,UIUtil.dip2px(mActivity,35),UIUtil.dip2px(mActivity,35));
        }
        ivCode.setImageBitmap(shareQRCodeBitmap);

//        android.view.ViewGroup.LayoutParams ppivScreen = ivScreen.getLayoutParams();
//        int height = (int) ((float)ll_main.getHeight()/ll_main.getWidth() * dm.widthPixels);
//        ppivScreen.height = height;
//        ivScreen.setLayoutParams(ppivScreen);

        if(shareDialog==null){
            shareDialog = new AlertDialog.Builder(mActivity).setView(view1).create();
            shareDialog.setCancelable(true);
            shareDialog.setCanceledOnTouchOutside(true);
        }

    }
    private void shareScreen(){
        if(shareDialog == null){
            return;
        }
        //拼接截图
        //这种方式有缓存，且短视频和直播源画面空白
        ll_main.setDrawingCacheEnabled(true);
        Bitmap bitmap = ll_main.getDrawingCache();
        ivScreen.setImageBitmap(bitmap);
        shareDialog.setView(view1);

        //展示弹窗
        shareDialog.show();

        ll_pic.setDrawingCacheEnabled(true);
        picBitmap = convertViewToBitmap(ll_pic);

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Window w = shareDialog.getWindow();
        w.setLayout((int) (dm.widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        w.findViewById(R.id.tv_share).setOnClickListener(v -> {
            if(picBitmap==null){
                picBitmap = convertViewToBitmap(ll_pic);
            }
            //分享到第三方
            if(sharePictureFile(mActivity,picBitmap)){
                shareDialog.dismiss();
            }
        });

        w.findViewById(R.id.tv_url).setOnClickListener(v -> {
            //分享链接
            ShareUtil.shareText(mActivity,"",SHARE_LIVE_URL+"pages/Live/live-detail?id="+mAnchorId+"&ID="+mLiveId);
        });

        w.findViewById(R.id.tv_save).setOnClickListener(v -> {
            if(picBitmap==null){
                picBitmap = convertViewToBitmap(ll_pic);
            }
            //保存图片
            if(saveBitmapFile(mActivity,picBitmap)!=null){
                shareDialog.dismiss();
            }
        });

        w.findViewById(R.id.ll_pic).setOnClickListener(v->{
            shareDialog.dismiss();
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10005:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        ToastUtil.show(getString(flag?R.string.start_permission_storage_setting_tip:R.string.start_permission_storage_tip));
                        return;
                    }
                }
                sharePictureFile(mActivity,picBitmap);
                break;
            case 10004:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        ToastUtil.show(getString(flag?R.string.start_permission_storage_setting_tip:R.string.start_permission_storage_tip));
                        return;
                    }
                }
                saveBitmapFile(mActivity,picBitmap);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(shareDialog!=null && shareDialog.isShowing()){
            shareDialog.dismiss();
        }
    }
}
