package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.onecric.live.R;
import com.onecric.live.custom.CustomPagerTitleView;
import com.onecric.live.fragment.PopularRankingFragment;
import com.onecric.live.fragment.RichRankingFragment;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.live.RankingPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.RankingView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends MvpActivity<RankingPresenter> implements RankingView, View.OnClickListener {
    public static final int TYPE_WEEK = 0;
    public static final int TYPE_MONTH = 1;
    public static final int TYPE_TOTAL = 2;
    public static void forward(Context context) {
        Intent intent = new Intent(context, RankingActivity.class);
        context.startActivity(intent);
    }

    private MagicIndicator magicIndicator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private List<Fragment> mViewList;

    @Override
    protected RankingPresenter createPresenter() {
        return new RankingPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void initView() {
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitles.add(getString(R.string.popular_ranking));
        mTitles.add(getString(R.string.rich_ranking));
        mViewList.add(PopularRankingFragment.newInstance());
        mViewList.add(RichRankingFragment.newInstance());
        initViewPager();
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
                titleView.setNormalColor(getResources().getColor(R.color.c_E3AC72));
                titleView.setSelectedColor(getResources().getColor(R.color.c_87390E));
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
                linePagerIndicator.setLineWidth(DpUtil.dp2px(66));
                linePagerIndicator.setLineHeight(DpUtil.dp2px(3));
                linePagerIndicator.setXOffset(DpUtil.dp2px(0));
                linePagerIndicator.setYOffset(DpUtil.dp2px(1));
                linePagerIndicator.setRoundRadius(DpUtil.dp2px(2));
                linePagerIndicator.setColors(getResources().getColor(R.color.c_87390E));
                return linePagerIndicator;
            }
        });
        //初始化viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
