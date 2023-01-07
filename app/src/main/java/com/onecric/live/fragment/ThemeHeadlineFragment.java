package com.onecric.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.ThemeCollectionActivity;
import com.onecric.live.adapter.ChannelPagerAdapter;
import com.onecric.live.custom.CustomPagerInnerTitleView;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.presenter.theme.ThemeHeadlinePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.theme.ThemeHeadlineView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class ThemeHeadlineFragment extends MvpFragment<ThemeHeadlinePresenter> implements ThemeHeadlineView {

    public static ThemeHeadlineFragment newInstance() {
        ThemeHeadlineFragment fragment = new ThemeHeadlineFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private MagicIndicator mIndicator;
    private CommonNavigatorAdapter mIndicatorAdapter;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private ChannelPagerAdapter mPagerAdapter;
    private List<Fragment> mViewList;
    private SmartRefreshLayout smart_no_network;
    private TextView tv_empty;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_headline;
    }

    @Override
    protected ThemeHeadlinePresenter createPresenter() {
        return new ThemeHeadlinePresenter(this);
    }

    @Override
    protected void initUI() {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();

        mIndicator = rootView.findViewById(R.id.indicator);
        mViewPager = rootView.findViewById(R.id.view_pager);
        tv_empty = rootView.findViewById(R.id.tv_empty);
        smart_no_network = rootView.findViewById(R.id.smart_no_network);
        rootView.findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    LoginActivity.forward(getContext());
                    return;
                }
                ThemeCollectionActivity.forward(getContext(), 0);
            }
        });

        smart_no_network.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_no_network.setOnRefreshListener(refreshLayout -> {
            initData();
        });
        findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        tv_empty.setText(R.string.pull_refresh);
    }

    @Override
    protected void initData() {
        mvpPresenter.getClassify();
    }

    private void initViewPager() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mIndicatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CustomPagerInnerTitleView titleView = new CustomPagerInnerTitleView(context);
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.rightMargin = DpUtil.dp2px(12);
//                titleView.setContentView(R.layout.item_ym_indicator);
//                titleView.setLayoutParams(layoutParams);
//                SuperTextView tv_name = titleView.findViewById(R.id.tv_name);
                titleView.setText(mTitles.get(index));
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        };
        commonNavigator.setAdapter(mIndicatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        mPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), mViewList);
        mViewPager.setOffscreenPageLimit(mViewList.size());
        mViewPager.setAdapter(mPagerAdapter);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<ThemeClassifyBean> list) {
        smart_no_network.finishRefresh();
        smart_no_network.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        if (list != null && list.size() > 0) {
            mTitles.clear();
            mViewList.clear();
            mTitles.add(getString(R.string.theme_hot));
            mViewList.add(ThemeHeadlineInnerFragment.newInstance(0));
            for (int i = 0; i < list.size(); i++) {
                ThemeClassifyBean bean = list.get(i);
                if (!TextUtils.isEmpty(bean.getName())) {
                    mTitles.add(bean.getName());
                    mViewList.add(ThemeHeadlineInnerFragment.newInstance(bean.getId()));
                }
            }
            initViewPager();
        }
    }

    @Override
    public void getDataFail(String msg) {
        //没网时空图
        smart_no_network.finishRefresh();
        if(msg.equals(getString(R.string.no_internet_connection)) && (mIndicatorAdapter == null)){
            smart_no_network.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
        }
    }
}
