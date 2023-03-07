package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.CricketNewLiveAdapter;
import com.onecric.live.model.CricketLiveBean;
import com.onecric.live.presenter.cricket.CricketLivePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketLiveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class AnimationLiveFragment extends MvpFragment<CricketLivePresenter> implements CricketLiveView {
    public static AnimationLiveFragment newInstance() {
        AnimationLiveFragment fragment = new AnimationLiveFragment();
        return fragment;
    }

    private String mLivePath;
    private WebView mWvAnimation;
    private LinearLayout ll_empty;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_live_animation;
    }

    @Override
    protected CricketLivePresenter createPresenter() {
        return new CricketLivePresenter(this);
    }

    @Override
    protected void initUI() {
        mWvAnimation = findViewById(R.id.wv_animation);
        ll_empty = findViewById(R.id.ll_empty);
        initWebViewOne();
    }

    private void initWebViewOne() {
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
    }

    public void setLivePath(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        initWebViewOne();
        mWvAnimation.loadUrl(url);
        mWvAnimation.setVisibility(View.VISIBLE);
        ll_empty.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<CricketLiveBean> list) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void loadMoreDataSuccess(List<CricketLiveBean> list) {

    }

    @Override
    public void loadMoreDataFailed() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWvAnimation != null) {
            mWvAnimation.destroy();
        }

    }
}
