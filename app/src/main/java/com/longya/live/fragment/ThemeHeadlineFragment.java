package com.longya.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.activity.LoginActivity;
import com.longya.live.activity.ThemeCollectionActivity;
import com.longya.live.adapter.ChannelPagerAdapter;
import com.longya.live.custom.CustomPagerInnerTitleView;
import com.longya.live.model.ThemeClassifyBean;
import com.longya.live.presenter.theme.ThemeHeadlinePresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.theme.ThemeHeadlineView;

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
        if (list != null && list.size() > 0) {
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

    }
}
