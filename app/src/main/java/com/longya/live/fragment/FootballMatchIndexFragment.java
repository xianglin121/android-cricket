package com.longya.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.longya.live.R;
import com.longya.live.adapter.FootballMatchBenchLineupAdapter;
import com.longya.live.custom.CustomPagerTitleView;
import com.longya.live.model.BasketballIndexBean;
import com.longya.live.model.BasketballIndexOneBean;
import com.longya.live.model.BasketballIndexTwoBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.FootballMatchIndexPresenter;
import com.longya.live.presenter.match.FootballMatchLineupPresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchIndexView;
import com.longya.live.view.match.FootballMatchLineupView;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FootballMatchIndexFragment extends MvpFragment<FootballMatchIndexPresenter> implements FootballMatchIndexView, View.OnClickListener {

    public static FootballMatchIndexFragment newInstance(int id) {
        FootballMatchIndexFragment fragment = new FootballMatchIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private TextView tv_rangqiu, tv_oupei, tv_daxiaoqiu;
    private ViewPager vp_index;
    private List<Fragment> mViewList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_index;
    }

    @Override
    protected FootballMatchIndexPresenter createPresenter() {
        return new FootballMatchIndexPresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");
        mViewList = new ArrayList<>();

        tv_rangqiu = rootView.findViewById(R.id.tv_rangqiu);
        tv_oupei = rootView.findViewById(R.id.tv_oupei);
        tv_daxiaoqiu = rootView.findViewById(R.id.tv_daxiaoqiu);
        vp_index = rootView.findViewById(R.id.vp_index);

        tv_rangqiu.setOnClickListener(this);
        tv_oupei.setOnClickListener(this);
        tv_daxiaoqiu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_rangqiu.setSelected(true);
        mViewList.add(FootballMatchIndexInnerFragment.newInstance(FootballMatchIndexInnerFragment.TYPE_RANGQIU,mId));
        mViewList.add(FootballMatchIndexInnerFragment.newInstance(FootballMatchIndexInnerFragment.TYPE_OUPEI,mId));
        mViewList.add(FootballMatchIndexInnerFragment.newInstance(FootballMatchIndexInnerFragment.TYPE_DAXIAOQIU,mId));

        initViewPager();
        mvpPresenter.getDetail(mId);
    }

    private void initViewPager() {
        //初始化viewpager
        vp_index.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        vp_index.setOffscreenPageLimit(2);
        vp_index.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mViewList.get(i);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rangqiu:
                if (tv_rangqiu.isSelected()) {
                    return;
                }
                tv_rangqiu.setSelected(true);
                tv_oupei.setSelected(false);
                tv_daxiaoqiu.setSelected(false);
                vp_index.setCurrentItem(0, false);
                break;
            case R.id.tv_oupei:
                if (tv_oupei.isSelected()) {
                    return;
                }
                tv_rangqiu.setSelected(false);
                tv_oupei.setSelected(true);
                tv_daxiaoqiu.setSelected(false);
                vp_index.setCurrentItem(1, false);
                break;
            case R.id.tv_daxiaoqiu:
                if (tv_daxiaoqiu.isSelected()) {
                    return;
                }
                tv_rangqiu.setSelected(false);
                tv_oupei.setSelected(false);
                tv_daxiaoqiu.setSelected(true);
                vp_index.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(BasketballIndexBean indexBean) {
        if (indexBean != null) {
            //让球
            List<BasketballIndexOneBean> asia = indexBean.getAsia();
            if (asia != null) {
                if (indexBean.getAsia_additional() != null) {
                    BasketballIndexTwoBean highest = indexBean.getAsia_additional().getHighest();
                    if (highest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.highest_value));
                        bean.setEarly_host(highest.getHost());
                        bean.setEarly_early(highest.getEarly());
                        bean.setEarly_guest(highest.getGuest());
                        bean.setScilicet_host(highest.getHosts());
                        bean.setScilicet_early(highest.getEarlys());
                        bean.setScilicet_guest(highest.getGuests());
                        asia.add(bean);
                    }
                    BasketballIndexTwoBean lowest = indexBean.getAsia_additional().getLowest();
                    if (lowest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.lowest_value));
                        bean.setEarly_host(lowest.getHost());
                        bean.setEarly_early(lowest.getEarly());
                        bean.setEarly_guest(lowest.getGuest());
                        bean.setScilicet_host(lowest.getHosts());
                        bean.setScilicet_early(lowest.getEarlys());
                        bean.setScilicet_guest(lowest.getGuests());
                        asia.add(bean);
                    }
                }
            }
            ((FootballMatchIndexInnerFragment)mViewList.get(0)).setData(asia, indexBean.getCompensations());
            //欧赔
            List<BasketballIndexOneBean> eu = indexBean.getEu();
            if (eu != null) {
                if (indexBean.getEu_additional() != null) {
                    BasketballIndexTwoBean highest = indexBean.getEu_additional().getHighest();
                    if (highest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.highest_value));
                        bean.setEarly_host(highest.getHost());
                        bean.setEarly_early(highest.getEarly());
                        bean.setEarly_guest(highest.getGuest());
                        bean.setScilicet_host(highest.getHosts());
                        bean.setScilicet_early(highest.getEarlys());
                        bean.setScilicet_guest(highest.getGuests());
                        eu.add(bean);
                    }
                    BasketballIndexTwoBean lowest = indexBean.getEu_additional().getLowest();
                    if (lowest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.lowest_value));
                        bean.setEarly_host(lowest.getHost());
                        bean.setEarly_early(lowest.getEarly());
                        bean.setEarly_guest(lowest.getGuest());
                        bean.setScilicet_host(lowest.getHosts());
                        bean.setScilicet_early(lowest.getEarlys());
                        bean.setScilicet_guest(lowest.getGuests());
                        eu.add(bean);
                    }
                    BasketballIndexTwoBean average = indexBean.getEu_additional().getAverage();
                    if (average != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.average_value));
                        bean.setEarly_host(average.getHost());
                        bean.setEarly_early(average.getEarly());
                        bean.setEarly_guest(average.getGuest());
                        bean.setScilicet_host(average.getHosts());
                        bean.setScilicet_early(average.getEarlys());
                        bean.setScilicet_guest(average.getGuests());
                        eu.add(bean);
                    }
                }
            }
            ((FootballMatchIndexInnerFragment)mViewList.get(1)).setData(eu, indexBean.getCompensations());
            //大小球
            List<BasketballIndexOneBean> bs = indexBean.getBs();
            if (bs != null) {
                if (indexBean.getBs_additional() != null) {
                    BasketballIndexTwoBean highest = indexBean.getBs_additional().getHighest();
                    if (highest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.highest_value));
                        bean.setEarly_host(highest.getHost());
                        bean.setEarly_early(highest.getEarly());
                        bean.setEarly_guest(highest.getGuest());
                        bean.setScilicet_host(highest.getHosts());
                        bean.setScilicet_early(highest.getEarlys());
                        bean.setScilicet_guest(highest.getGuests());
                        bs.add(bean);
                    }
                    BasketballIndexTwoBean lowest = indexBean.getBs_additional().getLowest();
                    if (lowest != null) {
                        BasketballIndexOneBean bean = new BasketballIndexOneBean();
                        bean.setCompany_name(getContext().getString(R.string.lowest_value));
                        bean.setEarly_host(lowest.getHost());
                        bean.setEarly_early(lowest.getEarly());
                        bean.setEarly_guest(lowest.getGuest());
                        bean.setScilicet_host(lowest.getHosts());
                        bean.setScilicet_early(lowest.getEarlys());
                        bean.setScilicet_guest(lowest.getGuests());
                        bs.add(bean);
                    }
                }
            }
            ((FootballMatchIndexInnerFragment)mViewList.get(2)).setData(bs, indexBean.getCompensations());
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
