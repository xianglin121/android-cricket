package com.onecric.live.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.LiveRecommendHistoryAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LiveMorePresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.LiveMoreView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
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

    private int mType;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LiveRecommendAdapter mAdapter;
    private LiveRecommendHistoryAdapter mHistoryAdapter;

    private int mPage = 1;

    private LoginDialog loginDialog;
    private WebView webview;
    private WebSettings webSettings;

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
        if (mType == 1) {
            setTitleText(getString(R.string.today_live));
        }else if (mType == 2) {
            setTitleText(getString(R.string.history));
        }else {
            setTitleText(getString(R.string.free_live));
        }

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);

        EventBus.getDefault().register(this);
        initWebView();
        loginDialog =  new LoginDialog(this, R.style.dialog,true, () -> {
            loginDialog.dismiss();
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl("javascript:ab()");
        });
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(mType == 2){
                    mvpPresenter.getHistoryList(false, mPage);
                }else{
                    mvpPresenter.getList(false, mType, mPage);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(mType == 2){
                    mvpPresenter.getHistoryList(true, 1);
                }else{
                    mvpPresenter.getList(true, mType, 1);
                }
            }
        });

        View inflate2 = LayoutInflater.from(this).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerview.addItemDecoration(new GridDividerItemDecoration(this, 10, 2));
        if(mType == 2){
            mHistoryAdapter = new LiveRecommendHistoryAdapter(R.layout.item_live_recommend, new ArrayList<>());
            mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String url = mHistoryAdapter.getItem(position).getMediaUrl();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                        if(loginDialog!=null){
                            loginDialog.show();
                        }else{
                            ToastUtil.show(getString(R.string.please_login));
                        }
                    }else{
                        VideoSingleActivity.forward(mActivity, mHistoryAdapter.getItem(position).getMediaUrl(), null);
//                        LiveDetailActivity.forward(mActivity,mHistoryAdapter.getItem(position).getAuthorId(),mHistoryAdapter.getItem(position).getMatchId(),mHistoryAdapter.getItem(position).getMediaUrl());
                    }
                }
            });
            mHistoryAdapter.setEmptyView(inflate2);
            recyclerview.setAdapter(mHistoryAdapter);
        }else{
            mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(mAdapter.getItem(position).getIslive() == 0){
                        ToastUtil.show("The broadcast has not started");
                    }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                        if(loginDialog!=null){
                            loginDialog.show();
                        }else{
                            ToastUtil.show(getString(R.string.please_login));
                        }
                    }else{
                        LiveDetailActivity.forward(mActivity, mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
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
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mHistoryAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
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
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
