package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.appbar.AppBarLayout;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.custom.listener.SoftKeyBoardListener;
import com.onecric.live.fragment.FootballMatchIndexFragment;
import com.onecric.live.fragment.FootballMatchLineupFragment;
import com.onecric.live.fragment.FootballMatchLiveFragment;
import com.onecric.live.fragment.FootballMatchStatusFragment;
import com.onecric.live.fragment.MatchChatFragment;
import com.onecric.live.model.CustomMsgBean;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.NormalMsgBean;
import com.onecric.live.model.ReportBean;
import com.onecric.live.presenter.match.FootballMatchDetailPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.match.FootballMatchDetailView;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.liteav.demo.superplayer.MatchPlayerView;
import com.tencent.liteav.demo.superplayer.SuperPlayerDef;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pro.piwik.sdk.extra.TrackHelper;

public class FootballMatchDetailActivity extends MvpActivity<FootballMatchDetailPresenter> implements FootballMatchDetailView, View.OnClickListener {

    public static void forward(Context context, int id) {
        Intent intent = new Intent(context, FootballMatchDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private int mId;
    private AppBarLayout appBarLayout;
    private ConstraintLayout cl_title_one, cl_title_two;
    private ImageView iv_collect;
    private TextView tv_match_name, tv_match_time;
    private ImageView iv_team_logo_one, iv_team_logo_two;
    private TextView tv_team_name_one, tv_team_name_two;
    private TextView tv_team_one_score, tv_team_two_score;
    private TextView tv_state;
    private TextView tv_half_score;
    private TextView tv_title_team_one, tv_title_team_two;
    private TextView tv_title_score_one, tv_title_score_two;
    private TextView tv_title_match_state;
    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    private FrameLayout fl_input;
    private EditText et_input;
    private InputMethodManager imm;
    private RelativeLayout more_groups;
    private FaceFragment mFaceFragment;//表情界面
    private FragmentManager mFragmentManager;
    private String mRoomId;

    private List<ReportBean> mReportList;//举报列表

    private FrameLayout mFlWebview;
    private WebView mWebView;

    private MatchPlayerView mMatchPlayerView;
    private boolean mIsFullScreen;
    private String mHdUrl;//高清地址
    private String mSdUrl;//标清地址
    private String mPlayingUrl;//正在播放地址

    @Override
    protected FootballMatchDetailPresenter createPresenter() {
        return new FootballMatchDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_football_match_detail;
    }

    @Override
    protected void initView() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mId = getIntent().getIntExtra("id", 0);
        appBarLayout = findViewById(R.id.appBarLayout);
        cl_title_one = findViewById(R.id.cl_title_one);
        cl_title_two = findViewById(R.id.cl_title_two);
        iv_collect = findViewById(R.id.iv_collect);
        tv_match_name = findViewById(R.id.tv_match_name);
        tv_match_time = findViewById(R.id.tv_match_time);
        iv_team_logo_one = findViewById(R.id.iv_team_logo_one);
        iv_team_logo_two = findViewById(R.id.iv_team_logo_two);
        tv_team_name_one = findViewById(R.id.tv_team_name_one);
        tv_team_name_two = findViewById(R.id.tv_team_name_two);
        tv_team_one_score = findViewById(R.id.tv_team_one_score);
        tv_team_two_score = findViewById(R.id.tv_team_two_score);
        tv_state = findViewById(R.id.tv_state);
        tv_half_score = findViewById(R.id.tv_half_score);
        tv_title_team_one = findViewById(R.id.tv_title_team_one);
        tv_title_team_two = findViewById(R.id.tv_title_team_two);
        tv_title_score_one = findViewById(R.id.tv_title_score_one);
        tv_title_score_two = findViewById(R.id.tv_title_score_two);
        tv_title_match_state = findViewById(R.id.tv_title_match_state);
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);
        et_input = findViewById(R.id.et_input);
        more_groups = findViewById(R.id.more_groups);
        fl_input = findViewById(R.id.fl_input);
        mFlWebview = findViewById(R.id.fl_webview);
        mWebView = findViewById(R.id.webView);
        mMatchPlayerView = findViewById(R.id.matchPlayerView);

        findViewById(R.id.tv_animation).setOnClickListener(this);
        findViewById(R.id.tv_video).setOnClickListener(this);
        findViewById(R.id.iv_report).setOnClickListener(this);
        findViewById(R.id.iv_collect).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_back_two).setOnClickListener(this);
        findViewById(R.id.iv_back_three).setOnClickListener(this);
        findViewById(R.id.iv_emoji).setOnClickListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);

        cl_title_two.setAlpha(0f);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
                cl_title_one.setAlpha(1f - percent);
                cl_title_two.setAlpha(percent);
            }
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(fl_input.getWidth(), fl_input.getHeight());
                layoutParams.bottomMargin = height;
                fl_input.setLayoutParams(layoutParams);
            }

            @Override
            public void keyBoardHide(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(fl_input.getWidth(), fl_input.getHeight());
                layoutParams.bottomMargin = 0;
                fl_input.setLayoutParams(layoutParams);
            }
        });
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    more_groups.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mRoomId = "f" + mId;
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(WordUtil.getString(this, R.string.match_status));
        mTitles.add(WordUtil.getString(this, R.string.match_live));
        mTitles.add(WordUtil.getString(this, R.string.match_lineup));
        mTitles.add(WordUtil.getString(this, R.string.match_index));
        mTitles.add(WordUtil.getString(this, R.string.match_chat));
        mViewList.add(FootballMatchStatusFragment.newInstance());
        mViewList.add(FootballMatchLiveFragment.newInstance(mId));
        mViewList.add(FootballMatchLineupFragment.newInstance(mId));
        mViewList.add(FootballMatchIndexFragment.newInstance(mId));
        mViewList.add(MatchChatFragment.newInstance(0, mId));
        initViewPager();

        showLoadingDialog();
        mvpPresenter.getDetail(mId);
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mvpPresenter.getReportList();
        } else {
            findViewById(R.id.fl_board).setVisibility(View.VISIBLE);
            findViewById(R.id.fl_board).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.forward(mActivity);
                }
            });
        }

        initPlayerView();
    }

    private void initPlayerView() {
        mMatchPlayerView.setPlayerViewCallback(new MatchPlayerView.OnSuperPlayerViewCallback() {
            @Override
            public void onStartFullScreenPlay() {
                mIsFullScreen = true;
            }

            @Override
            public void onStopFullScreenPlay() {
                mIsFullScreen = false;
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            @Override
            public void onClickFloatCloseBtn() {
                // 点击悬浮窗关闭按钮，那么结束整个播放
//                playerView.resetPlayer();
//                finish();
            }

            @Override
            public void onClickSmallReturnBtn() {
                backAction();
            }

            @Override
            public void onStartFloatWindowPlay() {
                // 开始悬浮播放后，直接返回到首页，进行悬浮播放
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onRefreshClick() {
                if (!TextUtils.isEmpty(mPlayingUrl)) {
                    mMatchPlayerView.play(mPlayingUrl);
                }
            }

            @Override
            public void onQualityChange(int type) {
            }

            @Override
            public void onClickRedEnvelope() {
            }
        });
    }

    private void backAction() {
        mMatchPlayerView.setVisibility(View.GONE);
        mMatchPlayerView.onPause();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mIsFullScreen) {
                mMatchPlayerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
                return true;
            } else {
                //继续执行父类其他点击事件
                return super.onKeyUp(keyCode, event);
            }
        }
        //继续执行父类其他点击事件
        return super.onKeyUp(keyCode, event);
    }

    private void initWebView(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        /* 设置支持Js */
        mWebView.getSettings().setJavaScriptEnabled(true);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        /* 设置缓存模式 */
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDomStorageEnabled(true);

        /* 设置为使用webview推荐的窗口 */
        mWebView.getSettings().setUseWideViewPort(true);
        /* 设置为使用屏幕自适配 */
        mWebView.getSettings().setLoadWithOverviewMode(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWebView.getSettings().setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWebView.getSettings().setGeolocationEnabled(true);
        /* 设置可以访问文件 */
        mWebView.getSettings().setAllowFileAccess(true);

        // 设置UserAgent标识
//        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " app-shikuimapp");
        mWebView.loadUrl(url);
        try {
            TrackHelper.track().outlink(new URL(url)).with(((AppManager) getApplication()).getTracker());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataSuccess(FootballDetailBean detailBean) {
        dismissLoadingDialog();
        if (detailBean != null) {
            if (detailBean.getIs_collect() == 1) {
                iv_collect.setSelected(true);
            } else {
                iv_collect.setSelected(false);
            }
            if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//繁体中文
                if (!TextUtils.isEmpty(detailBean.getCompetition_name_zht())) {
                    tv_match_name.setText(detailBean.getCompetition_name_zht());
                } else {
                    tv_match_name.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_zht())) {
                    tv_team_name_one.setText(detailBean.getHome_team_name_zht());
                } else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_zht())) {
                    tv_team_name_two.setText(detailBean.getAway_team_name_zht());
                } else {
                    tv_team_name_two.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_zht())) {
                    tv_title_team_one.setText(detailBean.getHome_team_name_zht());
                } else {
                    tv_title_team_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_zht())) {
                    tv_title_team_two.setText(detailBean.getAway_team_name_zht());
                } else {
                    tv_title_team_two.setText("");
                }
            } else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//英文
                if (!TextUtils.isEmpty(detailBean.getCompetition_name_en())) {
                    tv_match_name.setText(detailBean.getCompetition_name_en());
                } else {
                    tv_match_name.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_en())) {
                    tv_team_name_one.setText(detailBean.getHome_team_name_en());
                } else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_en())) {
                    tv_team_name_two.setText(detailBean.getAway_team_name_en());
                } else {
                    tv_team_name_two.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_en())) {
                    tv_title_team_one.setText(detailBean.getHome_team_name_en());
                } else {
                    tv_title_team_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_en())) {
                    tv_title_team_two.setText(detailBean.getAway_team_name_en());
                } else {
                    tv_title_team_two.setText("");
                }
            } else {//简体中文
                if (!TextUtils.isEmpty(detailBean.getCompetition_name_zh())) {
                    tv_match_name.setText(detailBean.getCompetition_name_zh());
                } else {
                    tv_match_name.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_zh())) {
                    tv_team_name_one.setText(detailBean.getHome_team_name_zh());
                } else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_zh())) {
                    tv_team_name_two.setText(detailBean.getAway_team_name_zh());
                } else {
                    tv_team_name_two.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getHome_team_name_zh())) {
                    tv_title_team_one.setText(detailBean.getHome_team_name_zh());
                } else {
                    tv_title_team_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_name_zh())) {
                    tv_title_team_two.setText(detailBean.getAway_team_name_zh());
                } else {
                    tv_title_team_two.setText("");
                }
            }
            if (!TextUtils.isEmpty(detailBean.getTime())) {
                tv_match_time.setText(detailBean.getTime());
            } else {
                tv_match_time.setText("");
            }
            GlideUtil.loadTeamImageDefault(this, detailBean.getHome_team_log(), iv_team_logo_one);
            GlideUtil.loadTeamImageDefault(this, detailBean.getAway_team_log(), iv_team_logo_two);
            switch (detailBean.getStatus()) {
                case 1:
                    tv_state.setText("未开赛");
                    tv_title_match_state.setText("未开赛");
                    break;
                case 2:
                    tv_state.setText("上半场");
                    tv_title_match_state.setText("上半场");
                    break;
                case 3:
                    tv_state.setText("中场");
                    tv_title_match_state.setText("中场");
                    break;
                case 4:
                    tv_state.setText("下半场");
                    tv_title_match_state.setText("下半场");
                    break;
                case 5:
                    tv_state.setText("加时赛");
                    tv_title_match_state.setText("加时赛");
                    break;
                case 6:
                    tv_state.setText("点球决战");
                    tv_title_match_state.setText("点球决战");
                    break;
                case 7:
                    tv_state.setText("完场");
                    tv_title_match_state.setText("完场");
                    break;
                default:
                    tv_state.setText("");
                    tv_title_match_state.setText("");
                    break;
            }
            tv_team_one_score.setText(String.valueOf(detailBean.getHome_data().get(0)));
            tv_team_two_score.setText(String.valueOf(detailBean.getAway_data().get(0)));
            tv_half_score.setText("半场" + detailBean.getHalf_score());
            tv_title_score_one.setText(String.valueOf(detailBean.getHome_data().get(0)));
            tv_title_score_two.setText(String.valueOf(detailBean.getAway_data().get(0)));
            ((FootballMatchStatusFragment) mViewList.get(0)).setData(detailBean);

            //初始化动画直播网页
            initWebView(detailBean.getLive_animation_url());
            //获取视频地址
            mHdUrl = detailBean.getPushurl1();
            mSdUrl = detailBean.getPushurl2();
        }
    }

    @Override
    public void getReportListSuccess(List<ReportBean> list) {
        if (list != null) {
            mReportList = list;
        }
    }

    @Override
    public void doReportSuccess() {
        ToastUtil.show(getString(R.string.tip_report_success));
    }

    @Override
    public void getDataFail(String msg) {
        dismissLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_animation:
                mFlWebview.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_video:
                DialogUtil.showSelectPullUrlDialog(this, mHdUrl, mSdUrl, new DialogUtil.SelectPullUrlBack() {
                    @Override
                    public void onSelectUrl(String url) {
                        if (!TextUtils.isEmpty(url)) {
                            mMatchPlayerView.setVisibility(View.VISIBLE);
                            mPlayingUrl = url;
                            mMatchPlayerView.play(url);
                        }
                    }
                });
                break;
            case R.id.iv_back_three:
                mFlWebview.setVisibility(View.GONE);
                break;
            case R.id.iv_report:
                if (mReportList != null && mReportList.size() > 0) {
                    SparseArray<String> array = new SparseArray<>();
                    for (int i = 0; i < mReportList.size(); i++) {
                        array.put(mReportList.get(i).getId(), mReportList.get(i).getName());
                    }
                    DialogUtil.showStringArrayDialog(mActivity, array, new DialogUtil.StringArrayDialogCallback() {
                        @Override
                        public void onItemClick(String text, int tag) {
                            mvpPresenter.doReport(tag, mId);
                        }
                    });
                }
                break;
            case R.id.iv_collect:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                iv_collect.setSelected(!iv_collect.isSelected());
                mvpPresenter.doCollect(2, mId);
                break;
            case R.id.iv_back:
            case R.id.iv_back_two:
                finish();
                break;
            case R.id.iv_emoji:
                if (more_groups.getVisibility() == View.GONE) {
                    showFaceViewGroup();
                } else {
                    hideFaceViewGroup();
                }
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getString(R.string.tip_input_chat_msg_empty));
                    return;
                }
                sendMessage(et_input.getText().toString());
                et_input.setText("");
                hideSoftInput();
                break;
        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_groups.setVisibility(View.GONE);
    }

    private void hideFaceViewGroup() {
        more_groups.setVisibility(View.GONE);
        et_input.requestFocus();
        imm.showSoftInput(et_input, 0);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        more_groups.setVisibility(View.VISIBLE);
        et_input.requestFocus();
        mFaceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
            @Override
            public void onEmojiDelete() {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                boolean isFace = false;
                if (index <= 0) {
                    return;
                }
                if (editable.charAt(index - 1) == ']') {
                    for (int i = index - 2; i >= 0; i--) {
                        if (editable.charAt(i) == '[') {
                            String faceChar = editable.subSequence(i, index).toString();
                            if (FaceManager.isFaceChar(faceChar)) {
                                editable.delete(i, index);
                                isFace = true;
                            }
                            break;
                        }
                    }
                }
                if (!isFace) {
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void onEmojiClick(Emoji emoji) {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                editable.insert(index, emoji.getFilter());
                FaceManager.handlerEmojiText(et_input, editable.toString(), true);
            }

            @Override
            public void onCustomFaceClick(int groupIndex, Emoji emoji) {
//                mMessageHandler.sendMessage(ChatMessageInfoUtil.buildCustomFaceMessage(groupIndex, emoji.getFilter()));
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mFaceFragment).commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (more_groups.getVisibility() == View.VISIBLE) {
                hideFaceViewGroup();
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initViewPager() {
        //初始化指示器
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CustomPagerTitleView titleView = new CustomPagerTitleView(context);
                titleView.setNormalColor(getResources().getColor(R.color.c_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.c_333333));
                titleView.setText(mTitles.get(index));
                titleView.setTextSize(16);
//                titleView.getPaint().setFakeBoldText(true);
                titleView.setOnPagerTitleChangeListener(new CustomPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(index);
                        }
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(DpUtil.dp2px(25));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_E3AC72));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 4) {
                    fl_input.setVisibility(View.VISIBLE);
                } else {
                    fl_input.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setOffscreenPageLimit(5);
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
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    //发送普通消息
    public void sendMessage(String content) {
        if (!TextUtils.isEmpty(mRoomId)) {
            NormalMsgBean msgBean = new NormalMsgBean();
            msgBean.setText(content);
            CustomMsgBean customMsgBean = new CustomMsgBean();
            customMsgBean.setType(MessageInfo.MSG_TYPE_BG_DANMU);
            customMsgBean.setNormal(msgBean);
            MessageInfo messageInfo = ChatMessageInfoUtil.buildCustomMessage(JSONObject.toJSONString(customMsgBean), "", null);
            messageInfo.setNickName(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            messageInfo.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
            messageInfo.setSelf(true);
            messageInfo.setRead(true);
            V2TIMManager.getMessageManager().sendMessage(messageInfo.getTimMessage(), null, mRoomId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null,
                    new V2TIMSendCallback<V2TIMMessage>() {
                        @Override
                        public void onProgress(int i) {

                        }

                        @Override
                        public void onSuccess(V2TIMMessage v2TIMMessage) {
                            if (mViewList != null && mViewList.size() > 0) {
                                if (mViewList.get(4) instanceof MatchChatFragment) {
                                    ((MatchChatFragment) mViewList.get(4)).updateMsg(messageInfo);
                                }
                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (playerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) {
//            //隐藏虚拟按键，并且全屏
//            View decorView = getWindow().getDecorView();
//            if (decorView == null) return;
//            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//                decorView.setSystemUiVisibility(View.GONE);
//            } else if (Build.VERSION.SDK_INT >= 19) {
//                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//                decorView.setSystemUiVisibility(uiOptions);
//            }
//        }
        if (mMatchPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mMatchPlayerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMatchPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mMatchPlayerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMatchPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mMatchPlayerView.resetPlayer();
        }
        super.onDestroy();
    }
}
