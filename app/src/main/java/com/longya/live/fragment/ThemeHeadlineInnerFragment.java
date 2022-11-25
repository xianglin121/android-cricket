package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.HeadlineDetailActivity;
import com.longya.live.adapter.BannerRoundImageAdapter;
import com.longya.live.adapter.ThemeBannerImageAdapter;
import com.longya.live.adapter.ThemeHeadlineAdapter;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.theme.ThemeHeadlineInnerPresenter;
import com.longya.live.presenter.video.VideoPresenter;
import com.longya.live.util.GlideUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.theme.ThemeHeadlineInnerView;
import com.longya.live.view.video.VideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThemeHeadlineInnerFragment extends MvpFragment<ThemeHeadlineInnerPresenter> implements ThemeHeadlineInnerView {

    public static ThemeHeadlineInnerFragment newInstance(int id) {
        ThemeHeadlineInnerFragment fragment = new ThemeHeadlineInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private SmartRefreshLayout smart_rl;
    private Banner mBanner;
    private RecyclerView rv_headline;
    private ThemeHeadlineAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_headline_inner;
    }

    @Override
    protected ThemeHeadlineInnerPresenter createPresenter() {
        return new ThemeHeadlineInnerPresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");

        smart_rl = rootView.findViewById(R.id.smart_rl);
        mBanner = rootView.findViewById(R.id.banner_headline);
        rv_headline = rootView.findViewById(R.id.rv_headline);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mId, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mId, 1);
            }
        });

        mAdapter = new ThemeHeadlineAdapter(R.layout.item_theme_headline, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HeadlineDetailActivity.forward(getContext(), mAdapter.getItem(position).getId());
            }
        });
        rv_headline.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_headline.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<HeadlineBean> list, List<HeadlineBean> banners) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (banners != null) {
               initBanner(banners);
            }else {
                mBanner.setVisibility(View.GONE);
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            if (list.size() > 0) {
                hideEmptyView();
            }else {
                showEmptyView();
            }
            mAdapter.setNewData(list);
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

    private void initBanner(List<HeadlineBean> banners) {
        if (banners.size() == 0) {
            mBanner.setVisibility(View.GONE);
        }
        mBanner.setIndicator(new RectangleIndicator(getContext()));
        mBanner.setAdapter(new BannerRoundImageAdapter(banners) {
            @Override
            public void onBindView(Object holder, Object data, int position, int size) {
                GlideUtil.loadImageDefault(getContext(), ((HeadlineBean)data).getImg(), ((BannerRoundImageHolder)holder).imageView);
                if (!TextUtils.isEmpty(((HeadlineBean)data).getTitle())) {
                    ((BannerRoundImageHolder)holder).textView.setText(((HeadlineBean)data).getTitle());
                }else {
                    ((BannerRoundImageHolder)holder).textView.setText("");
                }
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                HeadlineDetailActivity.forward(getContext(), ((HeadlineBean)data).getId());
            }
        });
        mBanner.addBannerLifecycleObserver(this);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.destroy();
        }
    }
}
