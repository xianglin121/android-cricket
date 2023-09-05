package com.onecric.live.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.HttpConstant;
import com.onecric.live.R;
import com.onecric.live.fragment.CricketFantasyFragment;
import com.onecric.live.fragment.CricketInfoFragment;
import com.onecric.live.fragment.CricketLiveFragment;
import com.onecric.live.fragment.CricketScorecardFragment2;
import com.onecric.live.fragment.CricketSquadFragment;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.SubscribeTypeBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.presenter.cricket.CricketDetailPresenter;
import com.onecric.live.presenter.match.SubscribePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.CricketDetailView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 * 比赛详情页
 */
public class CricketDetailActivity extends MvpActivity<CricketDetailPresenter> implements CricketDetailView, View.OnClickListener {

    private View hor_line;
    private TextView tv_video;

    public static void forward(Context context, int matchId) {
        Intent intent = new Intent(context, CricketDetailActivity.class);
        intent.putExtra("matchId", matchId);
        context.startActivity(intent);
    }

    public static void forward(Context context, int matchId,int actionType) {
        Intent intent = new Intent(context, CricketDetailActivity.class);
        intent.putExtra("matchId", matchId);
        intent.putExtra("actionType", actionType);
        context.startActivity(intent);
    }

    private int mMatchId;
    private FrameLayout mFlWebview1;
    private WebView mWvAnimation;
    private FrameLayout mFlWebview2;
    private WebView mWvVideo;
    private LinearLayout ll_content;
    //已开始
    private ViewGroup cl_one;
    private TextView tv_home_name;
    private ImageView iv_home_logo;
    private TextView tv_home_score;
    private TextView tv_home_round;
    private TextView tv_away_name;
    private ImageView iv_away_logo;
    private TextView tv_away_score;
    private TextView tv_away_round;
    private TextView tv_desc;
    //未开始
    private ViewGroup cl_two;
    private TextView tv_home_name_two;
    private ImageView iv_home_logo_two;
    private TextView tv_center;
    private ImageView iv_away_logo_two;
    private TextView tv_away_name_two;
    private TextView tv_desc_two;
    private TabLayout tabLayout;
    public ViewPager mViewPager;
    public LinearLayout ll_top;
    private List<Fragment> mViewList;

    private CricketMatchBean mModel;
    private String tab;
    private ImageView iv_subscribe;

    private int actionType = 0;

    public LoginDialog loginDialog;
    private WebView webview;
    private WebSettings webSettings;
    private Timer mTimer;

    private ImageView iv_avatar;
    private LinearLayout ll_title;
    private TextView tv_title;

    @Override
    public boolean getStatusBarTextColor() {
        return false;
    }

    @Override
    protected CricketDetailPresenter createPresenter() {
        return new CricketDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cricket_detail;
    }

    @Override
    protected void initView() {
        //scheme
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String id = uri.getQueryParameter("id");
                mMatchId = Integer.parseInt(id);
                tab = uri.getQueryParameter("tab");
            }
        }else{
            mMatchId = getIntent().getIntExtra("matchId", 0);
            actionType = getIntent().getIntExtra("actionType",0);
        }



        ll_top = findViewById(R.id.ll_top);
        hor_line = findViewById(R.id.hor_line);
        tv_video = findViewById(R.id.tv_video);
        ll_content = findViewById(R.id.ll_content);
        cl_one = findViewById(R.id.cl_one);
        tv_home_name = findViewById(R.id.tv_home_name);
        iv_home_logo = findViewById(R.id.iv_home_logo);
        tv_home_score = findViewById(R.id.tv_home_score);
        tv_home_round = findViewById(R.id.tv_home_round);
        tv_away_name = findViewById(R.id.tv_away_name);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        tv_away_score = findViewById(R.id.tv_away_score);
        tv_away_round = findViewById(R.id.tv_away_round);
        tv_desc = findViewById(R.id.tv_desc);
        cl_two = findViewById(R.id.cl_two);
        tv_home_name_two = findViewById(R.id.tv_home_name_two);
        iv_home_logo_two = findViewById(R.id.iv_home_logo_two);
        tv_center = findViewById(R.id.tv_center);
        iv_away_logo_two = findViewById(R.id.iv_away_logo_two);
        tv_away_name_two = findViewById(R.id.tv_away_name_two);
        tv_desc_two = findViewById(R.id.tv_desc_two);
        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mFlWebview1 = findViewById(R.id.fl_webview1);
        mFlWebview2 = findViewById(R.id.fl_webview2);
        mWvAnimation = findViewById(R.id.wv_animation);
        mWvVideo = findViewById(R.id.wv_video);
        iv_avatar = findViewById(R.id.iv_avatar);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);

        ll_title.setVisibility(View.VISIBLE);

        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
        } else {
            iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
        }

        findViewById(R.id.tv_animation).setOnClickListener(this);
        findViewById(R.id.tv_video).setOnClickListener(this);
        findViewById(R.id.iv_back_three).setOnClickListener(this);
        findViewById(R.id.iv_back_four).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        ((ImageView) findViewById(R.id.iv_right)).setBackgroundResource(R.mipmap.icon_share2);
        ((ImageView) findViewById(R.id.iv_right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(mActivity, "", HttpConstant.CRICKET_DETAIL_URL + mMatchId);
            }
        });
        iv_subscribe = findViewById(R.id.iv_subscribe);
        initWebView();
        loginDialog = new LoginDialog(this, R.style.dialog, true, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });
    }

    @Override
    protected void initData() {
        /*tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fantasy)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.info)));
        //判断是否未开赛
        if(mModel.getStatus() == 0){
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.live)));
        }

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.scorecard)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.highlights)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.updates)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.squad)));

        mViewList = new ArrayList<>();
        mViewList.add(CricketFantasyFragment.newInstance());
        mViewList.add(CricketInfoFragment.newInstance(mMatchId));
        if(mModel.getStatus() == 0){
            mViewList.add(CricketLiveFragment.newInstance(mMatchId));
        }
        mViewList.add(CricketScorecardFragment2.newInstance());
//        mViewList.add(CricketFantasyFragment.newInstance());
//        mViewList.add(CricketUpdatesFragment.newInstance());
        mViewList.add(CricketSquadFragment.newInstance());

        initViewPager();
        mvpPresenter.getDetail(mMatchId);*/
//        mvpPresenter.getUpdatesDetail(mMatchId);
        mvpPresenter.getDetail(mMatchId);
    }

    private void initViewPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

        if(actionType == 2){
            tabLayout.getTabAt(2).select();
        }else if(!TextUtils.isEmpty(tab) && tab.equals("score")){
            tabLayout.getTabAt(mModel.getStatus() != 0?3:2).select();
        }

    }

    @Override
    public void getDataSuccess(CricketMatchBean model) {
        if (model != null) {
            mModel = model;

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fantasy)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.info)));
            //未开赛无live
            if(mModel.getStatus() != 0){
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.live)));
            }

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.scorecard)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.highlights)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.updates)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.squad)));

            mViewList = new ArrayList<>();
            mViewList.add(CricketFantasyFragment.newInstance());
            mViewList.add(CricketInfoFragment.newInstance(mMatchId));
            if(mModel.getStatus() != 0){
                mViewList.add(CricketLiveFragment.newInstance(mMatchId));
            }
            mViewList.add(CricketScorecardFragment2.newInstance());
//        mViewList.add(CricketFantasyFragment.newInstance());
//        mViewList.add(CricketUpdatesFragment.newInstance());
            mViewList.add(CricketSquadFragment.newInstance());

            initViewPager();


            if(!TextUtils.isEmpty(model.tournamentName)){
                tv_title.setText(model.tournamentName);
            }
            if (mModel.getStatus() != 1) {
                tv_video.setVisibility(View.GONE);
                hor_line.setVisibility(View.GONE);
//                ll_top.setVisibility(View.GONE);
//                ll_content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(70)));
            }else if(actionType == 1){
                mFlWebview1.setVisibility(View.GONE);
                mFlWebview2.setVisibility(View.VISIBLE);
                ll_content.setVisibility(View.GONE);
            }
            ((CricketFantasyFragment) mViewList.get(0)).getData(mMatchId, model.getHome_name(), model.getHome_logo(), model.getAway_name(), model.getAway_logo());
            if (!TextUtils.isEmpty(model.getTournament_id())) {
                ((CricketInfoFragment) mViewList.get(1)).getList(model.getHome_id(), model.getAway_id(), Integer.valueOf(model.getTournament_id()));
            }

            ((CricketSquadFragment) mViewList.get(mModel.getStatus() != 0?4:3)).getList(model);
            if(mModel.getStatus() != 0){
                ((CricketLiveFragment) mViewList.get(2)).getData(model.getMatch_id());
            }
            if (model.getStatus() == 0) {
                cl_one.setVisibility(View.GONE);
                cl_two.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(model.getHome_name())) {
                    tv_home_name_two.setText(model.getHome_name());
                }
                GlideUtil.loadTeamImageDefault(this, model.getHome_logo(), iv_home_logo_two);
                if (!TextUtils.isEmpty(model.getAway_name())) {
                    tv_away_name_two.setText(model.getAway_name());
                }
                GlideUtil.loadTeamImageDefault(this, model.getAway_logo(), iv_away_logo_two);
                String strOne = getString(R.string.match_starts_in);
                if (!TextUtils.isEmpty(model.getLive_time())) {
                    String strTwo = model.getLive_time();
                    SpannableStringBuilder builder = new SpannableStringBuilder(strOne + strTwo);
                    builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_DC3C23)), strOne.length(), (strOne.length() + strTwo.length()), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tv_center.setText(builder);
                } else {
                    tv_center.setText(strOne);
                }
                String str = "";
                if (!TextUtils.isEmpty(model.getMatch_result())) {
                    str = model.getMatch_result() + "\n";
                }
                if (!TextUtils.isEmpty(model.getVenue_name())) {
                    str = str + model.getVenue_name();
                }
                tv_desc_two.setText(str);
            } else {
                cl_one.setVisibility(View.VISIBLE);
                cl_two.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(model.getHome_name())) {
                    tv_home_name.setText(model.getHome_name());
                }
                GlideUtil.loadTeamImageDefault(this, model.getHome_logo(), iv_home_logo);
                if (!TextUtils.isEmpty(model.getHome_display_score())) {
                    tv_home_score.setText(model.getHome_display_score());
                }
                if (!TextUtils.isEmpty(model.getHome_display_overs())) {
                    tv_home_round.setText("(" + model.getHome_display_overs() + ")");
                }
                if (!TextUtils.isEmpty(model.getAway_name())) {
                    tv_away_name.setText(model.getAway_name());
                }
                GlideUtil.loadTeamImageDefault(this, model.getAway_logo(), iv_away_logo);
                if (!TextUtils.isEmpty(model.getAway_display_score())) {
                    tv_away_score.setText(model.getAway_display_score());
                }
                if (!TextUtils.isEmpty(model.getAway_display_overs())) {
                    tv_away_round.setText("(" + model.getAway_display_overs() + ")");
                }
                if (!TextUtils.isEmpty(model.getMatch_result())) {
                    tv_desc.setText(model.getMatch_result());
                }
            }

            if (mModel.getStatus() == 2) {//已结束
                iv_subscribe.setVisibility(View.GONE);
            } else {
                iv_subscribe.setVisibility(View.VISIBLE);
                if (mModel.getIs_subscribe() == 1) {//已经订阅过了
                    iv_subscribe.setImageResource(R.mipmap.subscribe);
                } else {
                    iv_subscribe.setImageResource(R.mipmap.unsubscribe);
                }

                iv_subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                            ToastUtil.show(mActivity.getString(R.string.please_login));
/*                            if (loginDialog != null) {
                                loginDialog.show();
                            }*/
                            OneLogInActivity.forward(mActivity);
                            return;
                        }
                        getSubscribeType();
                    }
                });
            }

            //初始化动画直播地址
            initWebViewOne(mModel.getLive_path());
            //初始化视频直播地址
            initWebViewTwo(mModel.getLive_url());
            //请求记分卡数据
            ((CricketScorecardFragment2) mViewList.get(model.getStatus() != 0?3:2)).getData(mModel);

            if(mModel.getStatus() != 2){
                refreshTodayData();
            }
        }
    }

    private void getSubscribeType() {//订阅推送消息
        showLoadingDialog();
        new SubscribePresenter().getSubscribeType(mModel.getMatch_id(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                dismissLoadingDialog();
                if (data != null) {
                    List<SubscribeTypeBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), SubscribeTypeBean.class);
                    //这里先弹出一个订阅消息的内容选择框  待选择好后点击确定订阅按钮再调用订阅接口
                    DialogUtil.showSelectSubscribeDialog(mActivity, mModel.getHome_name() + " VS " + mModel.getAway_name(), list, new DialogUtil.SelectSubscribeBack() {
                        @Override
                        public void onSelectSubscribe(String type) {
                            doSubscribe(mModel.getMatch_id() + "", type, iv_subscribe);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {
                dismissLoadingDialog();
            }

            @Override
            public void onError(String msg) {
                dismissLoadingDialog();
            }

            @Override
            public void onFinish() {
                dismissLoadingDialog();
            }
        });
    }

    private void doSubscribe(String matchId, String type, ImageView subscribeIv) {//订阅推送消息
        new SubscribePresenter().doSubscribe(matchId, type, new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                if (!TextUtils.isEmpty(type)) {
                    subscribeIv.setImageResource(R.mipmap.subscribe);
                } else {
                    subscribeIv.setImageResource(R.mipmap.unsubscribe);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onError(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void getUpdatesDataSuccess(List<UpdatesBean> list) {
//        ((CricketUpdatesFragment) mViewList.get(4)).setData(list);
    }

    private void initWebViewOne(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        /* 设置支持Js */
        mWvAnimation.getSettings().setJavaScriptEnabled(true);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWvAnimation.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        /* 设置缓存模式 */
        mWvAnimation.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWvAnimation.getSettings().setDomStorageEnabled(true);

        /* 设置为使用webview推荐的窗口 */
//        mWebView.getSettings().setUseWideViewPort(true);
        /* 设置为使用屏幕自适配 */
        mWvAnimation.getSettings().setLoadWithOverviewMode(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWvAnimation.getSettings().setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWvAnimation.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWvAnimation.getSettings().setGeolocationEnabled(true);
        /* 设置可以访问文件 */
        mWvAnimation.getSettings().setAllowFileAccess(true);

        // 设置UserAgent标识
//        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " app-shikuimapp");
        mWvAnimation.loadUrl(url);
    }

    private void initWebViewTwo(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        /* 设置支持Js */
        mWvVideo.getSettings().setJavaScriptEnabled(true);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWvVideo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        /* 设置缓存模式 */
        mWvVideo.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWvVideo.getSettings().setDomStorageEnabled(true);

        /* 设置为使用webview推荐的窗口 */
//        mWebView.getSettings().setUseWideViewPort(true);
        /* 设置为使用屏幕自适配 */
        mWvVideo.getSettings().setLoadWithOverviewMode(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWvVideo.getSettings().setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWvVideo.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWvVideo.getSettings().setGeolocationEnabled(true);
        /* 设置可以访问文件 */
        mWvVideo.getSettings().setAllowFileAccess(true);

        // 设置UserAgent标识
//        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " app-shikuimapp");
        mWvVideo.loadUrl(url);
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_animation:
                if (mModel != null) {
                    mFlWebview1.setVisibility(View.VISIBLE);
                    mFlWebview2.setVisibility(View.GONE);
                    ll_content.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_video:
                if (mModel != null) {
                    mFlWebview1.setVisibility(View.GONE);
                    mFlWebview2.setVisibility(View.VISIBLE);
                    ll_content.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_back_three:
            case R.id.iv_back_four:
                mFlWebview1.setVisibility(View.GONE);
                mFlWebview2.setVisibility(View.GONE);
                ll_content.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWvAnimation != null) {
            mWvAnimation.destroy();
        }
        if (mWvVideo != null) {
            mWvVideo.destroy();
        }
        if(mTimer != null){
            mTimer.cancel();
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
//                                dialog.show();
/*                                loginDialog.show();
                                loginDialog.passWebView();*/
                                OneLogInActivity.forward(mActivity);
                            }
                        });
                    }
                }
            }
        }, 500);
    }

    /**
     * 每10s刷新
     */
    private void refreshTodayData(){
        if(mModel.getStatus() != 0){
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //Live页面，刷新最新数据(未息屏、在前台
                    if(tabLayout.getSelectedTabPosition() == 2 &&
                            mModel!=null &&
                            ((PowerManager)getSystemService(Context.POWER_SERVICE)).isScreenOn() &&
                            getWindow().getDecorView().getVisibility() == View.VISIBLE ){
                        ((CricketLiveFragment) mViewList.get(2)).getData(mModel.getMatch_id());
                    }
                }
            }, 10000, 5000);
        }
    }

}
