package com.onecric.live.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.LiveRecommendHistoryAdapter;
import com.onecric.live.adapter.LiveVideoAllAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.presenter.live.LiveMorePresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveMoreView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class LiveMoreActivity extends MvpActivity<LiveMorePresenter> implements LiveMoreView, View.OnClickListener {

    public static void forward(Context context, int type) {
        Intent intent = new Intent(context, LiveMoreActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public static void forward(Context context,String matchTitle) {
        Intent intent = new Intent(context, LiveMoreActivity.class);
        intent.putExtra("matchTitle", matchTitle);
        context.startActivity(intent);
    }

    private int mType;
//    private int matchId;
    private String matchTitle;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LinearLayout ll_title;
    private TextView tv_title;
    private ImageView iv_avatar;


    private LiveRecommendAdapter mAdapter;
    private LiveRecommendHistoryAdapter mHistoryAdapter;
    private LiveVideoAllAdapter mAllAdapter;

    private int mPage = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;

//    private LoginDialog loginDialog;
//    private WebView webview;
//    private WebSettings webSettings;

    @Override
    protected LiveMorePresenter createPresenter() {
        return new LiveMorePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_more;
    }

    @Override
    protected void initView() {
        mType = getIntent().getIntExtra("type", 0);
//        matchId = getIntent().getIntExtra("matchId", 0);
        matchTitle = getIntent().getStringExtra("matchTitle");

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        iv_avatar = findViewById(R.id.iv_avatar);

        ll_title.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(matchTitle)){
            tv_title.setText(matchTitle+"");
        }else
            if (mType == 1) {
            tv_title.setText(getString(R.string.today_live));
        }else if (mType == 2) {
            tv_title.setText(getString(R.string.history));
        }else {
            tv_title.setText(getString(R.string.free_live));
        }
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
        } else {
            iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.iv_avatar).setOnClickListener(v -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                OneLogInActivity.forward(mActivity);
            } else {
                if (!isFastDoubleClick())
                    PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
            }
        });

        EventBus.getDefault().register(this);
/*        initWebView();
        loginDialog =  new LoginDialog(this, R.style.dialog,true, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });*/
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(!TextUtils.isEmpty(matchTitle) ){
                }else
                    if(mType == 2){
                    mvpPresenter.getHistoryList(false, mPage);
                }else{
                    mvpPresenter.getList(false, mType, mPage);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(!TextUtils.isEmpty(matchTitle) ){
                    mvpPresenter.getMatchVideoList(matchTitle);
                }else
                    if(mType == 2){
                    mvpPresenter.getHistoryList(true, 1);
                }else{
                    mvpPresenter.getList(true, mType, 1);
                }
            }
        });

        View inflate2 = LayoutInflater.from(this).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(matchTitle)){
            smart_rl.setEnableLoadMore(false);
            recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
            mAllAdapter = new LiveVideoAllAdapter(R.layout.item_live_video_all, new ArrayList<>());
            mAllAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(mActivity);
                    }else{
                        LiveDetailActivity.forward(mActivity,mAllAdapter.getItem(position).uid,mAllAdapter.getItem(position).matchId, mAllAdapter.getItem(position).liveId);
                    }
                }
            });
            mAllAdapter.setEmptyView(inflate2);
            recyclerview.setAdapter(mAllAdapter);
            skeletonScreen = Skeleton.bind(recyclerview)
                    .adapter(mAllAdapter)
                    .shimmer(false)
                    .count(10)
                    .load(R.layout.item_live_video_skeleton)
                    .show();

        }else if(mType == 2){
                smart_rl.setRefreshFooter(new ClassicsFooter(this));
//                recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
//                recyclerview.addItemDecoration(new GridDividerItemDecoration(this, 10, 2));
                recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));

            mHistoryAdapter = new LiveRecommendHistoryAdapter(R.layout.item_live_video_all, new ArrayList<>());
            mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String url = mHistoryAdapter.getItem(position).getMediaUrl();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(mActivity);
                    }else{
//                        VideoSingleActivity.forward(mActivity, mHistoryAdapter.getItem(position).getMediaUrl(), null);
                        LiveDetailActivity.forward(mActivity,Integer.parseInt(mHistoryAdapter.getItem(position).getUid()),mHistoryAdapter.getItem(position).getMatchId(),
                                mHistoryAdapter.getItem(position).getMediaUrl(),mHistoryAdapter.getItem(position).getLive_id());
                    }
                }
            });
            mHistoryAdapter.setEmptyView(inflate2);
            recyclerview.setAdapter(mHistoryAdapter);
            skeletonScreen = Skeleton.bind(recyclerview)
                    .adapter(mHistoryAdapter)
                    .shimmer(false)
                    .count(10)
                    .load(R.layout.item_live_video_skeleton)
                    .show();
        }else{
                smart_rl.setRefreshFooter(new ClassicsFooter(this));
            recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
            recyclerview.addItemDecoration(new GridDividerItemDecoration(this, 10, 2));
            mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(mAdapter.getItem(position).getIslive() == 0){
                        LiveNotStartDetailActivity.forward(mActivity,mAdapter.getItem(position).getUid(),
                                mAdapter.getItem(position).getMatch_id(),mAdapter.getItem(position).getLive_id());
                    }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(mActivity);
                    }else{
                        LiveDetailActivity.forward(mActivity, mAdapter.getItem(position).getUid(),
                                mAdapter.getItem(position).getMatch_id(),mAdapter.getItem(position).getLive_id());
                    }
                }
            });
            mAdapter.setEmptyView(inflate2);
            recyclerview.setAdapter(mAdapter);
        }
        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.setNewData(list);
            }else {
                mAdapter.setNewData(new ArrayList<>());
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataHistorySuccess(boolean isRefresh, List<HistoryLiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null && list.size() > 0) {
                mHistoryAdapter.setNewData(list);
            }else {
                mHistoryAdapter.setNewData(new ArrayList<>());
            }
            skeletonScreen.hide();
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mHistoryAdapter.addData(list);

            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
            skeletonScreen.hide();
        }
    }

    @Override
    public void getMatchVideoSuccess(List<HistoryLiveBean> list) {

    }

    @Override
    public void getTournamentSuccess(List<LiveVideoBean.LBean> list) {
        smart_rl.finishRefresh();
        if (list != null && list.size() > 0) {
            mAllAdapter.setNewData(list);
            skeletonScreen.hide();
        }else {
            mAllAdapter.setNewData(new ArrayList<>());
            skeletonScreen.hide();
        }
    }

    @Override
    public void getVideoSuccess(boolean isRefresh,List<ViewMoreBean> list) {

    }


    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    //登录成功，更新信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            smart_rl.autoRefresh();
            if (CommonAppConfig.getInstance().getUserBean() != null) {
                GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            } else {
                iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
            }
        }
    }

/*
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
*/
/*                                loginDialog.show();
                                loginDialog.passWebView();*//*

                                OneLogInActivity.forward(mActivity);
                            }
                        });
                    }
                }
            }
        }, 500);
    }
*/

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
