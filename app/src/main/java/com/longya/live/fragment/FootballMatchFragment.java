package com.longya.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.R;
import com.longya.live.activity.FootballMatchSettingActivity;
import com.longya.live.adapter.ChannelPagerAdapter;
import com.longya.live.custom.CustomPagerInnerTitleView;
import com.longya.live.custom.CustomPagerMatchTitleView;
import com.longya.live.event.ChangeFootballLanguageEvent;
import com.longya.live.event.UpdateFootballCollectCountEvent;
import com.longya.live.event.UpdateRedAndYellowCardEvent;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchSocketBean;
import com.longya.live.presenter.match.FootballMatchPresenter;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FootballMatchFragment extends MvpFragment<FootballMatchPresenter> implements FootballMatchView {
    public static FootballMatchFragment newInstance() {
        FootballMatchFragment fragment = new FootballMatchFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private MagicIndicator mIndicator;
    private  CommonNavigator commonNavigator;
    private List<String> mTitles;
    private ViewPager mViewPager;
    private ChannelPagerAdapter mPagerAdapter;
    private List<Fragment> mViewList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match;
    }

    @Override
    protected FootballMatchPresenter createPresenter() {
        return new FootballMatchPresenter(this);
    }

    @Override
    protected void initUI() {
        mTitles = new ArrayList<>();
        mViewList = new ArrayList<>();

        mIndicator = rootView.findViewById(R.id.indicator);
        mViewPager = rootView.findViewById(R.id.view_pager);
        rootView.findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FootballMatchSettingActivity.forward(getContext());
            }
        });
    }

    @Override
    protected void initData() {
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_complete));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_started));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_schedule));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_result));
        mTitles.add(WordUtil.getString(getActivity(), R.string.match_collect));

        mViewList.add(FootballMatchInnerFragment.newInstance(0));
        mViewList.add(FootballMatchInnerFragment.newInstance(1));
        mViewList.add(FootballMatchScheduleFragment.newInstance(2));
        mViewList.add(FootballMatchScheduleFragment.newInstance(3));
        mViewList.add(FootballMatchInnerFragment.newInstance(4));
        initViewPager();

        mvpPresenter.getCollectCount();
    }

    private void initViewPager() {
        commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
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
        });
        commonNavigator.setAdjustMode(true);
        mIndicator.setNavigator(commonNavigator);
        mPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), mViewList);
        mViewPager.setOffscreenPageLimit(mViewList.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CustomPagerInnerTitleView pagerTitleView = (CustomPagerInnerTitleView) commonNavigator.getPagerTitleView(4);
                if (position == 4) {
                    pagerTitleView.setCountVisibility(View.INVISIBLE);
                }else {
                    pagerTitleView.setCountVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(mPagerAdapter);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    public void updateData(MatchSocketBean dataBean) {
        if (mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof  FootballMatchInnerFragment) {
                    ((FootballMatchInnerFragment)mViewList.get(i)).updateData(dataBean);
                }else if (mViewList.get(i) instanceof  FootballMatchScheduleFragment) {
                    ((FootballMatchScheduleFragment)mViewList.get(i)).updateData(dataBean);
                }
            }
        }
    }

    public void updateTime() {
        if (mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof  FootballMatchInnerFragment) {
                    ((FootballMatchInnerFragment)mViewList.get(i)).updateTime();
                }else if (mViewList.get(i) instanceof  FootballMatchScheduleFragment) {
                    ((FootballMatchScheduleFragment)mViewList.get(i)).updateTime();
                }
            }
        }
    }

    public void updateId(String id) {
        if (mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof  FootballMatchInnerFragment) {
                    ((FootballMatchInnerFragment)mViewList.get(i)).updateId(id);
                }else if (mViewList.get(i) instanceof  FootballMatchScheduleFragment) {
                    ((FootballMatchScheduleFragment)mViewList.get(i)).updateId(id);
                }
            }
        }
    }

    public List<FootballMatchBean> getData() {
        List<FootballMatchBean> list = new ArrayList();
        if (mViewList != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof  FootballMatchInnerFragment) {
                    list.addAll(((FootballMatchInnerFragment)mViewList.get(i)).getData());
                }else if (mViewList.get(i) instanceof  FootballMatchScheduleFragment) {
                    list.addAll(((FootballMatchScheduleFragment)mViewList.get(i)).getData());
                }
            }
        }
        return list;
    }

    public List<FootballMatchBean> getCollectData() {
        List<FootballMatchBean> list = new ArrayList();
        if (mViewList != null) {
            if (mViewList.get(4) instanceof  FootballMatchInnerFragment) {
                list.addAll(((FootballMatchInnerFragment)mViewList.get(4)).getData());
            }
        }
        return list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFootballLanguageEvent(ChangeFootballLanguageEvent event) {
        if (event != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof FootballMatchInnerFragment) {
                    ((FootballMatchInnerFragment)mViewList.get(i)).refreshData();
                }
                if (mViewList.get(i) instanceof FootballMatchScheduleFragment) {
                    ((FootballMatchScheduleFragment)mViewList.get(i)).refreshData();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateRedAndYellowCardEvent(UpdateRedAndYellowCardEvent event) {
        if (event != null) {
            for (int i = 0; i < mViewList.size(); i++) {
                if (mViewList.get(i) instanceof FootballMatchInnerFragment) {
                    ((FootballMatchInnerFragment)mViewList.get(i)).refreshData();
                }
                if (mViewList.get(i) instanceof FootballMatchScheduleFragment) {
                    ((FootballMatchScheduleFragment)mViewList.get(i)).refreshData();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateFootballCollectCountEvent(UpdateFootballCollectCountEvent event) {
        if (event != null) {
            mvpPresenter.getCollectCount();
            if (mViewList.get(4) instanceof FootballMatchInnerFragment) {
                ((FootballMatchInnerFragment)mViewList.get(4)).refreshData();
            }
        }
    }

    @Override
    public void getCollectCountSuccess(int count) {
        if (commonNavigator != null) {
            CustomPagerInnerTitleView pagerTitleView = (CustomPagerInnerTitleView) commonNavigator.getPagerTitleView(4);
            pagerTitleView.setCount(String.valueOf(count));
            pagerTitleView.setCountVisibility(View.VISIBLE);
        }
    }
}
